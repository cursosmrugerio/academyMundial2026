package com.curso.v0;

public class Resta extends Operacion {
	
	Resta(int x,int y){
		super(x,y);
	}

	public String toStringResta() {
		return "Resta [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	int ejecuta() {
		return x-y;
	}
	
}
