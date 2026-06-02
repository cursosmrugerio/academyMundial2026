package com.curso.v1;

public class Principal {
	
	public static void main(String[] args) {
		Conexion con1 = Conexion.getInstance();
		Conexion con2 = Conexion.getInstance();
		Conexion con3 = Conexion.getInstance();
		Conexion con4 = Conexion.getInstance();
		Conexion con5 = Conexion.getInstance();
		Conexion con999 = Conexion.getInstance();
		
		System.out.println(con1);
		System.out.println(con2);
		System.out.println(con3);
		System.out.println(con999);
		
		System.out.println(con1==con999); // true
		System.out.println(con1.equals(con999)); //true
	}

}
