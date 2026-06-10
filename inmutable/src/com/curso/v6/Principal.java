package com.curso.v6;

public class Principal {

	public static void main(String[] args) {
		
		String[] prestaciones = {
				"IMSS",
				"INFONAVIT",
				"CLUB DEPORTIVO"
		};
		
		StringBuilder curpMain = new StringBuilder("AXY123");
		
		Empleado eustabio = new Empleado(
			"Eustabio",
			40,
			prestaciones,
			curpMain
		);
			
		System.out.println(eustabio);
		
		System.out.println(eustabio.nombre());

	}

}
