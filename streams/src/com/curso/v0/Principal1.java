package com.curso.v0;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Principal1 {

	public static void main(String[] args) {
		
		List<Integer> values = Arrays.asList(2, 4, 6, 9); 
		
		Predicate<Integer> check0 = i -> i==4;
		
		Predicate<Integer> check1 = i -> {
		    System.out.println("Checking");
		    return i == 4; //2
		};
		
		Predicate<Integer> check2 = (i) -> {
		    System.out.println("Checking");
		    return i == 4; //2
		};
		
		Predicate<Integer> check3 = (Integer i) -> {
		    System.out.println("Checking");
		    return i == 4; //2
		};

	}

}
