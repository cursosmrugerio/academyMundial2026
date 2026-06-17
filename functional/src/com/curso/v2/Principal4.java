package com.curso.v2;

import java.util.List;
//import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;

public class Principal4 {

	public static void main(String[] args) {
		System.out.println("***V4***");
		
		List<IntBinaryOperator> operaciones = List.of(
				(x,y) -> x+y, //DEFINICION
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		);
		
		//                                           EJECUTA LA LAMBDA
		operaciones.forEach(ibo -> System.out.println(ibo.applyAsInt(8,4)));
		
	}

}
