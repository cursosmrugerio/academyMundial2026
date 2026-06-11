package com.curso.v2;

import java.time.LocalDate;

public class Task {
	
	int id;
	String nombreTarea;
	String responsable;
	String asiganda;
	LocalDate fechaInicio;
	LocalDate fechaTermino;
	boolean completada;
	StringBuilder descripcion;
	boolean cancelada;
	double presupuesto;
	
	public Task(int id, String nombreTarea, String responsable, String asiganda, LocalDate fechaInicio,
			LocalDate fechaTermino, boolean completada, StringBuilder descripcion, boolean cancelada,
			double presupuesto) {
		super();
		this.id = id;
		this.nombreTarea = nombreTarea;
		this.responsable = responsable;
		this.asiganda = asiganda;
		this.fechaInicio = fechaInicio;
		this.fechaTermino = fechaTermino;
		this.completada = completada;
		this.descripcion = descripcion;
		this.cancelada = cancelada;
		this.presupuesto = presupuesto;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", nombreTarea=" + nombreTarea + ", responsable=" + responsable + ", asiganda="
				+ asiganda + ", fechaInicio=" + fechaInicio + ", fechaTermino=" + fechaTermino + ", completada="
				+ completada + ", descripcion=" + descripcion + ", cancelada=" + cancelada + ", presupuesto="
				+ presupuesto + "]";
	}
	
	

}
