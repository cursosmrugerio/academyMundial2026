package com.curso.v0;

public class Suma extends Operacion {
	
	Suma(int x,int y){
		super(x,y);
	}

	public String toStringSuma() {
		return "Suma [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	int ejecuta() {
		return x+y;
	}
	
}
