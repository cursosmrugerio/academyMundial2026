package com.curso.v2;

public class Principal {
	
	public static void main(String[] args) {
		
		int x = 8;
		int y = -2;
		int resultado = 0;
		
		try {
			resultado = dividir(x,y);
		} catch (ValorInvalidoException e) {
			e.printStackTrace();
		} catch (DividirCeroException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		System.out.println("Resultado: "+resultado);
		System.out.println("END PROGRAM");
	}

	private static int dividir(int x, int y) 
			throws ValorInvalidoException,DividirCeroException {
		
		if (x>1000)
			throw new ValorInvalidoException("Valor invalido");
		else if (y == 0)
			throw new DividirCeroException("No se puede dividir entre 0");
		else if (y < 0)
			throw new UnsupportedOperationException("No se permite dividir entre negativos");
		
		return x/y;
	}
	
	
	

}
