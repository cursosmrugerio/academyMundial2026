package com.curso.v2;

class Animal{
	static void feed() {
		System.out.println("Animal feed()");
	}
}
class Pato extends Animal{
	//@Override
	static void feed() {
		System.out.println("Pato feed()");
	}
}

public class Principal {
	public static void main(String[] args) {
		Animal animal = new Pato();
		((Pato)animal).feed(); 
	}
}
