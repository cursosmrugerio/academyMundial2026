package com.simulador.v0A;


public class Principal {
	
	enum Coffee{ //static
		EXPRESSO,
		MOCHA,
		LATTE
	}

	public static void main(String[] args) {
		for(Coffee c:Coffee.values())
			System.out.println(c);
	}

}
