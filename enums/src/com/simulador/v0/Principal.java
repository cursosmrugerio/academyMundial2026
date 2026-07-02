package com.simulador.v0;

enum  Coffee{
	
	EXPRESSO,
	MOCHA,
	LATTE
	
}

public class Principal {
	
	public static void main(String[] args) {
		for(Coffee c:Coffee.values())
			System.out.println(c);
	}

}
