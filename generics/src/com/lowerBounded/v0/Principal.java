package com.lowerBounded.v0;

import java.util.*;

class Figura{}
class Triangulo extends Figura{}
class Cuadrado extends Figura{}
class Rectangulo extends Figura{}

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("LowerBounded Wildcard");
		
		List<Object> listaObject = new ArrayList<>();
		listaObject.add(new Object());
		listaObject.add("Hola");
		listaObject.add(12.34); //Autoboxing
		listaObject.add(new Cuadrado());
		listaObject.add(new Rectangulo());
		showLowerBounded(listaObject);
		
		List<String> listaString = new ArrayList<>();
		listaString.add("Hola");
		listaString.add("Mundo");
		//showLowerBounded(listaString);
		
		List<Figura> listaFigura = new ArrayList<>();
		listaFigura.add(new Cuadrado());
		listaFigura.add(new Rectangulo());
		listaFigura.add(new Triangulo());
		showLowerBounded(listaFigura);

		List<Triangulo> listaTriangulo = new ArrayList<>();
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		//showLowerBounded(listaTriangulo);
		
		List<Cuadrado> listaCuadrado = new ArrayList<>();
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		//showLowerBounded(listaCuadrado);
	}
	
	//Lower Bounded Wildcard
	private static void showLowerBounded(List<? super Figura> list) {
		list.add(new Figura());
		list.add(new Cuadrado());
		//list.add(new Object()); //TIENE QUE SER UNA SUBCLASE DE FIGURA
		System.out.println("********");
		for (Object o:list)
			System.out.println(o);
	}

}