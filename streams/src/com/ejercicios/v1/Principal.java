package com.ejercicios.v1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {

	public static void main(String[] args) {

		Stream<String> ohMy = Stream.of("lions","gato", "pato","tigers", "bears");
		
		//Function<String,Integer> function = name -> name.length();
		Function<String,Integer> function = String::length;
		
		Collector<String,?,Map<Integer,List<String>>> collector  = 
				Collectors.groupingBy(function);
		
		Map<Integer, List<String>> map = 
				ohMy.collect( collector ); 
		
		System.out.println(map);

		//{5=[lions,bears],6=[tigers]}
	}

}
