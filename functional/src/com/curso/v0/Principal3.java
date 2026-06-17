package com.curso.v0;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Principal3 {

	public static void main(String[] args) {

		//SOLO DEFINICIONES DE LAMBDA
		Supplier<String> supp = () -> "Hello";
		Runnable runnable = () -> System.out.println("Hola");
		Consumer<Double> cons = d -> System.out.println("Double: "+d);
		BiConsumer<Integer,Boolean> biCons = 
				(x,y) -> System.out.println((x > 10)==y);
		Predicate<StringBuilder> pre = 	pato -> pato.isEmpty();
		
		//EJECUCION DE LAMBDAS
		System.out.println(supp.get());
		runnable.run();
		cons.accept(8.0);
		biCons.accept(8,false); //true
		System.out.println(pre.test(new StringBuilder(""))); //true
		System.out.println(pre.test(new StringBuilder())); //true
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("Hola Mundo Java Academy Xideral");
//		System.out.println(sb);
		
		
		
		
	}

}
