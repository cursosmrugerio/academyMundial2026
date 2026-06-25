package com.curso.v0;

public class Principal2 {

	public static void main(String[] args) {

		int x = 2;
		int y = Integer.MAX_VALUE;
		
		Suma suma = new Suma(x,y);
		
		int res = suma.ejecuta();
		
		System.out.println("Resultado: "+res);
	}

}
