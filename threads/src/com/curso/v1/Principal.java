package com.curso.v1;

public class Principal {
	
	public static void main(String[] args) {
		//Definicion de Lambda
		Runnable run1 = () -> System.out.println("Hello 1");
		Runnable run2 = () -> System.out.println("Hello 2");
		
		Thread hilo1 = new Thread(run1);
		Thread hilo2 = new Thread(run2);
		
		hilo1.start(); //Ejecuta Thread
		hilo2.start();
		
		System.out.println("Fin de Programa"); //Thread main
		
	}
	
	 

}
