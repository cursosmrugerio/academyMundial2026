package com.curso.v4;

import java.util.Arrays;
import java.util.Comparator;

class Estudiante{
	String nombre; //HAS-A
	int edad; //HAS-A
	double sueldo; //HAS-A
	
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

public class Principal {
	
	//Local class
	
	public static void main(String[] args) {
		System.out.println("*** Local class ***");
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20,123.12),
				new Estudiante("Andronico",28,450.23),
				new Estudiante("Patrobas",19,234.16),
				new Estudiante("Epeneto",22,345.12),
		};
		
		class ComparatorEdad implements Comparator<Estudiante> {
			@Override
			public int compare(Estudiante e1, Estudiante e2) {
				return e1.edad - e2.edad;
			}
		}
		
		class ComparatorSueldo implements Comparator<Estudiante> {
			@Override
			public int compare(Estudiante e1, Estudiante e2) {
				return (int)(e1.sueldo - e2.sueldo);
			}
		}

		class ComparatorNombre implements Comparator<Estudiante> {
			@Override
			public int compare(Estudiante e1, Estudiante e2) {
				return e2.nombre.compareTo(e1.nombre);
			}
		}
		
		System.out.println("***Comparator por Edad");
		Comparator<Estudiante> compEdad = 
				new ComparatorEdad();
		
		show(estudiantes,compEdad);
		
		System.out.println("***Comparator por Sueldo");
		Comparator<Estudiante> compSueldo = 
				new ComparatorSueldo();
		
		show(estudiantes,compSueldo);
		
		System.out.println("***Comparator por Nombre");
		Comparator<Estudiante> compNombre = 
				new ComparatorNombre();
		
		show(estudiantes,compNombre);
		
	}

	private static void show(Estudiante[] estudiantes, 
								Comparator<Estudiante> comparator) {
		Arrays.sort(estudiantes,comparator);
		for(Estudiante e: estudiantes)
			System.out.println(e);
		
	}

}
