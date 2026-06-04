package com.curso.v1;

public class Multi extends OperacionAbs {
	
	Multi(int x, int y) {
		super(x, y);
	}

	@Override
	public int ejecuta() {
		return x*y;

	}

}