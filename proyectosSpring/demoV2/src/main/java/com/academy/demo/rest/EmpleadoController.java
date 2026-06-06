package com.academy.demo.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.demo.model.Empleado;
import com.academy.demo.service.EmpleadoService;

/**
 * Controlador REST del CRUD de empleados.
 *
 * Endpoints (base: /empleados):
 *   GET    /empleados       -> lista todos
 *   GET    /empleados/{id}  -> obtiene uno
 *   POST   /empleados       -> crea uno
 *   PUT    /empleados/{id}  -> actualiza uno
 *   DELETE /empleados/{id}  -> elimina uno
 */
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

	private final EmpleadoService empleadoService;

	//INYECCION POR CONSTRUCTOR
	public EmpleadoController(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@GetMapping
	public List<Empleado> listar() {
		return empleadoService.listarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
		return empleadoService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Empleado> crear(@RequestBody Empleado empleado) {
		Empleado creado = empleadoService.crear(empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
		return empleadoService.actualizar(id, empleado)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (empleadoService.eliminar(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}

/**

# Listar los 10                         
  curl http://localhost:7777/empleados

  # Obtener uno
  curl http://localhost:7777/empleados/3
                                          
  # Crear
  curl -X POST http://localhost:7777/empleados \
    -H "Content-Type: application/json" \
    -d '{"nombre":"Pedro","apellido":"Gomez","puesto":"Becario","departamento":"
  Tecnologia","salario":20000,"email":"pedro@academy.com"}'

  # Actualizar
  curl -X PUT http://localhost:7777/empleados/3 \
    -H "Content-Type: application/json" \
    -d '{"nombre":"Maria","apellido":"Lopez","puesto":"CTO","departamento":
    "Direccion","salario":90000,"email":"maria.lopez@academy.com"}'

  # Eliminar
  curl -X DELETE http://localhost:7777/empleados/5
  
  java -jar demoV2-0.0.1-SNAPSHOT.jar //EJECUTAR JAR GENERADO

**/