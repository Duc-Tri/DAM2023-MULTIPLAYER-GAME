package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.AStarMap;

import java.util.ArrayList;

public class Monsters {
    private static ArrayList<Mob> mobs = new ArrayList<>();
    private static Map map;
    private static AStarMap aStarMap;

    public Monsters() {

        // TEMP, populate monsters
        for (int n = 0; n < 20; n++) {

            Mob m = new Mob();
            m.setX((float) (125 + Math.random() * 50 - 25));
            m.setY((float) (125 + Math.random() * 50 - 25));
            mobs.add(m);
        }
    }

    public Monsters(Map m) {

        this();
        setMap(m);
        aStarMap = new AStarMap(m);
    }

    public static Mob getMob(int i) {
        return mobs.get(i);
    }

    public void drawAndUpdate(SpriteBatch batch) {
        for (Mob m : mobs) {
            if (m != null) {
                m.drawAndUpdate(batch);
            }
        }
    }

    private String[] ids() {
        String[] uids = new String[mobs.size()];
        for (int i = 0; i < mobs.size(); i++) {
            uids[i] = mobs.get(i).getServerUniqueID();
        }
        return uids;
    }

    public static ArrayList<Mob> getMobs() {
        return mobs;
    }

    public static void createNewMobs(String[] tempMobs) {

        // FLOOD !!!
//        System.out.println("createNewMobs >>>>> tempMobs{" + tempMobs.length + "}=" + String.join("_@_", tempMobs) + " >>>>> mobs{" + mobs.size() + "}=" + String.join("_@_", ids()));

        if (tempMobs != null && tempMobs.length != 0) {

            for (String oneMob : tempMobs) {

                boolean found = false;

                if (oneMob != null && !oneMob.isEmpty()) {
                    for (Mob m : mobs) {
                        if (oneMob.equalsIgnoreCase(m.getServerUniqueID())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Mob newMob = new Mob();
                        mobs.add(newMob);
                        newMob.setServerUniqueID(oneMob);
                        ///RetrievePlayer.requestServer(newMob); // TODO: servlet for mobs
                        newMob.initializeSprite();
                    }
                }
            }
        }

    }

    public void moveRandomly(float deltaTime) {

        for (Mob m : mobs) {
            m.moveToRandomDir(deltaTime);
        }
    }

    public void moveRandomly() {

        for (Mob m : mobs) {
            m.moveToRandomDir();
        }
    }

    public static void setMap(Map m) {
        map = m;

        if (Mob.getMap() != map)
            Mob.setMap(map);
    }

}

