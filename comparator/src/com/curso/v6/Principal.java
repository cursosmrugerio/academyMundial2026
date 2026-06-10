package com.curso.v6;

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
	
	//Lambdas
	public static void main(String[] args) {
		System.out.println("*** Lambdas ***");
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20,123.12),
				new Estudiante("Andronico",28,450.23),
				new Estudiante("Patrobas",19,234.16),
				new Estudiante("Epeneto",22,345.12),
		};
	
		System.out.println("***Comparator por Edad");
		Comparator<Estudiante> compEdad = (e1,e2)->e1.edad-e2.edad;	
		show(estudiantes,compEdad);
		
		System.out.println("***Comparator por Sueldo");
		show(estudiantes,(x,y) -> (int)(x.sueldo-y.sueldo));
		
		System.out.println("***Comparator por Nombre");
		show(estudiantes,(pato1,pato2)->pato1.nombre.compareTo(pato2.nombre));
		
	}

	private static void show(Estudiante[] estudiantes, 
								Comparator<Estudiante> comparator) {
		Arrays.sort(estudiantes,comparator);
		for(Estudiante e: estudiantes)
			System.out.println(e);
		
	}

}
