package com.demo.v3;

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
		return new StringBuilder(curp);
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
		edadMain = 888;
		
		System.out.println(patrobas);
		
		curpMain = patrobas.getCurp();
		
		curpMain.append("WWW");
		
		System.out.println(patrobas);
		
		edadMain = patrobas.getEdad();
		
		edadMain = 999;
		
		System.out.println(patrobas);
		
		
	}

}
