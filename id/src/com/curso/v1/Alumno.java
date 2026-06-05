package com.curso.v1;

public class Alumno {
	
	String nombre;
	//BAJO ACOPLAMIENTO
	Computadora computadora;

	//INYECCION POR CONSTRUCTOR (VARIABLE, SETTER)
	public Alumno(String nombre, Computadora compu) {
		this.nombre = nombre;
		computadora = compu;
	}
	
	void encenderCompu() {
		System.out.print(nombre+" ");
		computadora.encender();
	}
	

}
