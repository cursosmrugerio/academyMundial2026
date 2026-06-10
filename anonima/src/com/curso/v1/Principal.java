package com.curso.v1;

public class Principal {
	
	//Static Nested Class
	
	static public class Fuecoco implements Pokemon {
		@Override
		public String atacar(double nivelPoder) {
			return "Ataque fuego nivel: "+nivelPoder;
		}
	}

	public static void main(String[] args) {

		System.out.println("Static Nested Class");
		Pokemon pokemon1 = new Principal.Fuecoco();
		
		System.out.println(pokemon1.atacar(80));
		
	}

}
