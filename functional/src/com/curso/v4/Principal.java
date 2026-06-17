package com.curso.v4;

import java.util.*;

class Empleado{
	String nombre;
	int edad;
	double sueldo;
	
	public Empleado(String nombre, int edad, double sueldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.sueldo = sueldo;
	}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", edad=" + edad + ", sueldo=" + sueldo + "]";
	}
}

public class Principal {

	public static void main(String[] args) {
		
		List<Empleado> empleados = new ArrayList<>();
		
		empleados.add(new Empleado("Patrobas",30,250.50));
		empleados.add(new Empleado("Eustabio",27,350.50));
		empleados.add(new Empleado("Rufo",25,150.50));
		empleados.add(new Empleado("Tercio",18,450.50));
		
		//consumer
		empleados.forEach(e-> System.out.println(e));
		System.out.println("*****************");
		
		//predicate
		empleados.removeIf(x -> x.edad<19);
		//consumer
		empleados.forEach(e-> System.out.println(e));
		System.out.println("*****************");
		//unaryOperator
		empleados.replaceAll(u -> {
			u.sueldo += 500;
			return u;
		});
		//consumer
		empleados.forEach(pato-> System.out.println(pato));
		System.out.println("*****************");
		//comparator
		empleados.sort((emp1,emp2)->emp1.edad-emp2.edad);
		empleados.forEach(pato-> System.out.println(pato));
		

	}

}
