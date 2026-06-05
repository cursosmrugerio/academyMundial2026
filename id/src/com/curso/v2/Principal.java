package com.curso.v2;

public class Principal {

	public static void main(String[] args) {
		
		//DEFINIR UN INYECTOR
		Computadora compu = new Computadora("Fedora 15");
		
		Alumno filologo = new Alumno("Filologo");
		
		//@Autowire
		filologo.setComputadora(compu);
		
		filologo.encenderCompu();
	}

}
