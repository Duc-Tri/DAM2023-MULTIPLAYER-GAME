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

    public static void removeOldMates(String[] tempMates) {
        for (int i = mates.size() - 1; i > -1; i--) {
            boolean found = false;
            if (tempMates != null) {
                for (String oneMate : tempMates) {
//                    System.out.println("Début");
//                    System.out.println("oneMate   " + oneMate);
//                    System.out.println("mates.get(i).getServerUniqueID()   " + mates.get(i).getServerUniqueID());
//                    System.out.println("Fin");
                    if (oneMate != null && !oneMate.isEmpty()) {
                        if (oneMate.equalsIgnoreCase(mates.get(i).getServerUniqueID())) {
                            if (oneMate.equalsIgnoreCase(mates.get(i).getServerUniqueID())) {
                                found = true;
                                break;
                            }
                        }
                    }
                }

            }
            if (!found) {
//                System.out.println("Remove   m  " + mates.get(i).getServerUniqueID());
                mates.remove(i);
            }
        }
//        System.out.println("mates.size() "  + mates.size());

    }

    public static void removeAllMates() {
        mates.clear();
    }

    public void drawAndUpdate(SpriteBatch batch) {
        for (Mate m : mates) {
            if (m != null) {
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
                        System.out.println("newMate " + newMate.getServerUniqueID());
                        newMate.setNumLobby(player.getNumLobby());
                        RetrievePlayer.requestServer(newMate);
                        newMate.initializeSprite();
                    }
                }
            }
        }

    }


}
