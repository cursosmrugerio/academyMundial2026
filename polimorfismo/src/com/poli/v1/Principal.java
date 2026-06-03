package com.poli.v1;

import java.util.Random;

class Animal{
	void makeSound(){
		System.out.println("Animal Sound");
	}
}

class Perro extends Animal{
	@Override
	void makeSound(){
		System.out.println("Guau guau");
	}
}

class Gato extends Animal{
	@Override
	void makeSound(){
		System.out.println("Miau miuau");
	}
}

class Pato extends Animal{
	@Override
	void makeSound(){
		System.out.println("kuak kuak");
	}
}

public class Principal {
	public static void main(String[] args) {
		Animal a = getAnimal();
		a.makeSound();
	}

	private static Animal getAnimal() {
		
		Animal[] animals = {
				new Animal(), //0
				new Gato(), //1
				new Perro(), //2
				new Pato() //3
		};
		
		int aleatorio = new Random().nextInt(animals.length);
		System.out.println("Aleatorio: "+aleatorio);
		
		return animals[aleatorio];
	}
}











