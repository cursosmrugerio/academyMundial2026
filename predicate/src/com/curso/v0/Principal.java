package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
		
		Predicado.methodStatic();
		
		//DEFINICION LAMBDA
		Predicado<Double> pred = d -> d>10.0;
		
		pred.methodDefault();
		
		Predicado.methodStatic2();
		pred.methodDefault2();

	}

}
