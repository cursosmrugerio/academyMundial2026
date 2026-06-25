package com.curso.v0;

public class Suma extends OperacionAbs {
	
	Suma(int x,int y){
		super(x,y);
	}
	
	@Override
	public int ejecuta() {
		return x+y;
	}
	
}
