package com.academy.demo.model;

/**
 * Entidad que representa a un empleado.
 *
 * Por ahora no se persiste en base de datos: es un POJO sencillo
 * que se almacena en memoria desde el repositorio.
 */
public class Empleado {

	//ENCAPSULAR
	private Long id;
	private String nombre;
	private String apellido;
	private String puesto;
	private String departamento;
	private Double salario;
	private String email;

	public Empleado() {
	}

	public Empleado(Long id, String nombre, String apellido, String puesto,
			String departamento, Double salario, String email) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.puesto = puesto;
		this.departamento = departamento;
		this.salario = salario;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Empleado{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", puesto='" + puesto + '\'' +
				", departamento='" + departamento + '\'' +
				", salario=" + salario +
				", email='" + email + '\'' +
				'}';
	}

}
