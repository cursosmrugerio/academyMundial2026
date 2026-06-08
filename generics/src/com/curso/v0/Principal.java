package com.curso.v0;

import java.util.*;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("Principal");
		
		List<String> nombres = new ArrayList<>();
		
		nombres.add("Patrobas");
		nombres.add("Filologo");
		//nombres.add(Double.valueOf(5.0)); //DON'T COMPILE
		nombres.add("Epeneto");
		nombres.add("Andronico");
		
		for(String nombre: nombres) {
			System.out.println(nombre);
			System.out.println(nombre.length());
		}
		
	}

}
