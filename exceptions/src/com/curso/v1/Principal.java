package com.curso.v1;

class DividirException extends Exception{
	DividirException(String msg){
		super(msg);
	}
}

public class Principal {

	public static void main(String[] args) throws DividirException {
		
		int x = 8;
		int y = 0;
		
		int resultado = 0;
		
		resultado = dividir(x,y);
		
		System.out.println("Resultado: "+resultado);
		
		System.out.println("End Program");
	}

	private static int dividir(int x,int y) throws DividirException {
		
		if (y == 0 ) 
			throw new DividirException("No se puede dividir entre 0");
		
		int res = 0;
		res = x/y;
		return res;
	}

}
