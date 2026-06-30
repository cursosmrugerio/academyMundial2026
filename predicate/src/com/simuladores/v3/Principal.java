package com.simuladores.v3;

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
		System.out.println("v3");
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data(1));
		al.add(new Data(2));
		al.add(new Data(3));
		
		
		//new Principal().printUsefulData(al , d -> d.value>2 );
		new Principal().printUsefulData(al , (Data d) -> {return d.value>2;} );
		//new Principal().printUsefulData(al , (Data d) -> return d.value>2; );
		//new Principal().printUsefulData(al , Data d -> d.value>2);
		
	}

	
	public void printUsefulData(ArrayList<Data> dataList, Predicate<Data> p) {
		for (Data d : dataList) {
			if (p.test(d))
				System.out.println(d.value);
		}
	}

}

