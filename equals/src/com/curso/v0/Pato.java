package com.curso.v0;

public class Pato extends Object {
	
	String nombre;
	int edad;
	
	public Pato(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	@Override
	public boolean equals(Object obj) {
		Pato other = (Pato) obj;
		return edad == other.edad && nombre == other.nombre;
	}
	
	
}
