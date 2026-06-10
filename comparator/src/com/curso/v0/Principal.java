package com.curso.v0;

import java.util.Arrays;
import java.util.Comparator;

class Estudiante{
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
}

class ComparatorEdad implements Comparator<Estudiante> {
	@Override
	public int compare(Estudiante e1, Estudiante e2) {
		return e1.edad - e2.edad;
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
		
		System.out.println("***Comparator por Edad");
		Comparator<Estudiante> compEdad = new ComparatorEdad();
		show(estudiantes,compEdad);
		
	}

	private static void show(Estudiante[] estudiantes, 
								Comparator<Estudiante> comparator) {
		Arrays.sort(estudiantes,comparator);
		for(Estudiante e: estudiantes)
			System.out.println(e);
		
	}

}
