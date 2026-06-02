package com.curso.v1;

public class Conexion {
	
	static private Conexion conexion; //null
	String port;

	private Conexion(String port) {
		this.port = port;
	}
	
	static public Conexion getInstance() {
		if (conexion == null)
			conexion = new Conexion("7777");
		return conexion;
	}
	
	
	
	
}
