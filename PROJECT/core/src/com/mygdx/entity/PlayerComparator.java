package com.mygdx.entity;


import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getY() > p2.getY()) {
//            System.out.println("Je suis le comparateur et je suis sensé avoir : " + p1.getY() + " > " + p2.getY());
            return 1;
        }
        if (p1.getY() == p2.getY()) {
//            System.out.println("Je suis le comparateur et je suis sensé avoir : " + p1.getY() + " = " + p2.getY());
            return 0;
        }
//        System.out.println("Je n'existe pas : " + p1.getY() + " < " + p2.getY());
        return -1;
    }
}
