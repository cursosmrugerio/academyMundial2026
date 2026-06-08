package com.curso.v0;

import java.util.*;

public class Principal0 {

	public static void main(String[] args) {
		
		System.out.println("Principal0");
		
		List nombres = new ArrayList();
		
		nombres.add("Patrobas");
		nombres.add("Filologo");
		nombres.add("Epeneto");
		nombres.add("Andronico");
		
		for(Object nombre: nombres)
			System.out.println(nombre);
		

	}

}
