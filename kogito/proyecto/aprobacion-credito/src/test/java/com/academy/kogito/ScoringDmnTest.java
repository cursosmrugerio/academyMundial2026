package com.academy.kogito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.kogito.decision.DecisionModels;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;
import org.kie.kogito.dmn.DmnDecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas de la DECISIÓN DMN {@code scoringCredito}.
 *
 * <p>Se levanta el contexto de Spring Boot para que Kogito registre los modelos DMN.
 * Se inyecta el bean {@link DecisionModels} y se obtiene el modelo concreto por su
 * {@code namespace} y {@code name} (deben coincidir EXACTO con los del archivo .dmn):</p>
 * <ul>
 *   <li>namespace: {@code https://academy.com/dmn/scoringCredito}</li>
 *   <li>name: {@code scoringCredito}</li>
 * </ul>
 *
 * <p>El patrón de evaluación es:</p>
 * <ol>
 *   <li>{@code DecisionModel modelo = decisionModels.getDecisionModel(NAMESPACE, NAME);}</li>
 *   <li>{@code DMNContext ctx = modelo.newContext(mapaDeEntradas);}</li>
 *   <li>{@code DMNResult resultado = modelo.evaluateAll(ctx);}</li>
 *   <li>Leer el resultado de la decisión "evaluacion" del contexto resultante.</li>
 * </ol>
 */
@SpringBootTest
@DisplayName("Decisión DMN scoringCredito")
class ScoringDmnTest {

    /** Namespace del modelo DMN; debe coincidir con el atributo namespace del .dmn. */
    private static final String NAMESPACE = "https://academy.com/dmn/scoringCredito";

    /** Nombre del modelo DMN; debe coincidir con el atributo name del .dmn. */
    private static final String NAME = "scoringCredito";

    /** Catálogo de modelos DMN generado por Kogito. */
    @Autowired
    DecisionModels decisionModels;

    /**
     * CASO APROBADO: una solicitud de perfil fuerte debe producir score >= 70
     * y decisión "APROBADO".
     */
    @Test
    @DisplayName("Perfil fuerte -> decision APROBADO y score >= 70")
    void perfilFuerte_aprobado() {
        // Mapa "solicitud" con los campos que el Input Data del DMN espera.
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("nombre", "Ana");
        solicitud.put("edad", 35);
        solicitud.put("ingresosMensuales", 30000.0);
        solicitud.put("montoSolicitado", 10000.0);
        solicitud.put("plazoMeses", 12);
        solicitud.put("tieneMora", false);

        Map<String, Object> resultado = evaluar(solicitud);

        assertThat(resultado)
                .as("La decisión 'evaluacion' debe estar presente en el resultado")
                .isNotNull();
        assertThat(asString(resultado.get("decision")))
                .as("Perfil fuerte -> APROBADO")
                .isEqualTo("APROBADO");
        assertThat(asInt(resultado.get("score")))
                .as("APROBADO requiere score >= 70")
                .isGreaterThanOrEqualTo(70);
    }

    /**
     * CASO RECHAZADO: una solicitud de perfil débil (ingresos bajos, monto alto,
     * con mora y edad fuera del rango premiado) debe producir score &lt; 50 y
     * decisión "RECHAZADO".
     */
    @Test
    @DisplayName("Perfil débil -> decision RECHAZADO y score < 50")
    void perfilDebil_rechazado() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("nombre", "Caro");
        solicitud.put("edad", 70);               // fuera del rango 25-65: penaliza
        solicitud.put("ingresosMensuales", 2000.0); // ingresos bajos
        solicitud.put("montoSolicitado", 50000.0);  // monto muy alto
        solicitud.put("plazoMeses", 48);
        solicitud.put("tieneMora", true);            // con mora: penaliza fuerte

        Map<String, Object> resultado = evaluar(solicitud);

        assertThat(resultado).isNotNull();
        assertThat(asString(resultado.get("decision")))
                .as("Perfil débil -> RECHAZADO")
                .isEqualTo("RECHAZADO");
        assertThat(asInt(resultado.get("score")))
                .as("RECHAZADO corresponde a score < 50")
                .isLessThan(50);
    }

    // ------------------------------------------------------------------
    // Helpers privados
    // ------------------------------------------------------------------

    /**
     * Evalúa el modelo DMN con la "solicitud" dada y devuelve el resultado de la
     * decisión "evaluacion" como {@link Map} (score, decision, motivo).
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> evaluar(Map<String, Object> solicitud) {
        // 1) Obtenemos el modelo DMN por namespace + name.
        DmnDecisionModel modelo =
                (DmnDecisionModel) decisionModels.getDecisionModel(NAMESPACE, NAME);
        assertThat(modelo)
                .as("El modelo DMN 'scoringCredito' debe estar registrado")
                .isNotNull();

        // 2) Construimos el contexto de entrada con la variable "solicitud".
        Map<String, Object> entradas = new HashMap<>();
        entradas.put("solicitud", solicitud);
        DMNContext contexto = modelo.newContext(entradas);

        // 3) Evaluamos todas las decisiones del modelo.
        DMNResult dmnResult = modelo.evaluateAll(contexto);

        // 4) Verificamos que no haya errores en la evaluación.
        assertThat(dmnResult.hasErrors())
                .as("La evaluación DMN no debe arrojar errores: %s", dmnResult.getMessages())
                .isFalse();

        // 5) Recuperamos la salida de la decisión "evaluacion".
        Object salida = dmnResult.getContext().get("evaluacion");
        if (salida instanceof Map<?, ?> map) {
            return (Map<String, Object>) map;
        }
        return null;
    }

    /** Convierte de forma segura un valor numérico del resultado DMN a int. */
    private int asInt(Object valor) {
        return valor instanceof Number n ? n.intValue() : Integer.MIN_VALUE;
    }

    /** Convierte de forma segura un valor de texto del resultado DMN a String. */
    private String asString(Object valor) {
        return valor != null ? valor.toString() : null;
    }
}
