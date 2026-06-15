package com.curso.v0;

public class Principal1 {

	public static void main(String[] args) {
		
		int x = 4;
		int y = 0; //<==
		
		int resultado = 0;
		
		resultado = dividir(x,y);
		
		System.out.println("Resultado: "+resultado); //0
		
		System.out.println("End Program");
				
	}

	private static int dividir(int x, int y) {
				
		int res = 0;

		try {
			res = x/y;
		}catch(ArithmeticException e) {
			System.out.println("No puedo dividir entre Cero");
		}
		
		return res; //Uncheked Exception 
	}

}
