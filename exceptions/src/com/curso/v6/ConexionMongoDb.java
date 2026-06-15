package com.curso.v6;

public class ConexionMongoDb {
	
	private String port;

	public ConexionMongoDb(String port) {
		this.port = port;
	}
	
	public void open() throws Exception {
		System.out.println("Abrir conexion MongoDB, port: "+port);
		//throw new Exception("Error open conexion MongoDB");
	}
	
	public void close() throws Exception {
		System.out.println("Cerrar conexion MongoDB");
		//throw new Exception("Error close conexion MongoDB");
	}
	

}
