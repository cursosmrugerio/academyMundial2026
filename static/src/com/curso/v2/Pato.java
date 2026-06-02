package com.curso.v2;

public class Pato {
	
	//VARIABLES DE INSTANCIA (OBJECTO)
	String nombre; //HAS-A //null
	
	////VARIABLES DE CLASE
	static int contador; //HAS-A //0
	
	public Pato(String nombre) {
		this.nombre = nombre;
		contador++;
	}

}
