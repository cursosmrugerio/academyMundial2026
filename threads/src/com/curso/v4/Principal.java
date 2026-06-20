package com.curso.v4;

public class Principal {

	public static void main(String[] args) {

		Runnable printInventory = 
				() -> System.out.println("Printing zoo inventory :"+Thread.currentThread().getName());
		
		Runnable printRecords = () -> {
			for (int i = 0; i < 3; i++)
				System.out.println("Printing record: " + i);
		};
		
		System.out.println("begin"); //main
		
		new Thread(printInventory,"Inventory1").run();
		new Thread(printRecords).run();
		new Thread(printInventory,"Inventory2").run();
		
		System.out.println("end");  //main

	}
}
