package com.curso.v5;

class Bici{}
class Motoneta{}
class Moto{}

class Contenedor<T>{
	T t; //HAS-A
}

public class Principal0 {
	public static void main(String[] args) {
		Bici bici = new Bici();
		Motoneta motoneta = new Motoneta();
		Moto moto = new Moto();
		
		Contenedor<Bici> contenedor1 = new Contenedor<>();
		contenedor1.t = bici;
		
		Contenedor<Motoneta> contenedor2 = new Contenedor<>();
		contenedor2.t = motoneta;
		
		Contenedor<Moto> contenedor3 = new Contenedor<>();
		contenedor3.t = moto;
		
	}
}
