package com.curso.v0B;

public class Principal {

	public static void main(String[] args) {
		
		//DEFINICION LAMBDA
		Predicado<Double> pred = d -> d>10.0;
		
		System.out.println(pred.probar(8.0));
		
		Predicado pre = Predicado.and(100);
		
		boolean res = pre.probar(Long.valueOf(100));
		System.out.println(res);
		

	}

}
