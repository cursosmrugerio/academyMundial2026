package com.curso.v5;

public class Principal {

	public static void main(String[] args)  {
		
		ConexionMongoDb conn1 = new ConexionMongoDb("6063");
		
		//PERO: No haber cerrado la Conexion
		try {
			conn1.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Program End");
		
	}
}
