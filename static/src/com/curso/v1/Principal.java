package com.curso.v1;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println(Pato.contador); //0
		
		System.out.println("************");
		
		Pato pato1 = new Pato("Lucas");
		Pato pato2 = new Pato("Donald");
		Pato pato3 = new Pato("Feo");
		
		System.out.println(pato1.nombre); //Lucas 
		System.out.println(pato2.nombre); //Donald
		System.out.println(pato3.nombre); //Feo
		
		System.out.println(pato1.contador); //3 !!ALERT
		System.out.println(pato2.contador); //3 !!ALERT
		System.out.println(pato3.contador); //3 !!ALERT
		
		System.out.println("**********");
		
		System.out.println(Pato.contador);
		System.out.println(Pato.contador);
		System.out.println(Pato.contador);

	}

}
