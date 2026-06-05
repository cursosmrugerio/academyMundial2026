package com.curso.v3;

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
	}
	
	static { 
		System.out.println("Paso Static Bloque 1");
	}
	
	{ 
		System.out.println("Paso Bloque 2");
	}
	
	static { 
		System.out.println("Paso Static Bloque 2");
	}
	

}
