package com.curso.v0;

public class Principal1 {
	public static void main(String[] args) {
		String nombre = "Epeneto";
		for (int x=0;x<10_000;x++) {
			nombre += x; //CREA UN NUEVO OBJETO
			System.out.println(nombre);
		}
		System.out.println("***End Program: "+nombre);
	}
}
