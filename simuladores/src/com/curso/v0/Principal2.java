package com.curso.v0;

public class Principal2 {
	public static void main(String[] args) {

		for (int x = 0; x < 10; x++ ) {
			x = x++;
			System.out.println(x);
		}
		
		int pig = (short)4;
		pig = pig++;
		long goat = (int)2;
		//goat = goat - (long)1.0;
		goat -= 1.0;
		System.out.print(pig + " - " + goat);
		//                4             1            

	}
}
