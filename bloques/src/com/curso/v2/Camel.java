package com.curso.v2;

public class Camel {
	
	int i = 100;
	
	{ 
		System.out.println("Paso Bloque 1");
		int hairs = 3_000_0;
	}
	
	Camel(){
		System.out.println("Paso por Constructor");
		i=10;
	}
	
	public static void main(String[] args) {
		System.out.println("Inicio Programa");
		Camel c = new Camel();
		System.out.println(c.i);
		
	}
	
	{ 
		System.out.println("Paso Bloque 2");
		i=1;
	}

}
