package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.client.RetrieveMate;
import com.mygdx.client.RetrievePlayer;
import com.mygdx.client.RetrieveUpdatePlayer;

import java.util.ArrayList;

public class Mates {
    private ArrayList<Mate> mates = new ArrayList<>();
    private static Player player;

    public Mates(Player pl) {
        if (player == null)
            player = pl;
    }

    String[] tempMates;

    public void drawAndUpdate(SpriteBatch batch) {
        tempMates = RetrieveMate.requestServer(player);
        createMates(tempMates);
        for (Mate m : mates) {
            if (m != null) {
                RetrieveUpdatePlayer.requestServer(m);
                m.drawAndUpdate(batch);
            }
        }
    }

    private void createMates(String[] tempMates) {
        //                    mates[i] = new Mate(this);
        //                    mates[i].setServerUniqueID(tempMates[i]);
        //                    RetrievePlayer.requestServer(mates[i]);
        if (tempMates != null) {

            for (int i0 = 0; i0 < tempMates.length; i0++) {

                boolean found = false;

                for (int i1 = 0; i1 < mates.size(); i1++) {
                    if (tempMates[i0] != null && tempMates[i0].equalsIgnoreCase(mates.get(i1).getServerUniqueID())) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    mates.add(new Mate());
                    mates.get(mates.size() - 1).setServerUniqueID(tempMates[i0]);
                    RetrievePlayer.requestServer(mates.get(mates.size() - 1));
                    mates.get(mates.size() - 1).initializeSprite();
                }
            }
        }

    }


}
