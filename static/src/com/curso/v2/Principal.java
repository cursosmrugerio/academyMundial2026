package com.curso.v2;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println(Pato.contador); //0
		
		Pato pato1 = new Pato("Lucas");
		Pato pato2 = new Pato("Donald");
		Pato pato3 = new Pato("Feo");
		Pato pato4 = new Pato("Rico");
		
		System.out.println(new Pato("Lucas").contador); //5

	}

}
