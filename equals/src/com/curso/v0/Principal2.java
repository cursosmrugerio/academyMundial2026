package com.curso.v0;

public class Principal2 {

	public static void main(String[] args) {
		String s = "Hello";
		String t = new String(s);
		
		if ("Hello".equals(s))
			System.out.println("one");
		if (t == s)
			System.out.println("two");
		if (t.intern() == s)
			System.out.println("three");
		if ("Hello" == s)
			System.out.println("four");
		if ("Hello".intern() == t)
			System.out.println("five");

	}

}
