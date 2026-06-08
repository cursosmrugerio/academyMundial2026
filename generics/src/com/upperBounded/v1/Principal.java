package com.upperBounded.v1;

import java.util.*;

interface Figura{}
class Triangulo implements Figura{}
class Cuadrado implements Figura{}
class Rectangulo implements Figura{}

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("UpperBounded Wildcard");
		
		List<Object> listaObject = new ArrayList<>();
		listaObject.add(new Object());
		listaObject.add("Hola");
		listaObject.add(12.34); //Autoboxing
		listaObject.add(new Cuadrado());
		listaObject.add(new Rectangulo());
		//showUpperBounded(listaObject);
		
		List<String> listaString = new ArrayList<>();
		listaString.add("Hola");
		listaString.add("Mundo");
		//showUpperBounded(listaString);
		
		List<Figura> listaFigura = new ArrayList<>();
		listaFigura.add(new Cuadrado());
		listaFigura.add(new Rectangulo());
		listaFigura.add(new Triangulo());
		showUpperBounded(listaFigura);
		
		List<Triangulo> listaTriangulo = new ArrayList<>();
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		showUpperBounded(listaTriangulo);
		
		List<Cuadrado> listaCuadrado = new ArrayList<>();
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		showUpperBounded(listaCuadrado);
	}
	
	//Upper Bounded Wildcard
	private static void showUpperBounded(List<? extends Figura> list) {
		System.out.println("********");
		for (Object o:list)
			System.out.println(o);
	}

}