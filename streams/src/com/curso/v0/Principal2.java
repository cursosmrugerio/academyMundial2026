package com.curso.v0;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Principal2 {

	public static void main(String[] args) {
		
		List<Integer> values = Arrays.asList(2, 4, 6, 9); //1
		
		Predicate<Integer> check = (Integer i) -> {
		    System.out.println("Checking");
		    return i == 4; //2
		};
		
		Predicate even0 = i -> (Integer)i%2==0;
		Predicate even = (Object i)-> (Integer)i%2==0;
		

	}

}
