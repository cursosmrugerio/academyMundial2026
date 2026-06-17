package com.curso.v1;

import java.util.Arrays;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		
		Operacion[] operaciones = {
				(x,y) -> x+y,
				(w,z) -> w-z,
				(pato1,pato2) -> (int)Math.pow(pato1,pato2),
				(int1, int2) -> int1*int2
		};
		
		List<Operacion> lista1 = Arrays.asList(operaciones);
		
		lista1.forEach(null);


	}

}
