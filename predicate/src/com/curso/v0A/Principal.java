package com.curso.v0A;

public class Principal {

	public static void main(String[] args) {
		
		//DEFINICION LAMBDA
		Predicado<Double> pred = d -> d>10.0;
		
		System.out.println(pred.probar(8.0));
		
		String res = Predicado.and(100);
		System.out.println(res);

	}

}
