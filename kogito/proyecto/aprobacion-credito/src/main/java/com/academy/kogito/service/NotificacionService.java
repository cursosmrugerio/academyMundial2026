package com.academy.kogito.service;

import com.academy.kogito.model.Evaluacion;
import com.academy.kogito.model.SolicitudCredito;
import org.springframework.stereotype.Service;

/**
 * Servicio de dominio que NOTIFICA al solicitante el resultado de su evaluación
 * de crédito.
 *
 * <p>Es un caso de uso clásico para probar con Mockito: contiene lógica propia
 * (arma el mensaje según la decisión) y delega el envío real en el colaborador
 * externo {@link MensajeriaGateway}, que se inyecta por constructor.</p>
 *
 * <p>La inyección por constructor facilita las pruebas: en los tests se pasa un
 * mock de {@link MensajeriaGateway} y se verifica que {@code enviar(...)} se
 * invoque con el destinatario y el mensaje esperados.</p>
 */
@Service
public class NotificacionService {

    /** Colaborador externo encargado de entregar los mensajes. */
    private final MensajeriaGateway mensajeriaGateway;

    /**
     * Crea el servicio con su dependencia de mensajería.
     *
     * @param mensajeriaGateway gateway de envío de mensajes (inyectado por Spring
     *                          en producción y mockeado en las pruebas)
     */
    public NotificacionService(MensajeriaGateway mensajeriaGateway) {
        this.mensajeriaGateway = mensajeriaGateway;
    }

    /**
     * Construye un mensaje en español acorde a la decisión de la evaluación y
     * lo envía al solicitante a través del {@link MensajeriaGateway}.
     *
     * @param s solicitud de crédito (de ella se toma el nombre del destinatario)
     * @param e evaluación con la decisión, el score y el motivo
     */
    public void notificarResultado(SolicitudCredito s, Evaluacion e) {
        // El destinatario es el nombre del solicitante.
        String destinatario = s.getNombre();

        // Se arma el cuerpo del mensaje según la decisión devuelta por el scoring.
        String mensaje;
        String decision = e.getDecision();

        if ("APROBADO".equals(decision)) {
            mensaje = "Hola " + destinatario + ", ¡felicidades! Tu solicitud de crédito por "
                    + e.getScore() + " puntos fue APROBADA. " + e.getMotivo();
        } else if ("REVISION".equals(decision)) {
            mensaje = "Hola " + destinatario + ", tu solicitud de crédito quedó EN REVISIÓN. "
                    + "Un analista la revisará pronto. " + e.getMotivo();
        } else if ("RECHAZADO".equals(decision)) {
            mensaje = "Hola " + destinatario + ", lamentamos informarte que tu solicitud de crédito fue RECHAZADA. "
                    + e.getMotivo();
        } else {
            // Caso defensivo: decisión desconocida o nula.
            mensaje = "Hola " + destinatario + ", tu solicitud de crédito fue procesada. " + e.getMotivo();
        }

        // Se delega el envío real al colaborador externo (mockeable en pruebas).
        mensajeriaGateway.enviar(destinatario, mensaje);
    }
}
