package com.curso.v0;

public class Conexion {
	
	static private Conexion conexion;
	String port;

	private Conexion(String port) {
		this.port = port;
	}
	
	static public Conexion getInstance() {
		return conexion;
	}
	
}
