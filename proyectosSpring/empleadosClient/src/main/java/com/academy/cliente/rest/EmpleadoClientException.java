package com.academy.cliente.rest;

/**
 * Error de comunicación con el API de empleados: red caída,
 * código de respuesta inesperado o JSON ilegible.
 *
 * Un 404 NO lanza esta excepción: es un resultado normal del negocio
 * y el cliente lo traduce a Optional.empty() o false.
 */
public class EmpleadoClientException extends RuntimeException {

	public EmpleadoClientException(String mensaje) {
		super(mensaje);
	}

	public EmpleadoClientException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}

}
