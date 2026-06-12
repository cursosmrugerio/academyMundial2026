package com.curso.v1;

public class Principal {

	public static void main(String[] args) {

		int myFavoriteNumber = 8;

		int bird = ~myFavoriteNumber;
		
		//complemento bit a bit o bitwise NOT.
		//~x = -(x + 1)
		
		System.out.println(bird); //-9
	}

}
