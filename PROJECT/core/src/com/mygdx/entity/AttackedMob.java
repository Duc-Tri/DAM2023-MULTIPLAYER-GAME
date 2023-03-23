package com.mygdx.entity;

public class AttackedMob {
    public String mobID;
    public int damageToApply;

    public AttackedMob(String id, int dam) {
        mobID = id;
        damageToApply = dam;
    }

    public String buildHttpParam() {
        return mobID + ";" + damageToApply;
    }

}
