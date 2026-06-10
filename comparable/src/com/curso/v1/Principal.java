package com.curso.v1;

import java.util.Arrays;

class Estudiante{
	String nombre;
	public Estudiante(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + "]";
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Estudiante[] estudiantes = {
				new Estudiante("Filologo"),
				new Estudiante("Andronico"),
				new Estudiante("Patrobas"),
				new Estudiante("Epeneto"),
		};
		
		//Estudiante no implementa Comparable<Estudiante>
		//por ende se gerera una exception en runtime.
		Arrays.sort(estudiantes);
		
		//System.out.println(estudiantes);
		System.out.println(Arrays.toString(estudiantes));
	}

}
