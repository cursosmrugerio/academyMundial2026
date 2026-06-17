package com.curso.v0;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Principal2 {

	public static void main(String[] args) {

		//SOLO DEFINICIONES DE LAMBDA
		Supplier<String> sup = () -> "Hello";
		//Supplier<String> sup2 = () -> System.out.println("Hello");
		Runnable run = () -> System.out.println("Hello");
		
		Consumer<Double> con = d -> System.out.println(d);
		
		System.out.println("End Program");
		
		
		
	}

}
