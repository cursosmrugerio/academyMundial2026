package com.academy.kogito.service;

/**
 * Puerto (interface) hacia un COLABORADOR EXTERNO de mensajería.
 *
 * <p>Representa el sistema que realmente entrega los mensajes al solicitante
 * (por ejemplo: correo, SMS o notificación push). Al definirlo como interface,
 * desacoplamos la lógica de negocio de la tecnología concreta de envío.</p>
 *
 * <p>En las pruebas unitarias de {@link NotificacionService} esta interface se
 * MOCKEA con Mockito, de modo que podemos verificar QUÉ mensaje se enviaría
 * sin depender de un canal real.</p>
 */
public interface MensajeriaGateway {

    /**
     * Envía un mensaje a un destinatario.
     *
     * @param destinatario identificador del destinatario (nombre, email, teléfono, etc.)
     * @param mensaje      contenido del mensaje a entregar
     */
    void enviar(String destinatario, String mensaje);
}
