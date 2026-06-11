package com.curso.v0;

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

	public Task(int id, String nombreTarea, String responsable, String asiganda, LocalDate fechaInicio,
			StringBuilder descripcion) {
		super();
		this.id = id;
		this.nombreTarea = nombreTarea;
		this.responsable = responsable;
		this.asiganda = asiganda;
		this.fechaInicio = fechaInicio;
		this.descripcion = descripcion;
	}

	public Task(int id, String nombreTarea, String responsable, String asiganda, LocalDate fechaInicio,
			StringBuilder descripcion, boolean cancelada, double presupuesto) {
		super();
		this.id = id;
		this.nombreTarea = nombreTarea;
		this.responsable = responsable;
		this.asiganda = asiganda;
		this.fechaInicio = fechaInicio;
		this.descripcion = descripcion;
		this.cancelada = cancelada;
		this.presupuesto = presupuesto;
	}

	public Task(int id, String nombreTarea) {
		super();
		this.id = id;
		this.nombreTarea = nombreTarea;
	}
	
	
}
