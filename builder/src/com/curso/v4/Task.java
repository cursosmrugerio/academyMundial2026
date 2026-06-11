package com.curso.v4;

import java.time.LocalDate;

public class Task {
	
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
	
	private Task(int id, String nombreTarea, String responsable, String asiganda, LocalDate fechaInicio,
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getAsiganda() {
		return asiganda;
	}

	public void setAsiganda(String asiganda) {
		this.asiganda = asiganda;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(LocalDate fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public boolean isCompletada() {
		return completada;
	}

	public void setCompletada(boolean completada) {
		this.completada = completada;
	}

	public StringBuilder getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(StringBuilder descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isCancelada() {
		return cancelada;
	}

	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	//STATIC NESTED CLASS
	static public class TaskBuilder { 
		
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

}
