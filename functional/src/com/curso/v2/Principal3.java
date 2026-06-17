package com.curso.v2;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class Principal3 {

	public static void main(String[] args) {
		
		//DEFINICION DE UNA LAMBDA
		BinaryOperator<Integer> bo1 = (x,y) -> x+y;
		
		List<BinaryOperator<Integer>> operaciones = List.of(
				bo1, //DEFINICION
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		);
		
		
		Consumer<BinaryOperator<Integer>> consumer = 
				binaryOperator -> System.out.println(binaryOperator);
		
		operaciones.forEach(consumer);
		
		System.out.println("*************");
		
		Consumer<BinaryOperator<Integer>> consumer2 = 
				binaryOperator -> System.out.println(binaryOperator.apply(8,4));
				
		operaciones.forEach(consumer2);
		
	}

}
