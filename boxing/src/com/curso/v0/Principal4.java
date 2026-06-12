package com.curso.v0;

import java.math.BigDecimal;

public class Principal4 {
	
	public static void main(String[] args) {
		
		float f1 = 0.3f;
		float f2 = 0.2f;
		
		float f3 = f1 - f2;
		
		System.out.println("float: "+ f3);
		
		double d1 = 0.3;
		double d2 = 0.2;
		
		double d3 = d1 - d2;
		
		System.out.println("double: "+d3);
		
		BigDecimal bd1 = new BigDecimal(0.3);
		BigDecimal bd2 = new BigDecimal(0.2);
		BigDecimal bd3 = bd1.subtract(bd2);
		
		System.out.println(bd3);
		
		bd1 = BigDecimal.valueOf(0.3);
		bd2 = BigDecimal.valueOf(0.2);
		
		bd3 = bd1.subtract(bd2);
		System.out.println(bd3);
		
		bd1 = new BigDecimal("0.3");
		bd2 = new BigDecimal("0.2");
		bd3 = bd1.subtract(bd2);
		
		System.out.println(bd3);
		
	}

}
