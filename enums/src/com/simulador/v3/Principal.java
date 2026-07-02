package com.simulador.v3;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

enum  Coffee{
	
	EXPRESSO("Very Strong"),
	MOCHA("Bold"),
	LATTE("Mild");
	
	String strenght;
	
	Coffee(String strenght) {
		this.strenght = strenght;
	}
	
	@Override
	public String toString() {
		return strenght;
	}
}

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("V3");
		
		List<Coffee> lista = List.of(Coffee.values());
		
		Stream<Coffee> stream = lista.stream();
		
		Consumer<Coffee> consumer = t -> System.out.print(t.name()+":"+t+",");
		
		stream.forEach(consumer);
		
	}

}
