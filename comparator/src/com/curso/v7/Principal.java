package com.curso.v7;

import java.util.Arrays;
import java.util.Comparator;

class Estudiante{
	private String nombre; //HAS-A
	private int edad; //HAS-A
	private double sueldo; //HAS-A
	
	public Estudiante(String nombre,int edad, double sueldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.sueldo = sueldo;
	}

	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", edad=" + edad + ", sueldo=" + sueldo + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}
	
	
}

public class Principal {
	
	//Lambdas
	public static void main(String[] args) {
		System.out.println("*** Lambdas ***");
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20,123.12), //3  //2
				new Estudiante("Andronico",20,123.12), //4 //1
				new Estudiante("Patrobas",20,234.16), //2  //3 
				new Estudiante("Epeneto",20,345.12), //1   //4
		};
	
		//POR edad, POR sueldo, POR Nombre, Orden inverso
		//Functional Programming
		Comparator<Estudiante> comparator = 
				Comparator.comparingInt(Estudiante::getEdad)
				.thenComparingDouble(Estudiante::getSueldo)
				.thenComparing(Estudiante::getNombre)
				.reversed();
		
		show(estudiantes,comparator );

	}

	private static void show(Estudiante[] estudiantes, 
								Comparator<Estudiante> comparator) {
		Arrays.sort(estudiantes,comparator);
		for(Estudiante e: estudiantes)
			System.out.println(e);
		
	}

}
