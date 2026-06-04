package com.curso.v1;

public class Division extends OperacionAbs {
	
	Division(int x, int y) {
		super(x, y);
	}

	@Override
	public int ejecuta() {
		return x/y;
	}
	
}
