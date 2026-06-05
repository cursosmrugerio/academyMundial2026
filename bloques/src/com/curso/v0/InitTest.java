package com.curso.v0;

public class InitTest{
	   static String s1 = sM1("a");{  //s1=b
	      s1 = sM1("b");
	   }
	   static{
	      s1 = sM1("c");
	   }
	   public static void main(String args[]){
	      InitTest it = new InitTest();
	   }
	   private static String sM1(String s){
	      System.out.println(s);  return s;
	   }
	}

//acb
