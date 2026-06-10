package com.curso.v2;

import java.util.Arrays;

class Estudiante implements Comparable<Estudiante>{
	String nombre;
	int edad;
	
	public Estudiante(String nombre,int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", edad=" + edad + "]";
	}
	@Override
	public int compareTo(Estudiante est) {
		if (this.edad > est.edad)
			return 200;
		else if (this.edad < est.edad)
			return -999;
		else
			return 0;
	}
}

public class Principal {
	
	public static void main(String[] args) {
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20),
				new Estudiante("Andronico",28),
				new Estudiante("Patrobas",19),
				new Estudiante("Epeneto",22),
		};
		
		Arrays.sort(estudiantes);
		
		for(Estudiante e: estudiantes)
			System.out.println(e);
	}

}
