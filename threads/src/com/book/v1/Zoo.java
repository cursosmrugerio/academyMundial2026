package com.book.v1;

public class Zoo {

    public static void pause() {
        try {
        	System.out.println("Thread durmiendo 10 segundos");
            Thread.sleep(10_000); //10 segundos
        } catch (InterruptedException e) {
        	System.out.println("Me despertaron a los 5 segundos");
        }

        System.out.println("Thread finished!");
    }

    public static void main(String[] unused) throws InterruptedException {
        Thread job = new Thread(() -> pause());

        job.start();
        
        Thread.sleep(5_000);
        job.interrupt();
        
        System.out.println("Main method finished!");
    }
}