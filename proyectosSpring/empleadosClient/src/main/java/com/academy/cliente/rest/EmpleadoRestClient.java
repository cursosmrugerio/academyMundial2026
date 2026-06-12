package com.academy.cliente.rest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.academy.cliente.model.Empleado;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cliente REST del CRUD de empleados de demoV2 (base: /empleados).
 *
 * Se construye con su Builder anidado:
 *
 *   EmpleadoRestClient cliente = EmpleadoRestClient.builder()
 *           .setBaseUrl("http://localhost:7777")
 *           .setTimeout(Duration.ofSeconds(5))
 *           .build();
 *
 * La baseUrl es OBLIGATORIA (build() la valida); el timeout es
 * opcional y tiene valor por defecto. Una vez construido, el cliente
 * es INMUTABLE: todos sus campos son final.
 *
 * Por debajo usa java.net.http.HttpClient: tanto HttpClient.newBuilder()
 * como HttpRequest.newBuilder() son el MISMO patrón Builder, aplicado
 * por el propio JDK.
 */
public final class EmpleadoRestClient {

	private final String baseUrl;
	private final Duration timeout;
	private final HttpClient httpClient;
	private final ObjectMapper mapper;

	//CONSTRUCTOR PRIVADO: solo el Builder puede crear instancias
	private EmpleadoRestClient(Builder builder) {
		this.baseUrl = builder.baseUrl;
		this.timeout = builder.timeout;
		this.httpClient = HttpClient.newBuilder() //BUILDER DEL JDK
				.connectTimeout(builder.timeout)
				.build();
		this.mapper = new ObjectMapper();
	}

	public static Builder builder() {
		return new Builder(); //CREAR AYUDANTE
	}

	// ---------- Operaciones del CRUD ----------

	/** GET /empleados -> lista todos (espera 200). */
	public List<Empleado> listar() {
		HttpRequest peticion = peticionBase("/empleados").GET().build();
		HttpResponse<String> respuesta = enviar(peticion);
		verificarCodigo(respuesta, 200);
		return leerLista(respuesta.body());
	}

	/** GET /empleados/{id} -> Optional.empty() si el servidor responde 404. */
	public Optional<Empleado> obtener(Long id) {
		HttpRequest peticion = peticionBase("/empleados/" + id).GET().build();
		HttpResponse<String> respuesta = enviar(peticion);
		if (respuesta.statusCode() == 404) {
			return Optional.empty();
		}
		verificarCodigo(respuesta, 200);
		return Optional.of(leer(respuesta.body()));
	}

	/** POST /empleados -> el empleado creado, con el id que asignó el servidor (espera 201). */
	public Empleado crear(Empleado empleado) {
		HttpRequest peticion = peticionBase("/empleados")
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(escribir(empleado)))
				.build();
		HttpResponse<String> respuesta = enviar(peticion);
		verificarCodigo(respuesta, 201);
		return leer(respuesta.body());
	}

	/** PUT /empleados/{id} -> Optional.empty() si no existe (404). */
	public Optional<Empleado> actualizar(Long id, Empleado empleado) {
		HttpRequest peticion = peticionBase("/empleados/" + id)
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(escribir(empleado)))
				.build();
		HttpResponse<String> respuesta = enviar(peticion);
		if (respuesta.statusCode() == 404) {
			return Optional.empty();
		}
		verificarCodigo(respuesta, 200);
		return Optional.of(leer(respuesta.body()));
	}

	/** DELETE /empleados/{id} -> true si lo eliminó (204), false si no existía (404). */
	public boolean eliminar(Long id) {
		HttpRequest peticion = peticionBase("/empleados/" + id).DELETE().build();
		HttpResponse<String> respuesta = enviar(peticion);
		if (respuesta.statusCode() == 404) {
			return false;
		}
		verificarCodigo(respuesta, 204);
		return true;
	}

	// ---------- Ayudantes privados ----------

	private HttpRequest.Builder peticionBase(String ruta) {
		//HttpRequest.newBuilder(): el patrón Builder en el propio JDK
		return HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + ruta))
				.timeout(timeout)
				.header("Accept", "application/json");
	}

	private HttpResponse<String> enviar(HttpRequest peticion) {
		try {
			return httpClient.send(peticion, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new EmpleadoClientException("No se pudo conectar con " + peticion.uri(), e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new EmpleadoClientException("Petición interrumpida: " + peticion.uri(), e);
		}
	}

	private void verificarCodigo(HttpResponse<String> respuesta, int esperado) {
		if (respuesta.statusCode() != esperado) {
			throw new EmpleadoClientException("Respuesta inesperada de " + respuesta.uri()
					+ ": " + respuesta.statusCode() + " (se esperaba " + esperado + ")");
		}
	}

	private Empleado leer(String json) {
		try {
			return mapper.readValue(json, Empleado.class);
		} catch (IOException e) {
			throw new EmpleadoClientException("No se pudo leer la respuesta JSON: " + json, e);
		}
	}

	private List<Empleado> leerLista(String json) {
		try {
			return mapper.readValue(json, new TypeReference<List<Empleado>>() {});
		} catch (IOException e) {
			throw new EmpleadoClientException("No se pudo leer la respuesta JSON: " + json, e);
		}
	}

	private String escribir(Empleado empleado) {
		try {
			return mapper.writeValueAsString(empleado);
		} catch (IOException e) {
			throw new EmpleadoClientException("No se pudo serializar el empleado: " + empleado, e);
		}
	}

	/**
	 * AYUDANTE: acumula la configuración del cliente y la valida en build().
	 * Static nested class con constructor privado (estilo v4 del curso).
	 */
	public static class Builder {

		private String baseUrl;                           //OBLIGATORIA
		private Duration timeout = Duration.ofSeconds(5); //OPCIONAL, con valor por defecto

		private Builder() {
		}

		public Builder setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this; //REGRESATE A TI MISMO
		}

		public Builder setTimeout(Duration timeout) {
			this.timeout = timeout;
			return this;
		}

		public EmpleadoRestClient build() {
			if (baseUrl == null || baseUrl.isBlank()) {
				throw new IllegalStateException("baseUrl es obligatoria para construir el cliente");
			}
			if (baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			return new EmpleadoRestClient(this);
		}

	}

}
