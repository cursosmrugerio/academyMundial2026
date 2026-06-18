package com.curso.v0B;

@FunctionalInterface
public interface Predicado<T> {
	
	boolean probar(T t);

	static Predicado and(int x) {
		Predicado pre = z -> true;
		return pre; 
	}
	
	

}
