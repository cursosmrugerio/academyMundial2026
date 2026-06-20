package com.book.v0;

public class Zoo {

    public static void pause() {
        try {
            Thread.sleep(10_000); //10 segundos
        } catch (InterruptedException e) {
        	
        }

        System.out.println("Thread finished!");
    }

    public static void main(String[] unused) {
        Thread job = new Thread(() -> pause());

        job.start();

        System.out.println("Main method finished!");
    }
}