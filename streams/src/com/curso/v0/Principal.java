package com.curso.v0;

import java.util.Arrays;
import java.util.List;

public class Principal {

	public static void main(String[] args) {
		
		List<Integer> values = Arrays.asList(2, 4, 6, 9); 
		
		//System.out.println(values.getClass().getName());
		//values.add(99); //UnsupportedOperationException
		//values.remove(0); //UnsupportedOperationException
		values.set(2, 66); //SI ES MUTABLE
		//values.forEach(x->System.out.println(x)); //Lambda
		values.forEach(System.out::println); //Method Reference

	}

}
