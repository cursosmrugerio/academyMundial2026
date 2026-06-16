package com.curso.v0;

import java.util.HashSet;
import java.util.Set;

public class Principal1 {
	
	public static void main(String[] args) {
		String nombre1 = new String("patrobas");
		String nombre2 = "Andronico";
		String nombre3 = new String("patrobas");
		
		Set<String> set = new HashSet<>();
		set.add(nombre1);
		set.add(nombre2);
		set.add(nombre3);
		
		for(String n:set) {
			System.out.println(n);
		}
		
		
		
	}  

}
