package com.curso.v0;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Principal3 {

	public static void main(String[] args) {
		
		List<Integer> values = Arrays.asList(2, 4, 6, 9); //1
		
		Predicate<Integer> check = (Integer i) -> {
		    System.out.println("Checking: "+i);
		    return i == 4; //2
		};
		
		Predicate even = (Object i)-> (Integer)i%2==0;
		
		//Lazy
		values.stream() //Genero un Stream
			.filter(check) //Intermediate Operation //2 //4
			.filter(even) //Intermediate Operation //4
			.count(); //Terminal Operation 
		
		//Checking //2
		//Checking //4
		//Checking //6
		//Checking //9
		
		//count = 1
		
	}

}
