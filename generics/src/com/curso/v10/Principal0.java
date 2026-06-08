package com.curso.v10;

import java.time.LocalDate;

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
	
	<Z> void showCertification(Z z) {
		System.out.println(t + ", Certificado: "+z);
	}
	
}

public class Principal0 {
	public static void main(String[] args) {
		Bici bici = new Bici();
		Motoneta motoneta = new Motoneta();
		Moto moto = new Moto();
		
		Contenedor<Bici> contenedor1 = new Contenedor<>(bici);
		//System.out.println(contenedor1.getT());
		contenedor1.showCertification(new StringBuilder("AAA111"));
		
		Contenedor<Motoneta> contenedor2 = new Contenedor<>(motoneta);
		//System.out.println(contenedor2.getT());
		contenedor2.showCertification(Double.valueOf(555.55));
		
		Contenedor<Moto> contenedor3 = new Contenedor<>(moto);
		//System.out.println(contenedor3.getT());
		contenedor3.showCertification(LocalDate.now());
		
	}
}
