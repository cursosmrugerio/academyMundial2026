package com.curso.v0;

public class Principal {

	public static void main(String[] args) {

		final int x = 10; //Constante
		//System.out.println(x=20);
		System.out.println(x);
		//*** OBJETO MUTABLE ***
		//NO PUEDES CAMBIAR LA REFERENCIA
		final StringBuilder sb = new StringBuilder("Hola");
		System.out.println(sb.append(" Mundo"));
		System.out.println(sb.append(" Academy Xideral"));
		//sb = new StringBuilder("Hello");
		//sb = null;
		
		//*** OBJETO INMUTABLE ***
		//NO PUEDES CAMBIAR LA REFERENCIA
		final String nombre = "Patrobas";
		//nombre = nombre.concat(" Filologo");
		//nombre = null;
		System.out.println(nombre); //Patrobas
		
	}
}
