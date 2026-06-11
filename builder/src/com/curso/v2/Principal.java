package com.curso.v2;

import java.time.LocalDate;

public class Principal {

	public static void main(String[] args) {
		
		TaskBuilder builder1 = new TaskBuilder(1);
		Task tarea1 = builder1.build();
		System.out.println(tarea1);
		
		TaskBuilder builder2 = new TaskBuilder(2);
		Task tarea2 = builder2
					  .setAsiganda("Patrobas")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .build();
		System.out.println(tarea2);
		
		TaskBuilder builder3 = new TaskBuilder(3);
		Task tarea3 = builder3
					  .setPresupuesto(999.99)
					  .setAsiganda("Filologo")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .setDescripcion(new StringBuilder("Presentar examen APX"))
					  .build();
		System.out.println(tarea3);
		
		TaskBuilder builder4 = new TaskBuilder(4);
		Task tarea4 = builder4
					  .setPresupuesto(999.99)
					  .setAsiganda("Filologo")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .setResponsable("Andronico")
					  .setDescripcion(new StringBuilder("Presentar examen APX"))
					  .setNombreTarea("Academy Xideral Mundial")
					  .build();
		System.out.println(tarea4);

	}

}
