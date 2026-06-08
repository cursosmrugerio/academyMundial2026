package com.curso.v0;

import java.util.*;

public class Principal1 {

	public static void main(String[] args) {
		
		System.out.println("Principal1");
		
		List nombres = new ArrayList();
		
		nombres.add("Patrobas");
		nombres.add("Filologo");
		nombres.add("Epeneto");
		nombres.add("Andronico");
		
		for(Object nombre: nombres) {
			System.out.println(nombre);
			System.out.println(((String)nombre).length());
		}
		

	}

}
