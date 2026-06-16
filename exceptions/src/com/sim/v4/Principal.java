package com.sim.v4;

class MyException extends Exception{}
//Subclases de MyException (Hijas)
class MyException1 extends MyException{}
class MyException2 extends MyException{}
//Subclass de MyException2 (Nieta)
class MyException3 extends MyException2{}

public class Principal {
	
	public static void main(String[] args) {
		
		try {
			 // code that could potentially throw any of 
			 // the above mentioned exceptions
//			throw new MyException();
//			throw new MyException1();
//			throw new MyException2();
			throw new MyException3();
		}
		
		catch(MyException3 e){ }
		catch(MyException e){ }
		
	}

}
