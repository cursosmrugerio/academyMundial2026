package com.curso.v0;

import java.util.Arrays;

public class Empleado {
	
	private String nombre;
	private int edad;
	private String[] prestaciones;
	
	public Empleado(String nombre, int edad, String[] prestaciones) {
		this.nombre = nombre;
		this.edad = edad;
		this.prestaciones = prestaciones;
	}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", edad=" + edad + ", prestaciones=" + Arrays.toString(prestaciones)
				+ "]";
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

	public String[] getPrestaciones() {
		return prestaciones;
	}

	public void setPrestaciones(String[] prestaciones) {
		this.prestaciones = prestaciones;
	}
	
}
