package com.curso.v0A;

@FunctionalInterface
public interface Predicado<T> {
	
	boolean probar(T t);

	static String and(int x) {
		return x+"";
	}
	
	

}
