package com.curso.hashcode;

class Pato{
	
	@Override
	public int hashCode() {
		return 1000;
	}
	
}

public class Principal {

	public static void main(String[] args) {
		
		Pato pato1 = new Pato();
		Pato pato2 = new Pato();
		
		System.out.println(pato1==pato2); //false
		
		System.out.println(pato1);
		System.out.println(pato2);

	}

}
