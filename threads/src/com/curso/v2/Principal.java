package com.curso.v2;

public class Principal {
	
	public static void main(String[] args) {
		//Definicion de Lambda
		Runnable run1 = () -> System.out.println("Hello 1");
		Runnable run2 = () -> System.out.println("Hello 2");
		
		Thread hilo1 = new Thread(run1);
		Thread hilo2 = new Thread(run2);
		
		hilo1.start(); //Ejecuta Thread
		hilo2.start();
		
		System.out.println("Fin de Programa : " + Thread.currentThread().getName()); //Thread main
		
	}
}

/** POSIBLES SALIDAS
 
Hello 2
Hello 1
Fin de Programa

Hello 1
Fin de Programa
Hello 2

Fin de Programa
Hello 1
Hello 2

**/