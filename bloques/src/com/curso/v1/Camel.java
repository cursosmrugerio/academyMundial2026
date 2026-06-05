package com.curso.v1;

public class Camel {
	
	{ 
		System.out.println("Paso Bloque 1");
		int hairs = 3_000_0;
	}
	
	Camel(){
		System.out.println("Paso por Constructor");
	}
	
	public static void main(String[] args) {
		System.out.println("Inicio Programa");
		Camel c = new Camel();
	}
	
	{ 
		System.out.println("Paso Bloque 2");
	}

}
