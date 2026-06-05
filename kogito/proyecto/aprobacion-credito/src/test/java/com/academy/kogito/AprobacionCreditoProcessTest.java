package com.academy.kogito;

import com.academy.kogito.model.Evaluacion;
import com.academy.kogito.model.SolicitudCredito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.kogito.Application;
import org.kie.kogito.Model;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.Processes;
import org.kie.kogito.process.WorkItem;
import org.kie.kogito.services.uow.UnitOfWorkExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de INTEGRACIÓN del proceso BPMN {@code aprobacionCredito}.
 *
 * <p>Se levanta el contexto completo de Spring Boot ({@link SpringBootTest}) para que
 * Kogito genere y registre todos los procesos. Se inyecta el bean
 * {@link Processes} (catálogo de procesos de la aplicación) y, a partir de él,
 * se obtiene el proceso por su {@code processId} = {@code "aprobacionCredito"}.</p>
 *
 * <p>El patrón de la API de procesos de Kogito en Spring Boot es:</p>
 * <ol>
 *   <li>{@code Process<Model> p = (Process<Model>) processes.processById("aprobacionCredito");}</li>
 *   <li>Crear un {@code Model} con las variables de entrada (aquí: "solicitud").</li>
 *   <li>{@code ProcessInstance<Model> instance = p.createInstance(model);}</li>
 *   <li>{@code instance.start();}</li>
 *   <li>Consultar {@code instance.status()} y {@code instance.variables()}.</li>
 * </ol>
 */
@SpringBootTest
@DisplayName("Proceso BPMN aprobacionCredito")
class AprobacionCreditoProcessTest {

    /** Catálogo de procesos generado por Kogito; permite obtener cada Process por su id. */
    @Autowired
    Processes processes;

    /**
     * Aplicación Kogito: expone el {@code UnitOfWorkManager}. Iniciar una instancia
     * de proceso debe hacerse dentro de una Unidad de Trabajo para que los efectos
     * (variables, tareas) se confirmen correctamente.
     */
    @Autowired
    Application application;

    /**
     * CASO FELIZ: una solicitud de PERFIL FUERTE (ingresos altos, monto bajo, sin mora,
     * edad en el rango premiado 25-65) debe obtener score alto y ser APROBADA.
     *
     * <p>Como el flujo "APROBADO" no tiene tareas humanas, el proceso debe llegar
     * hasta el fin y quedar en estado {@code STATE_COMPLETED}.</p>
     */
    @Test
    @DisplayName("Solicitud fuerte -> APROBADO y proceso COMPLETED")
    void solicitudFuerte_apruebaYcompleta() {
        // 1) Obtenemos el proceso por su processId (debe coincidir EXACTO con el del BPMN).
        Process<? extends Model> process = processes.processById("aprobacionCredito");
        assertThat(process)
                .as("El proceso 'aprobacionCredito' debe estar registrado por Kogito")
                .isNotNull();

        // 2) Construimos el modelo de entrada con una solicitud de perfil fuerte.
        SolicitudCredito solicitud = new SolicitudCredito(
                "Ana",          // nombre
                35,             // edad (dentro de 25-65: suma puntos)
                30000.0,        // ingresos mensuales altos
                10000.0,        // monto solicitado bajo (relación holgada ingresos/monto)
                12,             // plazo en meses
                false           // sin mora
        );

        Model model = process.createModel();
        Map<String, Object> params = new HashMap<>();
        params.put("solicitud", solicitud);
        model.fromMap(params);

        // 3) Creamos e iniciamos la instancia del proceso.
        ProcessInstance<? extends Model> instance = createAndStart(process, model);

        // 4) Verificaciones: el proceso debe haber COMPLETADO (sin tareas pendientes).
        assertThat(instance.status())
                .as("Una solicitud aprobada no tiene tareas humanas: el proceso debe completar")
                .isEqualTo(ProcessInstance.STATE_COMPLETED);

        // 5) Recuperamos la variable de salida "evaluacion" y validamos la decisión.
        Evaluacion evaluacion = extraerEvaluacion(instance);
        assertThat(evaluacion)
                .as("La variable de salida 'evaluacion' debe existir")
                .isNotNull();
        assertThat(evaluacion.getDecision())
                .as("Con perfil fuerte la decisión debe ser APROBADO")
                .isEqualTo("APROBADO");
        assertThat(evaluacion.getScore())
                .as("Un perfil APROBADO debe tener score >= 70")
                .isGreaterThanOrEqualTo(70);
    }

