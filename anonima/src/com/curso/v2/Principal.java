package com.curso.v2;

public class Principal {
	
	//Anonymous Class

	public static void main(String[] args) {

		System.out.println("Anonymous Class");
		
		Pokemon pokemon1 = new Pokemon() {
			@Override
			public String atacar(double nivelPoder) {
				return "Atacar con agua: "+nivelPoder;
			}
		};
		
		Pokemon pokemon2 = new Pokemon() {
			@Override
			public String atacar(double nivelPoder) {
				return "Atacar con fuego: "+nivelPoder;
			}
		};
		
		Pokemon pokemon3 = new Pokemon() {
			@Override
			public String atacar(double nivelPoder) {
				return "Atacar con rayos: "+nivelPoder;
			}
		};
		
		System.out.println(pokemon1.atacar(90));
		System.out.println(pokemon2.atacar(100));
		System.out.println(pokemon3.atacar(200));
		
	}

}
