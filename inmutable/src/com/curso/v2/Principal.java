package com.curso.v2;

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
				
		curpMain.append("RRR");
		
		System.out.println(eustabio);

	}

}
