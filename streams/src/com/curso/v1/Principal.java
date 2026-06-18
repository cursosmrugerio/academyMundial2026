package com.curso.v1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;


class Empleado{
	private String nombre;
	private int edad;
	private double sueldo;
	public Empleado(String nombre, int edad, double sueldo) {
		this.nombre = nombre;
		this.edad = edad;
		this.sueldo = sueldo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(edad, nombre, sueldo);
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
		return edad == other.edad && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(sueldo) == Double.doubleToLongBits(other.sueldo);
	}
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", edad=" + edad + ", sueldo=" + sueldo + "]";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public double getSueldo() {
		return sueldo;
	}
	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}
	
	
}

public class Principal {
	
	public static void main(String[] args) {
		
		List<Empleado> lista = Arrays.asList(
				new Empleado("Patrobas",30,50.50),
				new Empleado("Filologo",20,60.20),
				new Empleado("Rufo",17,60.20),
				new Empleado("Epeneto",25,35.50));
		
		//MAYORES DE EDAD
		//ADD 15% SUELDO
		//ORDENALOS POR NOMBRE
		//MUESTRAME QUINES SON CON SU NOMBRE EN MAYUSCULAS
		//SUMA DE SUS SALARIOS 
		
		double sumaSalarios = lista.stream()
			.filter(e -> e.getEdad()>=18)
			.map(employee -> {
				employee.setSueldo(employee.getSueldo()*1.15);
				return employee;
			})
			.sorted((x,y)->x.getNombre().compareTo(y.getNombre()))
			.map(emp -> {
				emp.setNombre(emp.getNombre().toUpperCase());
				return emp;
			})
			.peek(System.out::println)
			.mapToDouble(e -> e.getSueldo())
			.sum();
		
		System.out.println("Suma Salarios: "+sumaSalarios);
		
		System.out.println("**************");
		Empleado filologo = new Empleado("Filologo",22,25.50);
		Empleado andronico = new Empleado("Andronico",20,45.50);
		//DEFINICION DE UNA LAMBDA
		Function<Empleado,Double> functionEmpSueldo = e -> e.getSueldo();
		
		Empleado[] arrayEmpleados = {filologo,andronico};
		
		Stream<Empleado> streamEmpleados = Arrays.stream(arrayEmpleados);
		
		Stream<Double> streamDouble = streamEmpleados.map(functionEmpSueldo);
	
		DoubleStream doubleStream = streamDouble.mapToDouble(d -> d);
		
		System.out.println(doubleStream.sum());
		
		
	}

}
