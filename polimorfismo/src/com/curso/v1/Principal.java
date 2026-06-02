package com.curso.v1;

public class Principal {

	public static void main(String[] args) {
		
		Operacion ope = new Operacion(8,4);
		System.out.println(ope.toStringOperacion());
		System.out.println(ope.ejecuta());
		
		Operacion suma = new Suma(8,4);
		System.out.println(((Suma)suma).toStringSuma());
		System.out.println(suma.ejecuta());
		
		Operacion resta = new Resta(8,4);
		System.out.println(((Resta)resta).toStringResta());
		System.out.println(resta.ejecuta());
		
		Operacion multi = new Multi(8,4);
		System.out.println(((Multi) multi).toStringMulti());
		System.out.println(multi.ejecuta());
		
	}

}
