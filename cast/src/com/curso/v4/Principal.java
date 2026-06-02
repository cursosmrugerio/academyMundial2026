package com.curso.v4;

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
		System.out.println("V4");
		Ave ave01 = new Aguila(); //BUENA PRACTICA (POLIMORFISMO)
		
		((Aguila)ave01).volarAguila();
	}
}
