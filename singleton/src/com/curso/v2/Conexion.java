package com.curso.v2;

public class Conexion {
	
	static private Conexion conexion = new Conexion("7777");
	String port;

	private Conexion(String port) {
		this.port = port;
	}
	
	static public Conexion getInstance() {
		return conexion;
	}
	
}
