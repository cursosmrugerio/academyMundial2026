package com.curso.v5;

public class Principal {

	public static void main(String[] args) {
		
		Operacion[] operaciones = {
				new Operacion(8,4),
				new Potencia(8,4),
				new Suma(8,4),
				new Resta(8,4),
				new Multi(8,4),
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			System.out.println(ope.toString());
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta());
		}
	}

}



