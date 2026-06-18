package com.curso.v0;

@FunctionalInterface
public interface Predicado<T> {
	
	boolean probar(T t);
	
	static void methodStatic() {
		System.out.println("static");
	}
	
	static void methodStatic2() {
		System.out.println("static2");
	}
	
	default void methodDefault() {
		System.out.println("default");
	}
	
	default void methodDefault2() {
		System.out.println("default2");
	}

}
