package com.ejercicios.v0;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

record Sale(String product, String category, double amount,
        String region, LocalDate date) {}

public class SalesAnalyzer {
	
	public static void main(String[] args) {
		
		//NO SE PERMITE: add, remove, listaInmutable
		List<Sale> sales = List.of(
	            new Sale("Laptop",  "Tech",   25000, "CDMX", LocalDate.of(2026, 1, 15)),
	            new Sale("Phone",   "Tech",   15000, "GDL",  LocalDate.of(2026, 1, 20)),
	            new Sale("Desk",    "Office",  8000, "CDMX", LocalDate.of(2026, 2, 1)),
	            new Sale("Chair",   "Office",  5000, "MTY",  LocalDate.of(2026, 2, 10)),
	            new Sale("Monitor", "Tech",   12000, "CDMX", LocalDate.of(2026, 2, 15)),
	            new Sale("Tablet",  "Tech",   10000, "GDL",  LocalDate.of(2026, 1, 25)),
	            new Sale("Lamp",    "Office",  2000, "MTY",  LocalDate.of(2026, 1, 30))
	        );
		
		// 1. Ingreso total
//        double total = sales.stream()
//        				.mapToDouble(___)
//        				.sum();
		
		Stream<Sale> streamSales = sales.stream();
		
		DoubleStream doubleStream = streamSales.mapToDouble(v -> v.amount());
		
		double total = doubleStream.sum();
		
        System.out.println("=== Ingreso Total ===");
        System.out.printf("$%,.2f%n", total);
        
        
		
	}

}
