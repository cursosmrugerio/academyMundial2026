package com.curso.v0;

public class Suma implements Operacion {
	
	int x;
	int y;
	
	Suma(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	//public -> protected -> default -> private
	
	@Override
	public int ejecuta() {
		return x+y;
	}

	@Override
	public String toString() {
		return "Suma [x=" + x + ", y=" + y + "]";
	}
	
}
