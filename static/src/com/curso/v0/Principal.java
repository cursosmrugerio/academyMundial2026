package com.curso.v0;

public class Principal {

	public static void main(String[] args) {
		
		Pato pato1 = new Pato("Lucas");
		Pato pato2 = new Pato("Donald");
		Pato pato3 = new Pato("Feo");
		
		System.out.println(pato1.nombre); //Lucas
		System.out.println(pato2.nombre); //Donald
		System.out.println(pato3.nombre); //Feo
		
		
		System.out.println(pato1.contador); //1
		System.out.println(pato2.contador); //1
		System.out.println(pato3.contador); //1

	}

}
