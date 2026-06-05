package com.curso.v0;

public class Camel {
	
	{ 
		System.out.println("Paso Bloque 1");
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
