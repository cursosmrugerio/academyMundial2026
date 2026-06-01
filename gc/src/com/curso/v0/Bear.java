package com.curso.v0;

public class Bear {
	private Bear pandaBear; //HAS-A

	private void roar(Bear b) {
		System.out.println("Roar!");
		pandaBear = b; 
	}

	public static void main(String[] args) {
		Bear brownBear = new Bear(); //<== ***GC //1
		Bear polarBear = new Bear(); //<== ***GC //2
		brownBear.roar(polarBear); 
		polarBear = null; 
		brownBear = null; //<========Despues de la linea GC ACTUA
	}
}
