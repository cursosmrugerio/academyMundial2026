package com.curso.v2;

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
		this.id = id;
	}

	public TaskBuilder setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
		return this;
	}

	public TaskBuilder setResponsable(String responsable) {
		this.responsable = responsable;
		return this;
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
				id,
				nombreTarea,
				responsable,
				asiganda,
				fechaInicio,
				fechaTermino,
				completada,
				descripcion,
				cancelada,
				presupuesto	
		);
	}

}
