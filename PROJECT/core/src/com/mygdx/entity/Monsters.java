package com.mygdx.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.AStarMap;
import com.mygdx.pathfinding.Vector2int;

import java.util.ArrayList;

//=================================================================================================
// Gestion des monstres (appliquée à une carte et donc un AStar
//=================================================================================================
public class Monsters {
    private static ArrayList<Mob> mobs = new ArrayList<>();
    private static Map map;
    private static AStarMap aStarMap;
    private static final int MAX_RANDOM_MONSTERS = 10;
    private static Player targetPlayer;

    public Monsters() {

        // TODO : spawn monsters from map TRIGGERS

        /*
        for (int n = 0; n < MAX_RANDOM_MONSTERS; n++) {

            // TEMP : populate random monsters

            Mob m = new Mob();
            m.setX((float) (125 + Math.random() * 50 - 25));
            m.setY((float) (125 + Math.random() * 50 - 25));
            mobs.add(m);
        }
        */
    }

    public Monsters(Map m) {
        this();
        aStarMap = new AStarMap(m);
        setMap(m);
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
                        Mob newMob = new Mob(Mob.MonsterType.LIVING_TREE);
                        mobs.add(newMob);
                        newMob.setServerUniqueID(oneMob);
                        ///RetrievePlayer.requestServer(newMob); // TODO: write servlet for mobs
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
        for (Mob m : mobs) m.moveToRandomDir();
    }

    public void moveToPlayer() {
        for (Mob m : mobs) m.moveToPlayer();
    }

    public void moveToPlayer(float deltaTime) {
        for (Mob m : mobs) m.moveToPlayer(deltaTime);
    }

    public static void setMap(Map m) {
        map = m;

        if (Mob.getMap() != map) Mob.setMap(map); // carte globale à tous les monstres

        if (aStarMap.getMap() != map) aStarMap.setMap(map);
    }

    public void setTargetPlayer(Player player) {
        targetPlayer = player;
        for (Mob m : mobs) {
            m.setTargetPlayer(player);
            // System.out.println("setTargetPlayer ======================== " + player.uniqueID);
        }
    }

    // Spawn les monstres en nombre demandé au point de Spawn demandé
    //=============================================================================================
    public void spawnMonsters(String monstersToSpawn) {

        mobs.clear();

        String[] monsters = monstersToSpawn.split("\n");
        for (String line : monsters) {

            if (line.contains("=") && line.contains("@")) {

                // exemple de texte reçu depuis le TiledMap
                //-------------------------------------------------
                // OCTOPUS      =  1@spawn_01
                // BLOB         =3     @spawn_01
                // LIVING_TREE  =0   @spawn_01

                String[] mob_numspawn = line.split("="); // TYPE_MONSTRE   =NOMBRE   @SPAWN_PT
                String[] num_spawn = mob_numspawn[1].split("@");

                Mob.MonsterType type = null; // le type de monstre
                int num = -1; // le nombre à spawn
                String spawnArea = ""; // le point de spawn sur la carte (rectangle)

                try {
                    type = Mob.MonsterType.valueOf(mob_numspawn[0].trim());
                    num = Integer.parseInt(num_spawn[0].trim());
                    spawnArea = num_spawn[1].trim().toUpperCase();
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                // une fois qu'on a les 3 infos, tentative de spawn !
                //-------------------------------------------------------
                if (type != null && num > 0 && !spawnArea.isEmpty()) {

                    System.out.println("SPAWN ++++++++++ " +
                            type.toString() + " === " + num + " @@@ " + spawnArea);

                    for (int i = 0; i < num; i++) {
                        Mob m = new Mob(type);
                        Vector2int pos = map.centerPointAtSpawnArea(spawnArea);
                        m.setX(pos.x);
                        m.setY(pos.y);
                        m.setTargetPlayer(null);
                        mobs.add(m);
                    }

                }
            }
        }

    }

}

