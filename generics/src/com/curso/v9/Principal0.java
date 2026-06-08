package com.curso.v9;

abstract class Transporte{
	@Override
	public String toString() {
		return "Transporte: "+ this.getClass().getSimpleName();
	}
};

class Bici extends Transporte{}
class Motoneta extends Transporte{}
class Moto extends Transporte{}

class Pato{}

class Contenedor<T extends Transporte>{
	private T t; //HAS-A

	public Contenedor(T t) {
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
}

public class Principal0 {
	public static void main(String[] args) {
		Bici bici = new Bici();
		Motoneta motoneta = new Motoneta();
		Moto moto = new Moto();
		
		Contenedor<Bici> contenedor1 = new Contenedor<>(bici);
		System.out.println(contenedor1.getT());
		
		Contenedor<Motoneta> contenedor2 = new Contenedor<>(motoneta);
		System.out.println(contenedor2.getT());
		
		Contenedor<Moto> contenedor3 = new Contenedor<>(moto);
		System.out.println(contenedor3.getT());
		
		
		
	}
}
