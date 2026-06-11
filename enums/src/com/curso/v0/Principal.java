package com.curso.v0;

enum DiaSemana {
	LUNES, 
	MARTES,
	MIERCOLES,
	JUEVES,
	VIERNES,
	SABADO,
	DOMINGO
}

public class Principal {

	public static void main(String[] args) {
		
		DiaSemana inicioMundial = DiaSemana.JUEVES;
		System.out.println(inicioMundial);
		System.out.println("********");
		
		for (DiaSemana ds : DiaSemana.values()) {
			System.out.println(ds);
			System.out.println(ds.ordinal());
			System.out.println(ds.name());
			System.out.println("------------");	
		}
		

	}

}
