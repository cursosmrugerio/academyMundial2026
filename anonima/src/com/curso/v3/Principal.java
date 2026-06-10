package com.curso.v3;

public class Principal {
	

	public static void main(String[] args) {

		System.out.println("Lambda");
		
		Pokemon pokemon1 = d -> "Atacar con agua: "+d;
		
		Pokemon pokemon2 = zz -> "Atacar con fuego: "+zz;
		
		Pokemon pokemon3 = pato -> "Atacar con rayos: "+pato;
			
		System.out.println(pokemon1.atacar(90));
		System.out.println(pokemon2.atacar(100));
		System.out.println(pokemon3.atacar(200));
		
	}

}
