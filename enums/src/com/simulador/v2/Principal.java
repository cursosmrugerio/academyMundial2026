package com.simulador.v2;

enum  Coffee{
	
	EXPRESSO("Very Strong"){
		@Override
		double costo() {
			return 10.0;
		}
	},
	MOCHA("Bold"){
		@Override
		double costo() {
			return 8.5;
		}
	},
	LATTE("Mild"){
		@Override
		double costo() {
			return 7.5;
		}
	};
	
	String strenght;
	
	Coffee(String strenght) {
		this.strenght = strenght;
	}
	
	@Override
	public String toString() {
		return strenght;
	}
	
	abstract double costo();
}

public class Principal {
	
	public static void main(String[] args) {
		for(Coffee c:Coffee.values())
			System.out.print(c.name()+":"+c+",");
		
	}

}
