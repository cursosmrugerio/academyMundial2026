package com.curso.v3;

import java.time.LocalDate;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("V3");
		
		Task tarea1 = new TaskBuilder(1).build();
		System.out.println(tarea1);
		
		Task tarea2 = new TaskBuilder(2)
					  .setAsiganda("Patrobas")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .build();
		System.out.println(tarea2);
		
		Task tarea3 = new TaskBuilder(3)
					  .setPresupuesto(999.99)
					  .setAsiganda("Filologo")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .setDescripcion(new StringBuilder("Presentar examen APX"))
					  .build();
		System.out.println(tarea3);
		
		Task tarea4 = new TaskBuilder(4)
					  .setPresupuesto(999.99)
					  .setAsiganda("Filologo")
					  .setFechaInicio(LocalDate.now())
					  .setCompletada(false)
					  .setResponsable("Andronico")
					  .setDescripcion(new StringBuilder("Presentar examen APX"))
					  .setNombreTarea("Academy Xideral Mundial")
					  .build();
		System.out.println(tarea4);
		
		tarea4.setFechaTermino(LocalDate.now().plusDays(5));
		tarea4.setPresupuesto(111.11);
		
		System.out.println(tarea4);
		
		//Tarea5 tarea5 = new Tarea();
		

	}

}
