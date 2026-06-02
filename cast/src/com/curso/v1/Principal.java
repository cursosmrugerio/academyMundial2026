package com.curso.v1;

class Ave{
	void volar(){
		System.out.println("Ave volar");
	}
}

class Aguila extends Ave{
	@Override
	void volar(){
		System.out.println("Aguila volar");
	}
}

public class Principal {
	public static void main(String[] args) {
		Ave ave01 = new Aguila();
		ave01.volar();
	}
}
