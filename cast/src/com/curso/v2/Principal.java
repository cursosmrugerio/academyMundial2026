package com.curso.v2;

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
	
	void volarAguila() {
		System.out.println("volarAguila()");
	}
}

public class Principal {
	public static void main(String[] args) {
		System.out.println("V2");
		Aguila ave01 = new Aguila();
		ave01.volar();
		ave01.volarAguila();
	}
}
