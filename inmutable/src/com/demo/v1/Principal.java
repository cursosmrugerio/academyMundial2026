package com.demo.v1;

final class Empleado{
	//HAS A
	final private String nombre; //INMUTABLE
	final private StringBuilder curp; //MUTABLE
	final private int edad; //PRIMITIVO
	
	public Empleado(String nombre, StringBuilder curp, int edad) {
		this.nombre = nombre;
		this.curp = new StringBuilder(curp);
		this.edad = edad;
	}
	
	public String getNombre() {
		return nombre;
	}

	public StringBuilder getCurp() {
		return curp;
	}

	public int getEdad() {
		return edad;
	}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", curp=" + curp + ", edad=" + edad + "]";
	}

	
}

public class Principal {
	
	public static void main(String[] args) {
		
		String nombreMain = "Patrobas";
		StringBuilder curpMain = new StringBuilder("AXY123");
		int edadMain = 20;
		
		Empleado patrobas = new Empleado(nombreMain,curpMain,edadMain);
		
		System.out.println(patrobas);
		
		curpMain.append("ZZZ");
		
		System.out.println(patrobas);
		
		
	}

}
