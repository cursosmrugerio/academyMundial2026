package com.curso.v0;

import java.util.Arrays;
import static java.util.Arrays.sort;

public class Principal1  {

	public static void main(String[] args) {

		String[] textos = {"aa","Az","9","Za","a11","123","zzz","a9","aA"};
		
		//System.out.println(textos);
		
		sort(textos);
		//123, 9, Az, Za, a11, a9, aA, aa, zzz
		
		System.out.println(Arrays.toString(textos));
		
	}

}
