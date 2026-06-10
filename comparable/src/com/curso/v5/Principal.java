package com.curso.v5;

import java.util.Arrays;

class Estudiante implements Comparable<Estudiante>{
	String nombre;
	int edad;
	double sueldo;
	
	public Estudiante(String nombre,int edad, double sueldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.sueldo = sueldo;
	}

	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", edad=" + edad + ", sueldo=" + sueldo + "]";
	}

	@Override
	public int compareTo(Estudiante est) {
		return (int)(this.sueldo - est.sueldo);
	}
	
}

public class Principal {
	
	public static void main(String[] args) {
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20,123.12),
				new Estudiante("Andronico",28,450.23),
				new Estudiante("Patrobas",19,234.16),
				new Estudiante("Epeneto",22,345.12),
		};
		
		Arrays.sort(estudiantes);
		
		for(Estudiante e: estudiantes)
			System.out.println(e);
	}

}
