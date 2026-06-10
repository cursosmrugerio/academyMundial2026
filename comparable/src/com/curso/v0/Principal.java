package com.curso.v0;

import java.util.Arrays;

public class Principal {

	public static void main(String[] args) {

		String[] textos = {"aa","Az","9","Za","a11","123","zzz","a9","aA"};
		
		//System.out.println(textos);
		
		Arrays.sort(textos);
		//123, 9, Az, Za, a11, a9, aA, aa, zzz
		
		System.out.println(Arrays.toString(textos));
		
		System.out.println(textos[7]); //aa
		System.out.println(textos[6]); //aA
		
		//                      "aa"             "aA"       a>A (ascii)
		System.out.println(textos[7].compareTo(textos[6])); //+
		
		System.out.println(textos[6].compareTo(textos[7])); //A<a //-
		
	}

}
