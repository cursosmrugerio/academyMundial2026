package com.curso.v0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		
		Operacion[] operaciones = {
				(x,y) -> x+y,
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		};
		
		List<Operacion> lista1 = Arrays.asList(operaciones);
		System.out.println(lista1.getClass().getName());
		//lista1.add((x,y) -> x+y);
		//lista1.remove(0);
		
		
		List<Operacion> lista2 = new ArrayList<>();
		System.out.println(lista2.getClass().getName());
		lista2.add((x,y) -> x+y);
		lista2.add((x,y) -> x*y);
		lista2.add((pato1,pato2) -> (int)Math.pow(pato1,pato2));
		lista2.remove(0);
		
		
		System.out.println("Fin Programa");

	}

}
