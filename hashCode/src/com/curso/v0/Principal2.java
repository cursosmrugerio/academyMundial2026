package com.curso.v0;

import java.util.HashSet;
import java.util.Set;

public class Principal2 {
	
	public static void main(String[] args) {
		Empleado emp1 = new Empleado("Patrobas",20);
		Empleado emp2 = new Empleado("Andronico",20);
		Empleado emp3 = new Empleado("Patrobas",20);
		
		Set<Empleado> set = new HashSet<>();
		
		set.add(emp1);
		set.add(emp2);
		set.add(emp3);
		
		for(Empleado emp:set)
			System.out.println(emp);
		
		
	}  

}
