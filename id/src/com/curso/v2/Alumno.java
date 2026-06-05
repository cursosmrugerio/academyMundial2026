package com.curso.v2;

public class Alumno {
	
	private String nombre;
	//BAJO ACOPLAMIENTO
	private Computadora computadora;

	public Alumno(String nombre) {
		this.nombre = nombre;
	}
	
	void encenderCompu() {
		System.out.print(nombre+" ");
		computadora.encender();
	}
	
	public Computadora getComputadora() {
		return computadora;
	}

	//INYECCION POR SETTER
	public void setComputadora(Computadora computadora) {
		this.computadora = computadora;
	}
	

}
