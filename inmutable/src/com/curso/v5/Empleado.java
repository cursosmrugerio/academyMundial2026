package com.curso.v5;

import java.util.Arrays;

public final class Empleado {
	
	final private String nombre; //INMUTABLE
	final private StringBuilder curp; //MUTABLE
	final private int edad; //CONSTANTE
	final private String[] prestaciones; //MUTABLE
	
	public Empleado(String nombre, int edad, String[] prestaciones, StringBuilder curp) {
		this.nombre = nombre;
		this.edad = edad;
		this.prestaciones = Arrays.copyOf(prestaciones,prestaciones.length);
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
		return Arrays.copyOf(prestaciones,prestaciones.length);
	}
	
	public StringBuilder getCurp() {
		return new StringBuilder(curp);
	}

}
