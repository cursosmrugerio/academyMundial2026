package com.curso.v1;

public class InitTest{
	   public InitTest(){
	      s1 = sM1("1");
	   }
	   static String s1 = sM1("a"); //s1=1
	   String s3 = sM1("2"); //s3=2
	   { 
	      s1 = sM1("3");
	      s2 = sM1("888");
	      s4 = sM1("777");
	   }
	   static{
	      s1 = sM1("b");
	      //s4 = sM1("4"); //Error: Es de Instacia
	      s2 = sM1("999");
	      
	   }
	   static String s2 = sM1("c"); //s2=c
	   String s4 = sM1("4"); //s4 = 4
	    public static void main(String args[]){
	        InitTest it = new InitTest();
	    }
	    private static String sM1(String s){
	       System.out.println(s);  return s;
	    }
	}

//abc2341

