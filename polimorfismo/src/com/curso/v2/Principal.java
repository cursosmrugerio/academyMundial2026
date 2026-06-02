package com.curso.v2;

public class Principal {

	public static void main(String[] args) {
		
		Operacion ope1 = new Operacion(8,4);
		Operacion ope2 = new Suma(8,4);
		Operacion ope3 = new Resta(8,4);
		Operacion ope4 = new Multi(8,4);
		
		Operacion[] operaciones = {ope1,ope2,ope3,ope4};
		
		show(operaciones);
		
	}

	private static void show(Operacion[] operaciones) {
		
		//POLIMORFISMO
		for(Operacion ope:operaciones) {
			
			if (ope instanceof Suma)
				System.out.println(((Suma)ope).toStringSuma());
			else if (ope instanceof Resta)
				System.out.println(((Resta)ope).toStringResta());
			else if (ope instanceof Multi)
				System.out.println(((Multi)ope).toStringMulti());
			else
				System.out.println(ope.toStringOperacion());
			
			System.out.println(ope.ejecuta());
			
		}
	}

}



