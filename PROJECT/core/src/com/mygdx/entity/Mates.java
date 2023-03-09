package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.client.RetrieveMate;
import com.mygdx.client.RetrievePlayer;
import com.mygdx.client.RetrieveUpdatePlayer;

import java.util.ArrayList;

public class Mates {
    private static ArrayList<Mate> mates = new ArrayList<>();
    private static Player player;

    public Mates(Player pl) {
        if (player == null)
            player = pl;
    }

    public static Mate getMate(int i) {
        return mates.get(i);
    }

    public void drawAndUpdate(SpriteBatch batch) {
        //createNewMates(RetrieveMate.requestServer(player));
        for (Mate m : mates) {
//            m.setXFromRealX();
//            m.setYFromRealY();
            if (m != null) {
                //RetrieveUpdatePlayer.requestServer(m);
                m.drawAndUpdate(batch);
            }
        }
    }

    private String[] ids() {
        String[] uids = new String[mates.size()];

        for (int i = 0; i < mates.size(); i++) {
            uids[i] = mates.get(i).getServerUniqueID();
        }

        return uids;
    }

    public static ArrayList<Mate> getMates() {
        return mates;
    }

    public static void createNewMates(String[] tempMates) {

        // FLOOD !!!
//        System.out.println("createNewMates >>>>> tempMates{" + tempMates.length + "}=" + String.join("_@_", tempMates) + " >>>>> mates{" + mates.size() + "}=" + String.join("_@_", ids()));

        if (tempMates != null && tempMates.length != 0) {

            for (String oneMate : tempMates) {

                boolean found = false;

                if (oneMate != null && !oneMate.isEmpty()) {
                    for (Mate m : mates) {
                        if (oneMate.equalsIgnoreCase(m.getServerUniqueID())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Mate newMate = new Mate();
                        mates.add(newMate);
                        newMate.setServerUniqueID(oneMate);
                        RetrievePlayer.requestServer(newMate);
                        newMate.initializeSprite();
                    }
                }
            }
        }

    }


}
