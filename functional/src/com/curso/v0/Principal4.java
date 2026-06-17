package com.curso.v0;

import java.util.function.*;

public class Principal4 {

	public static void main(String[] args) {

		//SOLO DEFINICIONES DE LAMBDA
		Function<Integer,Double> function = x -> Double.valueOf(x); 
		
		//EJECUCION DE LA LAMBDA
		System.out.println(function.apply(999));
		
		
	}

}
