package com.q31;

class Exc1 extends Exception {
}

class Exc2 extends Exception {
}

public class Test {

	static void a() throws Exc1 {
		try {
			System.out.print("Try ");
			throw new Exc1();
		} 
		
		catch (Exc1 e) {
			System.out.print("Catch ");
		} 
		
		finally {
			System.out.println("Finally ");
		}
	}

	static void b() throws Exc1, Exc2 {
		throw new Exc2();
	}

}
