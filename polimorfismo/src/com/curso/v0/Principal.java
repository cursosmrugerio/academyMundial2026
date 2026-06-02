package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
		
		Operacion ope = new Operacion(8,4);
		System.out.println(ope.toStringOperacion());
		System.out.println(ope.ejecuta());
		
		Suma suma = new Suma(8,4);
		System.out.println(suma.toStringSuma());
		System.out.println(suma.ejecuta());
		
		Resta resta = new Resta(8,4);
		System.out.println(resta.toStringResta());
		System.out.println(resta.ejecuta());
		
		Multi multi = new Multi(8,4);
		System.out.println(multi.toStringMulti());
		System.out.println(multi.ejecuta());
		
	}

}
