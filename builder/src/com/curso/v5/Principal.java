package com.curso.v5;

import java.time.LocalDate;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("V5");
		
		Task tarea1 = new TaskBuilder(1).build();
		System.out.println(tarea1);
		
		Task tarea3 = new TaskBuilder(3)
				  .setPresupuesto(999.99) //return TaskBuilder(3)
				  .setAsiganda("Filologo") //return TaskBuilder(3)
				  .setFechaInicio(LocalDate.now())
				  .setCompletada(true)
				  .setDescripcion(new StringBuilder("Presentar examen APX"))
				  .build();
		System.out.println(tarea3);

	}

}
