package com.ejercicios.v2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Sale(String product, String category, double amount, String region, LocalDate date) {}

public class SalesAnalyzer {
	
	public static void main(String[] args) {
		
		List<Sale> sales = List.of(
	            new Sale("Laptop",  "Tech",   25000, "CDMX", LocalDate.of(2026, 1, 15)),
	            new Sale("Phone",   "Tech",   15000, "GDL",  LocalDate.of(2026, 1, 20)),
	            new Sale("Desk",    "Office",  8000, "CDMX", LocalDate.of(2026, 2, 1)),
	            new Sale("Chair",   "Office",  5000, "MTY",  LocalDate.of(2026, 2, 10)),
	            new Sale("Monitor", "Tech",   12000, "CDMX", LocalDate.of(2026, 2, 15)),
	            new Sale("Tablet",  "Tech",   10000, "GDL",  LocalDate.of(2026, 1, 25)),
	            new Sale("Lamp",    "Office",  2000, "MTY",  LocalDate.of(2026, 1, 30)));
		

		// 4. Ingresos por region (mayor a menor)
        System.out.println("\n=== Ingresos por Region ===");
        // TODO: groupingBy region, summingDouble, ordenar por valor descendente
//        sales.stream()
//            .collect(groupingBy(___, summingDouble(___)))
//            .entrySet().stream()
//            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
//            .forEach(e -> System.out.printf("  %s: $%,.2f%n", e.getKey(), e.getValue()));
        
        Stream<Sale> streamSale = sales.stream();
        //EXPLICACION LINEA .collect(groupingBy(___, summingDouble(___)))
        ToDoubleFunction<Sale> tdf = sale -> sale.amount();
        //Collectors.summingDouble(tdf);		
        Function<Sale,String> function = sale -> sale.region();
        //Collectors.groupingBy(function,Collectors.summingDouble(tdf));
        Map<String,Double> map = streamSale
        		.collect(Collectors.groupingBy(function,Collectors.summingDouble(tdf)));
        map.forEach((k,v) -> System.out.println("Region: "+k+", Total:"+v));
        
        //.entrySet().stream()
        
        Set<Map.Entry<String,Double>> entrySet = map.entrySet();
        Stream<Map.Entry<String,Double>> stream2 = entrySet.stream();
        		
        //List<String> lista = List.of("a","b","c");
        //Stream<String> stream = lista.stream();
        
	}

}
