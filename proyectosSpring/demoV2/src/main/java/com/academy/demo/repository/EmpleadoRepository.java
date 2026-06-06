package com.academy.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.academy.demo.model.Empleado;

import jakarta.annotation.PostConstruct;

/**
 * Repositorio en memoria de empleados.
 *
 * Como todavia no hay base de datos, los datos se guardan en una lista
 * y se inicializan con 10 empleados de prueba al arrancar la aplicacion.
 * El dia que se conecte una base de datos, esta clase se reemplaza por
 * un repositorio de Spring Data sin tocar el service ni el controller.
 */
@Repository
public class EmpleadoRepository {

	private final List<Empleado> empleados = new ArrayList<>();

	/** Generador de IDs: arranca en 11 porque los 10 primeros ya estan ocupados. */
	private final AtomicLong secuenciaId = new AtomicLong(11);

	/**
	 * Carga los 10 empleados de prueba.
	 * @PostConstruct se ejecuta una sola vez, justo despues de crear el bean.
	 */
	@PostConstruct
	private void cargarDatosIniciales() {
		empleados.add(new Empleado(1L, "Ana", "Garcia", "Desarrolladora Backend", "Tecnologia", 45000.0, "ana.garcia@academy.com"));
		empleados.add(new Empleado(2L, "Luis", "Martinez", "Desarrollador Frontend", "Tecnologia", 42000.0, "luis.martinez@academy.com"));
		empleados.add(new Empleado(3L, "Maria", "Lopez", "Arquitecta de Software", "Tecnologia", 60000.0, "maria.lopez@academy.com"));
		empleados.add(new Empleado(4L, "Carlos", "Hernandez", "DevOps", "Infraestructura", 50000.0, "carlos.hernandez@academy.com"));
		empleados.add(new Empleado(5L, "Sofia", "Ramirez", "QA Engineer", "Calidad", 40000.0, "sofia.ramirez@academy.com"));
		empleados.add(new Empleado(6L, "Jorge", "Torres", "Scrum Master", "Gestion", 52000.0, "jorge.torres@academy.com"));
		empleados.add(new Empleado(7L, "Lucia", "Flores", "Disenadora UX", "Diseno", 43000.0, "lucia.flores@academy.com"));
		empleados.add(new Empleado(8L, "Diego", "Sanchez", "Data Engineer", "Datos", 55000.0, "diego.sanchez@academy.com"));
		empleados.add(new Empleado(9L, "Valentina", "Diaz", "Product Owner", "Gestion", 58000.0, "valentina.diaz@academy.com"));
		empleados.add(new Empleado(10L, "Andres", "Romero", "Desarrollador Mobile", "Tecnologia", 47000.0, "andres.romero@academy.com"));
	}

	/** Devuelve todos los empleados. */
	public List<Empleado> findAll() {
		return new ArrayList<>(empleados);
	}

	/** Busca un empleado por su id. */
	public Optional<Empleado> findById(Long id) {
		return empleados.stream()
				.filter(empleado -> empleado.getId().equals(id))
				.findFirst();
	}

	/** Crea un nuevo empleado asignandole un id automatico. */
	public Empleado save(Empleado empleado) {
		empleado.setId(secuenciaId.getAndIncrement());
		empleados.add(empleado);
		return empleado;
	}

	/** Reemplaza los datos de un empleado existente. */
	public Optional<Empleado> update(Long id, Empleado datos) {
		return findById(id).map(existente -> {
			existente.setNombre(datos.getNombre());
			existente.setApellido(datos.getApellido());
			existente.setPuesto(datos.getPuesto());
			existente.setDepartamento(datos.getDepartamento());
			existente.setSalario(datos.getSalario());
			existente.setEmail(datos.getEmail());
			return existente;
		});
	}

	/** Elimina un empleado por id. Devuelve true si existia. */
	public boolean deleteById(Long id) {
		return empleados.removeIf(empleado -> empleado.getId().equals(id));
	}

}
