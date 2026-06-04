package com.curso.v2;

public abstract class Principal {

	public static void main(String[] args) {
		
		//CLASE ANONIMA
		Operacion ope0 = new Operacion() {
			@Override
			public int ejecuta() {
				int x = 8;
				int y = 4;
				System.out.println("Potencia [x=" + x + ", y=" + y + "]");
				return (int)Math.pow(x, y);
			}
		};
		
		Operacion ope1 = new Operacion() {
			@Override
			public int ejecuta() {
				int x = 8;
				int y = 4;
				System.out.println("Suma [x=" + x + ", y=" + y + "]");
				return x+y;
			}
		};
		
		Operacion ope2 = new Operacion() {
			@Override
			public int ejecuta() {
				int x = 8;
				int y = 4;
				System.out.println("Resta [x=" + x + ", y=" + y + "]");
				return x-y;
			}
		};
		
		Operacion ope3 = new Operacion() {
			@Override
			public int ejecuta() {
				int x = 8;
				int y = 4;
				System.out.println("Multi [x=" + x + ", y=" + y + "]");
				return x*y;
			}
		};
		
		Operacion[] operaciones = {
				ope0,
				ope1,
				ope2,
				new Operacion() {
					@Override
					public int ejecuta() {
						int x = 8;
						int y = 4;
						System.out.println("Division [x=" + x + ", y=" + y + "]");
						return x/y;
					}
				},
				ope3,
				};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		System.out.println("V2 Clases Anonimas");
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			//MISMO MENSAJE, DIFERENTE COMPORTAMIENTO
			System.out.println(ope.ejecuta());
		}
	}

}



