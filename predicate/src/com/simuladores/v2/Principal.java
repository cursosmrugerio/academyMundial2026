package com.simuladores.v2;

import java.util.*;
import java.util.function.Predicate;

//In Data.java
class Data {
	int value;

	Data(int value) {
		this.value = value;
	}
}

public class Principal {

	public static void main(String[] args) {
		System.out.println("v2");
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data(1));
		al.add(new Data(2));
		al.add(new Data(3));
		
		
		new Principal().printUsefulData(al , d -> d.value>2 );
		
		System.out.println("Fin de programa");
	}

	
	public void printUsefulData(ArrayList<Data> dataList, Predicate<Data> p) {
		for (Data d : dataList) {
			if (p.test(d))
				System.out.println(d.value);
		}
	}

}

