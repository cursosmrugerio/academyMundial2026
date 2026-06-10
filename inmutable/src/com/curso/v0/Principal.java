package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
		
		String[] prestaciones = {
				"IMSS",
				"INFONAVIT",
				"CLUB DEPORTIVO"
		};
		
		Empleado eustabio = new Empleado(
			"Eustabio",
			40,
			prestaciones
		);
			
		System.out.println(eustabio);
				
		eustabio.setNombre("Patrobas");
		
		System.out.println(eustabio);

	}

}
