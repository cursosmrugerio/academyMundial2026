package com.curso.v0;

import java.util.*;

public class Principal2 {

	public static void main(String[] args) {
		
		System.out.println("Principal2");
		
		List nombres = new ArrayList();
		
		nombres.add("Patrobas");
		nombres.add("Filologo");
		nombres.add(Double.valueOf(5.0));
		nombres.add("Epeneto");
		nombres.add("Andronico");
		
		
		for(Object nombre: nombres) {
			System.out.println(nombre);
			if (nombre instanceof String)
				System.out.println(((String)nombre).length());
		}
		

	}

}
