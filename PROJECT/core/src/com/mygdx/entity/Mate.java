package com.mygdx.entity;

public class Mate extends Player implements Runnable{

    int timeout = 99999999;
    @Override
    public void run() {
        int random  = (int) (Math.random()*100);
        System.out.println("debut tache " + Thread.currentThread().getName());
        for(int i = 0  ; i < 50; i++){
            System.out.println("Hello  thread numéro random -> " + random );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        System.out.println("fin tache");
    }
}