package com.mygdx.entity;


import java.util.Comparator;

public class LivingEntityComparator implements Comparator<LivingEntity> {

    @Override
    public int compare(LivingEntity le1, LivingEntity le2) {
        if (le1.getY() > le2.getY()) {
            return 1;
        }
        if (le1.getY() == le2.getY()) {
            return 0;
        }
        return -1;
    }
}
