package com.sim.v0;

public class TestClass {
    
    public static void doStuff() throws Exception{
        System.out.println("Doing stuff...");
        if(Math.random()>0.4){ // <= 0.4
            throw new Exception("Too high!");
        }
        System.out.println("Done stuff.");
    }
    
    public static void main(String[] args) throws Exception {
        doStuff();
        System.out.println("Over");	
    }
}

// <=0.4
//Doing stuff...
//Done stuff
//Over

// >0.4
//Doing stuff...
//DESBORDA