package com.academy.cliente;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.academy.cliente.model.Empleado;
import com.academy.cliente.rest.EmpleadoRestClient;

/**
 * Demo del cliente REST construido con el patrón Builder.
 *
 * Requiere que demoV2 esté corriendo en http://localhost:7777
 * (mvn spring-boot:run  o  java -jar demoV2-0.0.1-SNAPSHOT.jar).
 *
 * El patrón aparece en TRES lugares:
 *   1. EmpleadoRestClient.builder() -> configura el cliente (nuestro builder)
 *   2. Empleado.builder()           -> arma los cuerpos de POST/PUT (nuestro builder)
 *   3. HttpRequest.newBuilder()     -> dentro del cliente, el builder del JDK
 */
public class Principal {

	public static void main(String[] args) {

		//1. CONSTRUIR EL CLIENTE CON SU BUILDER: configuración fluida, validada e inmutable
		EmpleadoRestClient cliente = EmpleadoRestClient.builder()
				.setBaseUrl("http://localhost:7777")
				.setTimeout(Duration.ofSeconds(5))
				.build();

		//2. LISTAR TODOS (GET /empleados)
		System.out.println("== Empleados iniciales ==");
		List<Empleado> iniciales = cliente.listar();
		iniciales.forEach(System.out::println);
		System.out.println("Total: " + iniciales.size());

		//3. CREAR (POST /empleados): el cuerpo también se arma con Builder
		Empleado nuevo = Empleado.builder()
				.setNombre("Pedro")
				.setApellido("Gomez")
				.setPuesto("Becario")
				.setDepartamento("Tecnologia")
				.setSalario(20000.0)
				.setEmail("pedro@academy.com")
				.build();

		Empleado creado = cliente.crear(nuevo);
		System.out.println("\n== Creado (id asignado por el servidor) ==");
		System.out.println(creado);

		//4. OBTENER POR ID (GET /empleados/{id})
		System.out.println("\n== Obtenido por id " + creado.getId() + " ==");
		cliente.obtener(creado.getId()).ifPresent(System.out::println);

		//5. ACTUALIZAR (PUT /empleados/{id}): Pedro asciende
		Empleado ascendido = Empleado.builder()
				.setNombre("Pedro")
				.setApellido("Gomez")
				.setPuesto("Arquitecto")
				.setDepartamento("Tecnologia")
				.setSalario(75000.0)
				.setEmail("pedro@academy.com")
				.build();

		System.out.println("\n== Actualizado ==");
		Optional<Empleado> actualizado = cliente.actualizar(creado.getId(), ascendido);
		actualizado.ifPresent(System.out::println);

		//6. EL 404 NO ES ERROR: se traduce a Optional vacío
		System.out.println("\n== Buscar un id inexistente (9999) ==");
		Optional<Empleado> inexistente = cliente.obtener(9999L);
		System.out.println("¿Existe? " + inexistente.isPresent());

		//7. ELIMINAR (DELETE /empleados/{id}): true la primera vez, false la segunda
		System.out.println("\n== Eliminar al empleado " + creado.getId() + " ==");
		System.out.println("Primera vez:  " + cliente.eliminar(creado.getId()));
		System.out.println("Segunda vez:  " + cliente.eliminar(creado.getId()));

		//8. LISTAR FINAL: todo quedó como al inicio
		System.out.println("\n== Empleados finales ==");
		List<Empleado> finales = cliente.listar();
		finales.forEach(System.out::println);
		System.out.println("Total: " + finales.size());
	}

}

/**

  # Compilar
  mvn compile

  # Ejecutar la demo (demoV2 debe estar corriendo en el puerto 7777)
  mvn exec:java

**/
