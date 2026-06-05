package com.curso.v5;

public class Camel {

	{ 
		System.out.println("Paso Bloque 1"); //4 //7
		int hairs = 3_000_0;
	}
	
	Camel(){
		System.out.println("Paso por Constructor"); //6 //9
	}
	
	public static void main(String[] args) {
		System.out.println("Inicio Programa");  //3
		new Camel(); new Camel();
	}
	
	static { 
		System.out.println("Paso Static Bloque 1"); //1
	}
	
	{ 
		System.out.println("Paso Bloque 2"); //5 //8
	}
	
	static { 
		System.out.println("Paso Static Bloque 2"); //2
	}
	

}
