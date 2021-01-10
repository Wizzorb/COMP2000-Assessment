package com.company;

public class runnableMain implements Runnable{
    @Override
    public void run() {
        int count = 0;
        System.out.println("This code is in thread " + Thread.currentThread().getId() + " " + count);
        count++;
    }
}
