package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		Empleado emp1 = new Empleado("Patrobas",20);
		Empleado emp2 = new Empleado("Andronico",18);
		Empleado emp3 = new Empleado("Patrobas",20);
		
		System.out.println(emp1.equals(emp2)); //false
		System.out.println(emp1.equals(emp3)); //true
		
		
	}  

}
