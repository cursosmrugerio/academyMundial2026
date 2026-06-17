package com.curso.v2;

import java.util.List;
import java.util.function.BinaryOperator;

public class Principal2 {

	public static void main(String[] args) {
		
		List<BinaryOperator<Integer>> operaciones = List.of(
				(x,y) -> x+y, //DEFINICION
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		);
		
		//                                           EJECUTA LA LAMBDA
		operaciones.forEach(bo -> System.out.println(bo.apply(8,4)));
		
	}

}
