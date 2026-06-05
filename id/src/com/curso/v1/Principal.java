package com.curso.v1;

public class Principal {

	public static void main(String[] args) {
		
		//DEFINIR UN INYECTOR
		Computadora compu = new Computadora("Ubuntu 20");
		
		//@Autowire
		Alumno patrobas = new Alumno("Patrobas",compu);
		
		patrobas.encenderCompu();
	}

}
