package com.academy.kogito;

import com.academy.kogito.model.Evaluacion;
import com.academy.kogito.model.SolicitudCredito;
import com.academy.kogito.service.MensajeriaGateway;
import com.academy.kogito.service.NotificacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Pruebas UNITARIAS PURAS de {@link NotificacionService} con JUnit 5 + Mockito.
 *
 * <p>NO se levanta Spring ({@code @SpringBootTest}): el servicio se instancia a mano
 * pasándole un MOCK de su colaborador {@link MensajeriaGateway}. Así la prueba es
 * rápida y aislada, y podemos VERIFICAR qué mensaje se enviaría sin tocar un canal real.</p>
 *
 * <p>{@link MockitoExtension} integra Mockito con JUnit 5: inicializa los campos
 * anotados con {@link Mock}/{@link Captor} y, por defecto, valida los stubbings
 * innecesarios (strict stubs).</p>
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NotificacionService (Mockito puro)")
class NotificacionServiceTest {

    /** Mock del colaborador externo: no envía nada real, solo registra las llamadas. */
    @Mock
    MensajeriaGateway mensajeriaGateway;

    /**
     * Captor para inspeccionar los argumentos con los que se invocó
     * {@code enviar(destinatario, mensaje)}.
     */
    @Captor
    ArgumentCaptor<String> destinatarioCaptor;

    @Captor
    ArgumentCaptor<String> mensajeCaptor;

    /** Construye una solicitud reutilizable para los tests. */
    private SolicitudCredito solicitud(String nombre) {
        return new SolicitudCredito(nombre, 35, 30000.0, 10000.0, 12, false);
    }

    /** Construye una evaluación con la decisión indicada. */
    private Evaluacion evaluacion(int score, String decision, String motivo) {
        Evaluacion e = new Evaluacion();
        e.setScore(score);
        e.setDecision(decision);
        e.setMotivo(motivo);
        return e;
    }

    /**
     * Verifica que, ante una decisión APROBADO, el servicio invoque al gateway una
     * vez y que el mensaje capturado mencione al destinatario y la decisión.
     *
     * <p>Demuestra el uso de {@link ArgumentCaptor} y {@code verify(...)}.</p>
     */
    @Test
    @DisplayName("APROBADO -> enviar() recibe un mensaje que menciona la aprobación")
    void notificarAprobado_enviaMensajeConDecision() {
        // Arrange: servicio bajo prueba con el mock inyectado por constructor.
        NotificacionService service = new NotificacionService(mensajeriaGateway);
        SolicitudCredito s = solicitud("Ana");
        Evaluacion e = evaluacion(85, "APROBADO", "Buen perfil crediticio");

        // Act
        service.notificarResultado(s, e);

        // Assert: se llamó enviar() exactamente una vez; capturamos sus argumentos.
        verify(mensajeriaGateway, times(1))
                .enviar(destinatarioCaptor.capture(), mensajeCaptor.capture());

        assertThat(destinatarioCaptor.getValue())
                .as("El destinatario debe ser el nombre del solicitante")
                .isEqualTo("Ana");

        String mensaje = mensajeCaptor.getValue();
        assertThat(mensaje)
                .as("El mensaje debe personalizarse con el nombre")
                .contains("Ana");
        assertThat(mensaje)
                .as("El mensaje debe reflejar la decisión APROBADA")
                .containsIgnoringCase("APROBAD");

        // No deben quedar interacciones adicionales sin verificar.
        verifyNoMoreInteractions(mensajeriaGateway);
    }

    /**
     * Verifica que una decisión REVISION genere un mensaje que menciona la revisión.
     */
    @Test
    @DisplayName("REVISION -> el mensaje menciona la revisión del analista")
    void notificarRevision_mensajeMencionaRevision() {
        NotificacionService service = new NotificacionService(mensajeriaGateway);

        service.notificarResultado(
                solicitud("Beto"),
                evaluacion(60, "REVISION", "Requiere revisión manual"));

        verify(mensajeriaGateway).enviar(destinatarioCaptor.capture(), mensajeCaptor.capture());

        assertThat(destinatarioCaptor.getValue()).isEqualTo("Beto");
        assertThat(mensajeCaptor.getValue())
                .as("El mensaje debe mencionar la revisión")
                .containsIgnoringCase("REVIS");
    }

    /**
     * Verifica que una decisión RECHAZADO genere un mensaje que menciona el rechazo.
     */
    @Test
    @DisplayName("RECHAZADO -> el mensaje menciona el rechazo")
    void notificarRechazado_mensajeMencionaRechazo() {
        NotificacionService service = new NotificacionService(mensajeriaGateway);

        service.notificarResultado(
                solicitud("Caro"),
                evaluacion(30, "RECHAZADO", "Score insuficiente"));

        verify(mensajeriaGateway).enviar(destinatarioCaptor.capture(), mensajeCaptor.capture());

        assertThat(mensajeCaptor.getValue())
                .as("El mensaje debe mencionar el rechazo")
                .containsIgnoringCase("RECHAZAD");
    }

    /**
     * Demuestra el uso de {@code doThrow().when(...)}: si el gateway falla,
     * la excepción se propaga (el servicio no la traga). Esto ilustra el stubbing
     * de un método {@code void} con Mockito.
     */
    @Test
    @DisplayName("Si el gateway lanza excepción, esta se propaga")
    void siGatewayFalla_propagaExcepcion() {
        NotificacionService service = new NotificacionService(mensajeriaGateway);

        // Stub: la próxima llamada a enviar() lanzará una excepción.
        doThrow(new RuntimeException("canal caído"))
                .when(mensajeriaGateway).enviar(org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString());

        try {
            service.notificarResultado(
                    solicitud("Dani"),
                    evaluacion(90, "APROBADO", "ok"));
            org.junit.jupiter.api.Assertions.fail("Debió propagarse la excepción del gateway");
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage()).isEqualTo("canal caído");
        }

        // Se intentó enviar una vez; no hubo reintentos.
        verify(mensajeriaGateway, times(1))
                .enviar(org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString());
    }

    /**
     * Comprobación de sanidad: sin invocar el servicio, no debe haber interacciones
     * con el gateway (uso de {@code never()}).
     */
    @Test
    @DisplayName("Sin invocar el servicio, no hay envíos")
    void sinInvocar_noHayEnvios() {
        verify(mensajeriaGateway, never())
                .enviar(org.mockito.ArgumentMatchers.anyString(),
                        org.mockito.ArgumentMatchers.anyString());
    }
}
