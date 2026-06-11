package com.curso.v1;

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

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public void setAsiganda(String asiganda) {
		this.asiganda = asiganda;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaTermino(LocalDate fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public void setCompletada(boolean completada) {
		this.completada = completada;
	}

	public void setDescripcion(StringBuilder descripcion) {
		this.descripcion = descripcion;
	}

	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	

}
