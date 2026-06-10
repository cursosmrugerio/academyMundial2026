package com.curso.v4;

interface Predicado {
	boolean probar(String nombre);
}

public class Principal {
	public static void main(String[] args) {
		
		//Effective final
		String variable = "Roma"; //LOCAL
		
		Predicado pre = x -> {
			System.out.println("Variable: "+variable);
			return x.isBlank();
		};
		
		boolean resultado;
		
		resultado = pre.probar("Tercio");
		
		System.out.println(resultado); //false
		
		resultado = pre.probar("");
		
		System.out.println(resultado); //true
		
		//variable = "España";
		
	}
}
