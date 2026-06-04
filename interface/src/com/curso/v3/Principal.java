package com.curso.v3;

public abstract class Principal {

	public static void main(String[] args) {
		
		//LAMBDA
		Operacion ope0 = () -> (int)Math.pow(8, 4);
		
		Operacion ope1 = () -> {
			System.out.println("Suma");
			return 8+4;
		};
		
		Operacion ope2 = () -> 8-4;
		
		Operacion ope3 = () -> 8*4;
		
		Operacion[] operaciones = {
				ope0,
				ope1,
				ope2,
				() -> 8/4,
				ope3,
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		System.out.println("V3 Lambdas");
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta());
		}
	}

}



