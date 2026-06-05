package com.academy.kogito.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Implementacion por defecto de {@link MensajeriaGateway} que "entrega" los
 * mensajes escribiendolos en el log (consola).
 *
 * <p>Es la implementacion real que Spring inyecta en {@link NotificacionService}
 * cuando la aplicacion se ejecuta, de modo que el contexto arranca sin depender
 * de un canal externo (correo, SMS, push). En un sistema real se sustituiria por
 * un adaptador a la tecnologia concreta de mensajeria.</p>
 *
 * <p>En las pruebas unitarias NO se usa esta clase: alli {@link MensajeriaGateway}
 * se reemplaza por un mock de Mockito para verificar el mensaje enviado.</p>
 */
@Component
public class MensajeriaConsolaGateway implements MensajeriaGateway {

    private static final Logger log = LoggerFactory.getLogger(MensajeriaConsolaGateway.class);

    @Override
    public void enviar(String destinatario, String mensaje) {
        // En produccion: integrar con el canal real (email/SMS/push).
        log.info("[NOTIFICACION] -> {} : {}", destinatario, mensaje);
    }
}
