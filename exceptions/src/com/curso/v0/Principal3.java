package com.curso.v0;

public class Principal3 {

	public static void main(String[] args) {
		
		int x = 4;
		int y = 0; //<==
		
		int resultado = 0;
		
		try {
			resultado = dividir(x,y);
		}catch(ArithmeticException e) {
			System.out.println("No puedo dividir entre 0");
		}
		
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
