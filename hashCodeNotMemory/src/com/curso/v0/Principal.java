package com.curso.v0;

import java.util.Objects;

class Empleado{
	
	String nombre;

	public Empleado(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}
}

public class Principal {

	public static void main(String[] args) {
		
		Empleado emp1 = new Empleado("Patrobas");
		System.out.println(emp1);
		
		Empleado emp2 = new Empleado("Patrobas");
		System.out.println(emp2);

	}

}
