package com.simulador.v1;

enum  Coffee{
	
	EXPRESSO("Very Strong"),
	MOCHA("Bold"),
	LATTE("Mild");
	
	String strenght;
	
	Coffee(String strenght) {
		this.strenght = strenght;
	}
	
	@Override
	public String toString() {
		return strenght;
	}
}

public class Principal {
	
	public static void main(String[] args) {
		for(Coffee c:Coffee.values())
			System.out.print(c.name()+":"+c+",");
		
	}

}
