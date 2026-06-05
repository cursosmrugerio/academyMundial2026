package com.curso.v0;

public class Alumno {
	
	String nombre;
	//ALTO ACOPLAMIENTO //MALA PRACTICA
	Computadora computadora = new Computadora("Ubuntu 20"); 

	public Alumno(String nombre) {
		this.nombre = nombre;
	}
	
	void encenderCompu() {
		System.out.print(nombre+" ");
		computadora.encender();
	}
	

}
