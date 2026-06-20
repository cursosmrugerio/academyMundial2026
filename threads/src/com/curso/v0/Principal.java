package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		//Definicion de Lambda
		Runnable run = () -> System.out.println("Hello");
		
		Thread hilo1 = new Thread(run);
		
		hilo1.start(); //Ejecuta Thread
		
		System.out.println("Fin de Programa"); //Thread main
		
	}
	
	 

}
