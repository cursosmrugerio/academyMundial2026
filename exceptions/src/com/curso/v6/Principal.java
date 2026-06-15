package com.curso.v6;

public class Principal {

	public static void main(String[] args)  {
		
		ConexionMongoDb conn1 = new ConexionMongoDb("6063");
		
		try {
			conn1.open();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Program End");
		
	}
}
