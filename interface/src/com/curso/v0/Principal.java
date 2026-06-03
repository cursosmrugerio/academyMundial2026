package com.curso.v0;

public abstract class Principal {

	public static void main(String[] args) {
		
		Operacion[] operaciones = {
				//new Operacion(8,4),
				new Potencia(8,4),
				new Suma(8,4),
				new Resta(8,4),
				new Division(8,4),
				new Multi(8,4),
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		System.out.println("V0 Inteface");
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			System.out.println(ope);
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta());
		}
	}

}



