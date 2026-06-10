package com.curso.v3;

interface Predicado {
	boolean probar(String nombre);
}

public class Principal {
	public static void main(String[] args) {
		
		Predicado pre = x -> x.isBlank();
		
		boolean resultado;
		
		resultado = pre.probar("Tercio");
		
		System.out.println(resultado); //false
		
		resultado = pre.probar("");
		
		System.out.println(resultado); //true
		
	}
}
