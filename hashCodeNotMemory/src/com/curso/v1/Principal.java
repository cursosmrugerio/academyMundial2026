package com.curso.v1;

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
		return 1000;
	}
}

public class Principal {

	public static void main(String[] args) {
		
		Empleado emp1 = new Empleado("Patrobas");
		System.out.println(emp1);
		
		Empleado emp2 = new Empleado("Patrobas");
		System.out.println(emp2);
		
		Empleado emp3 = new Empleado("Filologo");
		System.out.println(emp3);
		
		Empleado emp4 = new Empleado("Epeneto");
		System.out.println(emp4);

	}
}
