package com.curso.v1;

// Class final NO SE PUEDE HEREDAR
// class Cadena extends String {}
// final class Animal{}

class Animal{
	//NO SE PUEDE SOBREESCRIBIR
	final void makeSound() {}
	
	//NO SE PUEDE OCULTAR
	final static void feed() {}
}
class Pato extends Animal{
	//METODO FINAL NO SE PUEDE SOBREESCRIBIR
	//@Override //SOBREESCRITURA
	//void makeSound() {} 
	
	//LOS METODOS static NO SE SOBREESCRIBEN
	//NO APLICA @Override EN static
	//HIDDEN (Ocultar)
	//static void feed() {}
}

public class Principal {
	
	public static void main(String[] args) {
		Animal animal = new Pato();
	}

}
