package com.curso.v1;

public class Potencia extends OperacionAbs {
	
	Potencia(int x, int y) {
		super(x, y);
	}

	@Override
	public int ejecuta() {
		return (int)Math.pow(x, y);
	}
	
}
