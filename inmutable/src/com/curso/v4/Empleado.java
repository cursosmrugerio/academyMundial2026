package com.curso.v4;

import java.util.Arrays;

public final class Empleado {
	
	final private String nombre;
	final private StringBuilder curp;
	final private int edad;
	final private String[] prestaciones;
	
	public Empleado(String nombre, int edad, String[] prestaciones, StringBuilder curp) {
		this.nombre = nombre;
		this.edad = edad;
		this.prestaciones = prestaciones;
		this.curp = new StringBuilder(curp);
	}

	

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", curp=" + curp + ", edad=" + edad + ", prestaciones="
				+ Arrays.toString(prestaciones) + "]";
	}



	public String getNombre() {
		return nombre;
	}

	public int getEdad() {
		return edad;
	}

	public String[] getPrestaciones() {
		return prestaciones;
	}
	
	public StringBuilder getCurp() {
		return new StringBuilder(curp);
	}

}
