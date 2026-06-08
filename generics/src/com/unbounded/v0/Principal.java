package com.unbounded.v0;

import java.util.*;

class Figura{}
class Triangulo extends Figura{}
class Cuadrado extends Figura{}
class Rectangulo extends Figura{}

public class Principal {
	
	public static void main(String[] args) {
		
		List<Object> listaObject = new ArrayList<>();
		listaObject.add(new Object());
		listaObject.add("Hola");
		listaObject.add(12.34); //Autoboxing
		listaObject.add(new Figura());
		listaObject.add(new Cuadrado());
		listaObject.add(new Rectangulo());
		show(listaObject);
		showUnbounded(listaObject);
		List<?> list1 = listaObject;
		//list1.add(new Object()); //NO SE PUEDE ADD
		
		List<String> listaString = new ArrayList<>();
		listaString.add("Hola");
		listaString.add("Mundo");
		//show(listaString); //DON'T COMPILE
		showUnbounded(listaString);
		List<?> list2 = listaString;
		//list2.add("Hello"); //NO SE PUEDE ADD
		
		List<Figura> listaFigura = new ArrayList<>();
		listaFigura.add(new Figura());
		listaFigura.add(new Cuadrado());
		listaFigura.add(new Triangulo());
		//show(listaFigura); //DON'T COMPILE
		showUnbounded(listaFigura);
		List<?> list3 = listaFigura;
		//list3.add(new Figura()); //NO SE PUEDE ADD
	}

	private static void show(List<Object> listaObject) {
		for (Object o:listaObject)
			System.out.println(o);
	}
	
	//Unbounded Wildcard
	private static void showUnbounded(List<?> list) {
		//list.add(new Object());
		System.out.println("********");
		for (Object o:list)
			System.out.println(o);
	}

}
