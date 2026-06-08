package com.upperBounded.v0;

import java.util.*;

class Figura{}
class Triangulo extends Figura{}
class Cuadrado extends Figura{}
class Rectangulo extends Figura{}

public class Principal {
	
	public static void main(String[] args) {
		
		System.out.println("UpperBounded Wildcard");
		
		List<Object> listaObject = new ArrayList<>();
		listaObject.add(new Object());
		listaObject.add("Hola");
		listaObject.add(12.34); //Autoboxing
		listaObject.add(new Figura());
		listaObject.add(new Cuadrado());
		listaObject.add(new Rectangulo());
		//showUpperBounded(listaObject);
		//List<? extends Figura> list1 = listaObject;
		
		List<String> listaString = new ArrayList<>();
		listaString.add("Hola");
		listaString.add("Mundo");
		//showUpperBounded(listaString);
		//List<? extends Figura> list1 = listaString;
		
		List<Figura> listaFigura = new ArrayList<>();
		listaFigura.add(new Figura());
		listaFigura.add(new Cuadrado());
		listaFigura.add(new Triangulo());
		showUpperBounded(listaFigura);
		List<? extends Figura> list1 = listaFigura;
		//list1.add(new Triangulo()); //NO PERMITE ADD
		
		List<Triangulo> listaTriangulo = new ArrayList<>();
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		listaTriangulo.add(new Triangulo());
		showUpperBounded(listaTriangulo);
		List<? extends Figura> list2 = listaTriangulo;
		
		List<Cuadrado> listaCuadrado = new ArrayList<>();
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		listaCuadrado.add(new Cuadrado());
		showUpperBounded(listaCuadrado);
		List<? extends Figura> list3 = listaCuadrado;
	}
	
	//Upper Bounded Wildcard
	private static void showUpperBounded(List<? extends Figura> list) {
		System.out.println("********");
		for (Object o:list)
			System.out.println(o);
	}

}