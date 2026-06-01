package com.curso.v0;

public class Principal {
	
	Operacion ope1 = new Suma(); //HAS-A (Objeto)
	static Operacion ope2 = new Resta(); //HAS-A (Clase)

	public static void main(String[] args) {
		
		Principal pri = new Principal();

		int res1 = pri.ope1.ejecuta(8, 4);
		int res2 = ope2.ejecuta(8, 4);
		
	}

}
