package com.academy.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.academy.demo.model.Empleado;
import com.academy.demo.repository.EmpleadoRepository;

/**
 * Capa de servicio: contiene la logica de negocio del CRUD de empleados.
 *
 * El controller habla con el service y el service habla con el repository.
 * Asi cada capa tiene una unica responsabilidad.
 */
@Service
public class EmpleadoService {

	private final EmpleadoRepository empleadoRepository;

	// Inyeccion de dependencias por constructor (la forma recomendada).
	public EmpleadoService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public List<Empleado> listarTodos() {
		return empleadoRepository.findAll();
	}

	public Optional<Empleado> buscarPorId(Long id) {
		return empleadoRepository.findById(id);
	}

	public Empleado crear(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public Optional<Empleado> actualizar(Long id, Empleado empleado) {
		return empleadoRepository.update(id, empleado);
	}

	public boolean eliminar(Long id) {
		return empleadoRepository.deleteById(id);
	}

}
