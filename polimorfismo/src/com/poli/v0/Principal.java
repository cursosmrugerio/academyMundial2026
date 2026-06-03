package com.poli.v0;

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
		
		Animal a;
		
		a = new Perro();
		a.makeSound();
		
		a = new Pato();
		a.makeSound();
		
		a = new Gato();
		a.makeSound();
		
	}

}
