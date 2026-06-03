package com.curso.v0;

public class Division implements Operacion {
	
	int x;
	int y;
	
	Division(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int ejecuta() {
		return x/y;

	}
	
	@Override
	public String toString() {
		return "Divison [x=" + x + ", y=" + y + "]";
	}
}
