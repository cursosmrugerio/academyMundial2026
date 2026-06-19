package com.curso.v1;

import java.util.function.Function;
import java.util.function.Supplier;

class Empleado{
	String nombre;
	public Empleado(){} //DEFAULT
	public Empleado(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + "]";
	}
}

public class Principal {
	public static void main(String[] args) {
		
		//POO
		Empleado emp1 = new Empleado();
		System.out.println(emp1);
		
		Empleado emp1A = new Empleado("Patrobas");
		System.out.println(emp1A);
		
		//LAMBDA
		Supplier<Empleado> sup = () -> new Empleado();
		Empleado emp2 = sup.get();
		System.out.println(emp2);
		
		Function<String,Empleado> fun = name -> new Empleado(name);
		Empleado emp2A = fun.apply("Patrobas");
		System.out.println(emp2A);
		
		//METHOD REFERENCE
		Supplier<Empleado> sup2 = Empleado::new;
		Empleado emp3 = sup2.get();
		System.out.println(emp3);
		
		Function<String,Empleado> fun2 = Empleado::new;
		Empleado emp3A = fun2.apply("Patrobas");
		System.out.println(emp3A);
		
		
		
	}
}
