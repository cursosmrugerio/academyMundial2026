package com.curso.v1;

class Estudiante{
	private String nombre; //HAS-A
	private int edad; //HAS-A
	private double promedio; //HAS-A
	
	public Estudiante(String nombre,int edad, double promedio) {
		this.nombre = nombre;
		this.edad = edad;
		this.promedio = promedio;
	}

	@Override
	public String toString() {
		return "Estudiante [nombre=" + nombre + ", edad=" + edad + ", promedio=" + promedio + "]";
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

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}
	
	
}


public class Principal {

	public static void main(String[] args) {
		
		Estudiante[] estudiantes = {
				new Estudiante("Filologo",20,78.12), //3  //2
				new Estudiante("Andronico",18,88.12), //4 //1
				new Estudiante("Patrobas",23,96.16), //2  //3 
				new Estudiante("Epeneto",35,65.12), //1   //4
		};

		Predicado<Estudiante> pre1 = e -> e.getEdad()>20;
		Predicado<Estudiante> pre2 = r -> r.getPromedio()>80;
		
		Predicado<Estudiante> pre3 = Predicado.and(pre1, pre2);
		
		for (Estudiante e: estudiantes)
			if (pre3.probar(e)) //EJECUTE LAMBDA pre3
				System.out.println(e);
		
		
	}

}
