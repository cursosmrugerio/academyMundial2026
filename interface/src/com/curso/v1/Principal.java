package com.curso.v1;

public abstract class Principal {

	public static void main(String[] args) {
		System.out.println("V1 Inteface with Abstract");
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
		
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			System.out.println(ope);
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta());
		}
	}

}



