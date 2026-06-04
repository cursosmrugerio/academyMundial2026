package com.curso.v1;

public abstract class OperacionAbs implements Operacion {
	
	int x;
	int y;
	
	OperacionAbs(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+"[x=" + x + ", y=" + y + "]";
	}

}
