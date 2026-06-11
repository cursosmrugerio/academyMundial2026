package com.curso.v2;

public class Cliente {

	private String nombre;
	private int edad;
	
	public Cliente(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", edad=" + edad + "]";
	}

	public String getNombre() {
		//if (User.getRol()=="admin")
			return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		if (edad<18)
			throw new IllegalArgumentException("Edad no valida");
		this.edad = edad;
	}
	
}
