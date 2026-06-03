package com.curso.v6;

public class Resta extends Operacion {
	
	Resta(int x,int y){
		super(x,y);
	}
	
	@Override
	int ejecuta() {
		return x-y;
	}
	
}
