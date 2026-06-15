package com.curso.v1;

class DividirException extends Exception{
	DividirException(String msg){
		super(msg);
	}
}

public class Principal1 {

	public static void main(String[] args) {
		
		int x = 8;
		int y = 0;
		
		int resultado = 0;
		
		try {
			resultado = dividir(x,y);
		} catch (DividirException e) {
			System.out.println(e);
		}
		
		System.out.println("Resultado: "+resultado);
		
		System.out.println("End Program***");
	}

	private static int dividir(int x,int y) throws DividirException {
		
		if (y == 0 ) 
			throw new DividirException("No se puede dividir entre 0");
		
		int res = 0;
		res = x/y;
		return res;
	}

}
