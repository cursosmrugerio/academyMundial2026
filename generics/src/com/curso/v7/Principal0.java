package com.curso.v7;

class Bici{}
class Motoneta{}
class Moto{}

class Pato{}

class Contenedor<T>{
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
		Bici bici2 = contenedor1.getT();
		
		Contenedor<Motoneta> contenedor2 = new Contenedor<>(motoneta);
		Motoneta motoneta2 = contenedor2.getT();
		
		Contenedor<Moto> contenedor3 = new Contenedor<>(moto);
		Moto moto2 = contenedor3.getT();
		
		Contenedor<Pato> contenedor4 = new Contenedor<>(new Pato());
		Pato pato = contenedor4.getT();
		
		
		
	}
}
