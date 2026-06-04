package com.curso.v4;

public abstract class Principal {

	public static void main(String[] args) {
		
		//LAMBDA
		Operacion ope0 = (x,y) -> {
			System.out.println("Pontencia "+"[x=" + x + ", y=" + y + "]");
			return (int)Math.pow(x, y);
		};
		
		Operacion ope1 = (x,y) -> {
			System.out.println("Suma "+"[x=" + x + ", y=" + y + "]");
			return x+y;
		};
		
		Operacion ope2 = (x,y) -> {
			System.out.println("Resta "+"[x=" + x + ", y=" + y + "]");
			return x-y;
		};
		
		Operacion ope3 = (x,y) -> {
			System.out.println("Multi "+"[x=" + x + ", y=" + y + "]");
			return x*y;
		};
		
		Operacion[] operaciones = {
				ope0,
				ope1,
				ope2,
				(x,y) -> {
					System.out.println("Division "+"[x=" + x + ", y=" + y + "]");
					return x/y;
				},
				ope3,
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		System.out.println("V3 Lambdas");
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta(8,4));
		}
	}

}



