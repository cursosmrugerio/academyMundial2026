package com.curso.v0;

public class PolarBear {
	
	//OBJETO MUTABLE
	StringBuilder value = new StringBuilder("t"); //HAS-A
	
	{ value.append("a"); }
	{ value.append("c"); }
	
	private PolarBear() {
		//super(); //DEBE SER PRIMERA LINEA
		value.append("b");
	}
	
	public PolarBear(String s) {
		this(); //DEBE SER PRIMERA LINEA
		System.out.println("PASE POR String");
		value.append(s);
	}
	
	public PolarBear(CharSequence p) { //<***
		System.out.println("PASE POR CharSequence");
		value.append(p);
	}

	public static void main(String[] args) {

		Object bear = new PolarBear();
		//value: tacb
		//System.out.println(((PolarBear)bear).value);
		bear = new PolarBear("f");
		//value: tacbf
		System.out.println(((PolarBear)bear).value);
		
		System.out.println("************");
		
		bear = new PolarBear(new StringBuilder("HOLA"));
		
		System.out.println("************");
		
		bear = new PolarBear((CharSequence)"Hola");

	}

}
