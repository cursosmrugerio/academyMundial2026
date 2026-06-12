package com.academy.cliente.model;

/**
 * Empleado tal como lo expone el API REST de demoV2.
 *
 * Mantiene constructor vacío y setters para que Jackson pueda
 * deserializar las respuestas JSON, y además ofrece un Builder
 * anidado (static nested class, estilo v4 del curso) para armar
 * de forma fluida los cuerpos de POST y PUT.
 */
public class Empleado {

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

	public static Builder builder() {
		return new Builder(); //CREAR AYUDANTE
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

	/**
	 * AYUDANTE: construye un Empleado paso a paso.
	 * El id no se establece aquí porque lo asigna el servidor al crear.
	 */
	public static class Builder {

		private Long id;
		private String nombre;
		private String apellido;
		private String puesto;
		private String departamento;
		private Double salario;
		private String email;

		private Builder() {
		}

		public Builder setId(Long id) {
			this.id = id;
			return this; //REGRESATE A TI MISMO
		}

		public Builder setNombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		public Builder setApellido(String apellido) {
			this.apellido = apellido;
			return this;
		}

		public Builder setPuesto(String puesto) {
			this.puesto = puesto;
			return this;
		}

		public Builder setDepartamento(String departamento) {
			this.departamento = departamento;
			return this;
		}

		public Builder setSalario(Double salario) {
			this.salario = salario;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Empleado build() {
			return new Empleado(id, nombre, apellido, puesto,
					departamento, salario, email);
		}

	}

}
