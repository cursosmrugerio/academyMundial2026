package com.curso.v3;

import java.time.LocalDate;

public class TaskBuilder { //AYUDANTE
	
	private int id;
	private String nombreTarea;
	private String responsable;
	private String asiganda;
	private LocalDate fechaInicio;
	private LocalDate fechaTermino;
	private boolean completada;
	private StringBuilder descripcion;
	private boolean cancelada;
	private double presupuesto;
	
	public TaskBuilder(int id) {
		this.id = id; //CREAR AYUDANTE
	}

	public TaskBuilder setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea; //ASIGNATE NOMBRE DE TAREA
		return this; //REGRESATE A TI MISMO
	}

	public TaskBuilder setResponsable(String responsable) {
		this.responsable = responsable; //ASIGNATE RESPONSABLE
		return this; //REGRESATE A TI MISMO
	}

	public TaskBuilder setAsiganda(String asiganda) {
		this.asiganda = asiganda;
		return this;
	}

	public TaskBuilder setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
		return this;
	}

	public TaskBuilder setFechaTermino(LocalDate fechaTermino) {
		this.fechaTermino = fechaTermino;
		return this;
	}

	public TaskBuilder setCompletada(boolean completada) {
		this.completada = completada;
		return this;
	}

	public TaskBuilder setDescripcion(StringBuilder descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public TaskBuilder setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
		return this;
	}

	public TaskBuilder setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
		return this;
	}
	
	Task build() {
		return new Task(
				this.id,
				nombreTarea,
				this.responsable,
				asiganda,
				fechaInicio,
				this.fechaTermino,
				completada,
				descripcion,
				cancelada,
				presupuesto	
		);
	}

}