    /**
     * CASO LÍMITE (REVISIÓN): una solicitud de perfil intermedio debe caer en la zona
     * 50 &lt;= score &lt; 70, cuya decisión es REVISION. Ese camino abre una User Task
     * ("Revisión del analista", grupo: analistas), por lo que la instancia NO completa:
     * queda ACTIVA esperando que un humano atienda la tarea.
     *
     * <p>Verificamos que el estado sea {@code STATE_ACTIVE} y que exista exactamente
     * una tarea de trabajo (work item / user task) pendiente.</p>
     */
    @Test
    @DisplayName("Solicitud intermedia -> REVISION deja una User Task ACTIVA")
    void solicitudIntermedia_quedaEnRevision() {
        Process<? extends Model> process = processes.processById("aprobacionCredito");
        assertThat(process).isNotNull();

        // Perfil intermedio: ingresos moderados frente al monto, edad ok, sin mora.
        // El objetivo es caer en 50 <= score < 70 -> REVISION.
        SolicitudCredito solicitud = new SolicitudCredito(
                "Beto",         // nombre
                40,             // edad dentro de rango
                8000.0,         // ingresos moderados
                45000.0,        // monto alto: tensiona la relación ingresos/monto -> score intermedio
                24,             // plazo
                false           // sin mora
        );

        Model model = process.createModel();
        Map<String, Object> params = new HashMap<>();
        params.put("solicitud", solicitud);
        model.fromMap(params);

        ProcessInstance<? extends Model> instance = createAndStart(process, model);

        // En el camino REVISION hay una User Task: la instancia debe quedar ACTIVA.
        assertThat(instance.status())
                .as("El camino REVISION abre una User Task: la instancia debe quedar ACTIVE")
                .isEqualTo(ProcessInstance.STATE_ACTIVE);

        // Debe existir al menos una tarea de trabajo pendiente (la User Task del analista).
        List<WorkItem> workItems = instance.workItems();
        assertThat(workItems)
                .as("Debe haber una User Task pendiente para el analista")
                .isNotEmpty();

        // Validación adicional: la decisión calculada por el scoring fue REVISION.
        Evaluacion evaluacion = extraerEvaluacion(instance);
        assertThat(evaluacion).isNotNull();
        assertThat(evaluacion.getDecision())
                .as("El perfil intermedio debe producir decisión REVISION")
                .isEqualTo("REVISION");
        assertThat(evaluacion.getScore())
                .as("REVISION corresponde a 50 <= score < 70")
                .isGreaterThanOrEqualTo(50)
                .isLessThan(70);
    }

    // ------------------------------------------------------------------
    // Helpers privados (reducen ruido y centralizan el manejo de genéricos)
    // ------------------------------------------------------------------

    /**
     * Crea e inicia una instancia del proceso DENTRO de una Unidad de Trabajo.
     *
     * <p>{@link UnitOfWorkExecutor#executeInUnitOfWork} abre la UoW, ejecuta el bloque
     * (crear instancia + {@code start()}) y la confirma al terminar. Es el patrón
     * recomendado en las pruebas de procesos de Kogito en Spring Boot.</p>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private ProcessInstance<? extends Model> createAndStart(Process<? extends Model> process, Model model) {
        return UnitOfWorkExecutor.executeInUnitOfWork(
                application.unitOfWorkManager(),
                () -> {
                    // Tipo raw para evitar la ambigüedad createInstance(T) vs createInstance(Model)
                    // cuando T = Model: con el tipo crudo, Java elige el overload más específico.
                    ProcessInstance instance = ((Process) process).createInstance(model);
                    instance.start();
                    return instance;
                });
    }

    /**
     * Extrae la variable de salida "evaluacion" del mapa de variables de la instancia,
     * tolerando que Kogito la entregue ya como {@link Evaluacion} o como {@link Map}.
     */
    @SuppressWarnings("unchecked")
    private Evaluacion extraerEvaluacion(ProcessInstance<? extends Model> instance) {
        Map<String, Object> vars = instance.variables() != null
                ? instance.variables().toMap()
                : Collections.emptyMap();
        Object valor = vars.get("evaluacion");
        if (valor instanceof Evaluacion e) {
            return e;
        }
        if (valor instanceof Map<?, ?> map) {
            // Defensa por si la variable llega como Map (p. ej. estructura del DMN).
            Evaluacion e = new Evaluacion();
            Object score = ((Map<String, Object>) map).get("score");
            Object decision = ((Map<String, Object>) map).get("decision");
            Object motivo = ((Map<String, Object>) map).get("motivo");
            if (score instanceof Number n) {
                e.setScore(n.intValue());
            }
            if (decision != null) {
                e.setDecision(decision.toString());
            }
            if (motivo != null) {
                e.setMotivo(motivo.toString());
            }
            return e;
        }
        return null;
    }
}
