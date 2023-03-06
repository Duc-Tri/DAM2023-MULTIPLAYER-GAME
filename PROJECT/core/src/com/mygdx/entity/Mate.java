package com.mygdx.entity;

import com.mygdx.bagarre.Game;

public class Mate extends Player implements Runnable{


    boolean started;
    int timeout = 99999999;

    public Mate(Game game) {
        super(game);
    }
    @Override
    public void run() {
        int random  = (int) (Math.random()*100);
        System.out.println("debut tache " + Thread.currentThread().getName());
        for(int i = 0  ; i < 50; i++){
            System.out.println("Hello  thread numéro random -> " + random );
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}