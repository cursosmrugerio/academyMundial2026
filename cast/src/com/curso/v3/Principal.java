package com.curso.v3;

class Ave{
	void volar(){
		System.out.println("Ave volar");
	}
	
//	void volarAguila() {
//		System.out.println("volarAguila()");
//	}
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
		System.out.println("V3");
		Ave ave01 = new Aguila(); //BUENA PRACTICA (POLIMORFISMO)
		ave01.volar();
		
		Aguila aguila = (Aguila)ave01;
		
		aguila.volarAguila();
	}
}
