package com.curso.v0;

public class Principal2 {
	public static void main(String[] args) {
		StringBuilder nombre = new StringBuilder("Epeneto");
		for (int x=0;x<1000;x++) {
			nombre.append(x); //1 SOLO OBJETO 
			System.out.println(nombre);
		}
		
		String nombreFinal = nombre.toString();
		System.out.println("***End Program: "+nombreFinal);
	}
}
