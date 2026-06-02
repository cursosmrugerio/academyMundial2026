package com.curso.v5;

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

class AguilaReal extends Aguila{
	@Override
	void volar(){
		System.out.println("Aguila Real volar");
	}
	
	void volarAguilaReal() {
		System.out.println("volarAguilaReal");
	}
}

class AguilaCalva extends Aguila{
	@Override
	void volar(){
		System.out.println("Aguila Calva volar");
	}
	
	void volarAguilaCalva() {
		System.out.println("volarAguilaCalva");
	}
}

public class Principal {
	public static void main(String[] args) {
		
		AguilaCalva aguilaCalva = new AguilaCalva(); //<==
		//aguilaCalva.volarAguilaCalva();
		
		//Upcasting, cast hacia arriba
		Aguila aguila = (Aguila)aguilaCalva;
		
		Ave ave = (Ave)aguila;
		
		//Downcasting, cast hacia abajo
		
		Aguila aguila2 = (Aguila)ave;
		
		
		AguilaReal aguilaReal = (AguilaReal)aguila2;
		aguilaReal.volarAguilaReal(); //ClassCastException
		
		AguilaCalva aguilaCalva2 = (AguilaCalva) aguila2;
		aguilaCalva2.volarAguilaCalva();
		
		
	}
}
