package com.curso.v0;

public abstract class Principal {

	public static void main(String[] args) {
		
		Operacion[] operaciones = {
				new Potencia(8,4),
				new Suma(8,4),
				new Resta(8,4),
				new Division(8,4),
				new Multi(8,4),
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		for(Operacion ope:operaciones) {
			System.out.println(ope);
			System.out.println(ope.ejecuta());
		}
	}

}



