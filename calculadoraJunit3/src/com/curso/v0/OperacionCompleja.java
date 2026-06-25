package com.curso.v0;

public class OperacionCompleja implements Operacion {
	
	byte b;
	char c;
	short sh;
	int i;
	long l;
	double d;
	float f;
	
	//                            1,      2,       3,      4,     5,      6.0,    7.0      
	public OperacionCompleja(byte b, char c, short sh, int i, long l, double d, float f) {
		super();
		this.b = b;
		this.c = c;
		this.sh = sh;
		this.i = i;
		this.l = l;
		this.d = d;
		this.f = f;
	}

	
	//100
	@Override
	public int ejecuta()  {
		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Usa los 7 campos (uno por tipo numerico). Al intervenir d (double) y f (float)
		// la expresion se promueve a double, por eso se castea a int al retornar.
		// 3*4*5 + 6*7 - 1*2 = 60 + 42 - 2 = 100
		return (int) (sh * i * l + d * f - b * c);
	}

}
