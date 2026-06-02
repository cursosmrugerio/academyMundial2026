package com.curso.v0;

public class Operacion {
	
	int x;
	int y;
	
	public Operacion(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toStringOperacion() {
		return "Operacion [x=" + x + ", y=" + y + "]";
	}

	int ejecuta() {
		return 0;
	}

}
