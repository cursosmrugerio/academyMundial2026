package com.curso.v0;

public class Principal5 {
	
	public static void main(String[] args) {
		
		String s2 = "";
		s2 += 2; //s2 = s2 + 2
		s2 += 'c'; //s2 = s2 + 'c'
		s2 += false; //s2 = s2 + false
		
		//System.out.println(s2); //2cfalse
		
		if ( s2 == "2cfalse") System.out.println("==");
		if ( s2.equals("2cfalse")) System.out.println("equals");
		
		System.out.println("End Program");
		
	}

}
