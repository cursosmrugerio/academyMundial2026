package com.curso.v0;

class Vehicle { }

class Car extends Vehicle { }

public class Test {
    public static void main(String[] args) {

        Vehicle v1 = new Car();

        // A
        Car c1 = (Car) v1; //COMPILE

        // B
        Vehicle v2 = new Vehicle();
        Car c2 = (Car) v2; //ClassCastException
        
        //Car c9 = new Vehicle();

        // C
        Vehicle v3 = new Car();
        //Car c3 = v3; //DON'T COMPILE: Car c3 = (Car) v3;

        // D
        Vehicle v4 = new Car();
        Car c4 = (Car) v4; //COMPILE
    }
}