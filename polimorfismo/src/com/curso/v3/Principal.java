package com.curso.v3;

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
			
			//JAVA 14
			if (ope instanceof Suma s)
				System.out.println(s.toStringSuma());
			else if (ope instanceof Resta r)
				System.out.println(r.toStringResta());
			else if (ope instanceof Multi m)
				System.out.println(m.toStringMulti());
			else
				System.out.println(ope.toStringOperacion());
			
			System.out.println(ope.ejecuta());
			
		}
	}

}



