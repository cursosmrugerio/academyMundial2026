package com.curso.v3;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

class Empleado{
	String nombre;
	int edad;
	double promedio;
	
	public Empleado(){} //DEFAULT
	public Empleado(String nombre) {
		this.nombre = nombre;
	}
	public Empleado(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	public Empleado(String nombre, int edad, double promedio) {
		this.nombre = nombre;
		this.edad = edad;
		this.promedio = promedio;
	}
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", edad=" + edad + ", promedio=" + promedio + "]";
	}
}

public class Principal {
	public static void main(String[] args) {
		
		System.out.println("***POO***");
		//POO
		Empleado emp1 = new Empleado();
		System.out.println(emp1);
		
		Empleado emp1A = new Empleado("Patrobas");
		System.out.println(emp1A);
		
		Empleado emp1B = new Empleado("Tercio",20);
		System.out.println(emp1B);
		
		Empleado emp1C = new Empleado("Epeneto",20,100.00);
		System.out.println(emp1C);
		
		System.out.println("***LAMBDA***");
		//LAMBDA
		Supplier<Empleado> sup = () -> new Empleado();
		Empleado emp2 = sup.get();
		System.out.println(emp2);
		
		Function<String,Empleado> fun = name -> new Empleado(name);
		Empleado emp2A = fun.apply("Patrobas");
		System.out.println(emp2A);
		
		BiFunction<String,Integer,Empleado> bifun =
					(name,age) -> new Empleado(name,age);
		Empleado emp2B = bifun.apply("Tercio",20);
		System.out.println(emp2B);
		
		
		
		System.out.println("***METHOD REFERENCE***");
		//METHOD REFERENCE
		Supplier<Empleado> sup2 = Empleado::new;
		Empleado emp3 = sup2.get();
		System.out.println(emp3);
		
		Function<String,Empleado> fun2 = Empleado::new;
		Empleado emp3A = fun2.apply("Patrobas");
		System.out.println(emp3A);
		
		BiFunction<String,Integer,Empleado> bifun2 = Empleado::new;
		Empleado emp3B = bifun2.apply("Tercio",20);
		System.out.println(emp3B);
		
		TriFunction<String,Integer,Double,Empleado> trifun2 = Empleado::new;
		Empleado emp3c = trifun2.apply("Epeneto", 30, 100.0);
		System.out.println(emp3c);
		
		
	}
}
