package com.curso.v2;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class Principal {

	public static void main(String[] args) {
		//NO SE PUEDEN MANEJAR LOS GENERICS CON arrays
		/*
		BinaryOperator<Integer>[] operaciones = {
				(x,y) -> x+y,
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		};
		
		List<BinaryOperator<Integer>> lista1 = Arrays.asList(operaciones);
		
		lista1.forEach(System.out::println);
		*/
	}

}
