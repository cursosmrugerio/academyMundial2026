package com.sim.v1;

public class TestClass {
	
	public static void main(String[] args) throws Exception  {
        TestClass tc = new TestClass();
        tc.myMethod();
    }
	
	
	public void myMethod() throws Exception {
       
		yourMethod();
		
    }
	
	
	public void yourMethod() throws Exception  {
		throw new Exception();
    }  

}

