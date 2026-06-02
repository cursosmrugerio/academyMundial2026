package com.curso.v0;

public class Principal {
	
	public static void main(String[] args) {
		Conexion con1 = Conexion.getInstance();
		Conexion con2 = Conexion.getInstance();
		Conexion con3 = Conexion.getInstance();
		Conexion con4 = Conexion.getInstance();
		Conexion con5 = Conexion.getInstance();
		Conexion con999 = Conexion.getInstance();
		
		System.out.println(con1==con2); //true //null == null
		System.out.println(con1.equals(con2)); //NullPointerException
	}

}
