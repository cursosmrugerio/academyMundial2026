package com.curso.v7;

public class Principal {

	public static void main(String[] args)  {
		
		System.out.println("V7");
	
		//Try With Resource
		try (ConexionMongoDb conn1 = new ConexionMongoDb("6063")) {
			conn1.open();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		System.out.println("Program End V7");
		
	}
}
