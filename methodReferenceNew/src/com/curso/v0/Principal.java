package com.curso.v0;

import java.util.function.Supplier;

class Empleado{}

public class Principal {
	public static void main(String[] args) {
		
		//POO
		Empleado emp1 = new Empleado();
		System.out.println(emp1);
		
		//FUNCTIONAL
		//DEFINICION DE LAMBDA
		Supplier<Empleado> sup = () -> new Empleado();
		//EJECUCION DE LAMBDA
		Empleado emp2 = sup.get();
		System.out.println(emp2);
		
		//DEFINICION DEL METHOD REFERENCE
		Supplier<Empleado> sup2 = Empleado::new;
		//EJECUCION DEL METHOD REFERENCE
		Empleado emp3 = sup2.get();
		System.out.println(emp3);
		
		
		
	}
}
