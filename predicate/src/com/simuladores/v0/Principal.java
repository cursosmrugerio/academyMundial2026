package com.simuladores.v0;

import java.util.*;
import java.util.function.Predicate;

//In Data.java
class Data {
	int value;

	Data(int value) {
		this.value = value;
	}
}

class MyPredicado implements Predicate<Data>{
	@Override
	public boolean test(Data t) {
		return t.value%3==0 && t.value==3;
	}
}


public class Principal {

	public static void main(String[] args) {
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data(1));
		al.add(new Data(2));
		al.add(new Data(3));
		al.add(new Data(4));
		al.add(new Data(6));
		
		new Principal().printUsefulData(al,new MyPredicado());
		
		System.out.println("Fin de programa");
	}

	
	public void printUsefulData(ArrayList<Data> dataList, Predicate<Data> p) {
		for (Data d : dataList) {
			if (p.test(d))
				System.out.println(d.value);
		}
	}

}

