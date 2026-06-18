package com.curso.v1;

@FunctionalInterface
public interface Predicado<T> {
	
	boolean probar(T t);
	
	//Funciones de orden superior (Regresar un bloque de código)
	static <Z> Predicado<Z> and(Predicado<Z> pre1, 
								Predicado<Z> pre2){
		
		return x -> pre1.probar(x) && pre2.probar(x);
	}
}
