package com.curso.v3;

public class Multi extends Operacion {
	
	Multi(int x,int y){
		super(x,y);
	}

	public String toStringMulti() {
		return "Multiplicacion [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	int ejecuta() {
		return x*y;
	}
	
}
