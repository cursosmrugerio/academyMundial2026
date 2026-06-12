package com.curso.v0;

import java.util.List;

public class Principal3 {
	
	public static void main(String[] args) {
		List<Integer> lista = null;
		//List<int> other = null;
		
		float f1 = 0.3f;
		float f2 = 0.2f;
		
		float f3 = f1*1000 - f2*1000;
		
		System.out.println("float: "+ f3/1000);
		
		double d1 = 0.3;
		double d2 = 0.2;
		
		double d3 = d1*1000 - d2*1000;
		
		System.out.println("double: "+d3/1000);
		
		
	}

}
