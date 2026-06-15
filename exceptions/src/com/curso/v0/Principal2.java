package com.curso.v0;

public class Principal2 {

	public static void main(String[] args) {
		
		int x = 4;
		int y = 0; //<==
		
		int resultado = 0;
		
		resultado = dividir(x,y);
		
		System.out.println("Resultado: "+resultado); //0
		
		System.out.println("End Program"); 
				
	}

	private static int dividir(int x, int y) {
		
		if (y==0)
			throw new ArithmeticException("No puedo dividir entre 0");
				
		int res = x/y;
		
		return res; //Uncheked Exception 
	}

}
