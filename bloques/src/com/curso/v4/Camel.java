package com.curso.v4;

public class Camel {

	{ 
		System.out.println("Paso Bloque 1"); //4
		int hairs = 3_000_0;
	}
	
	Camel(){
		System.out.println("Paso por Constructor"); //6
	}
	
	public static void main(String[] args) {
		System.out.println("Inicio Programa");  //3
		new Camel();
	}
	
	static { 
		System.out.println("Paso Static Bloque 1"); //1
		int i1 = 5;
		System.out.println(i1);
	}
	
	{ 
		System.out.println("Paso Bloque 2"); //5
	}
	
	static { 
		System.out.println("Paso Static Bloque 2"); //2
	}
	

}
