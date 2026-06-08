package com.curso.v1;

import java.util.ArrayList;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("Principal");
		
		List<String> nombres = new ArrayList<>();
		
		nombres.add("Patrobas");
		nombres.add("Filologo");
		nombres.add("Epeneto");
		nombres.add("Andronico");
		
		//show(nombres);
		//showObject(nombres); //DON'T COMPILE
		showUnboundedWildcard(nombres);

	}
	
	static void showUnboundedWildcard(List<?> names) {
		for (Object name: names) 
			System.out.println(name);
	}
	
	static void showObject(List<Object> names) {
		for (Object name: names) 
			System.out.println(name);
	}
	
	static void show(List<String> names) {
		for (String name: names) {
			System.out.println(name);
			System.out.println(name.length());
		}
	}
}
