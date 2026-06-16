package com.curso.v0;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

class Empleado{
	String nombre;
	
	public Empleado(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		Empleado other = (Empleado) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public int hashCode() {
		return 9999;
	}
	
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + "]";
	}
	
}

public class Principal {
	
	public static void main(String[] args) {
		
		Empleado emp1 = new Empleado("Epeneto");
		Empleado emp2 = new Empleado("Filologo");
		Empleado emp3 = new Empleado("Epeneto");
		
		Map<Empleado,Double> empSalary = new HashMap<>();
		
		empSalary.put(emp1, 1000.0);
		empSalary.put(emp2, 1000.0);
		empSalary.put(emp3, 1000.0);
		
		empSalary.forEach((x,y)-> System.out.println("Nombre: "+x+" ,Sueldo: "+y ));
		
	}

}
