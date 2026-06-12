package com.curso.v0;

public class Principal5 {

	public static void main(String[] args) {

		int i = 10;
		long l1 = i;
		long l2 = 50;
		
		//System.out.println(Integer.MAX_VALUE);
		
		long l3 = -2147483649L;
		
		long l4 = 2147483647L + i;
		
		System.out.println(l4);
		
		System.out.println("********");
		
		//-128 al 127
		byte b1 = 127;
		System.out.println(b1);
		
		b1 += 1;
		
		System.out.println(b1); //-128
		
	}

}
