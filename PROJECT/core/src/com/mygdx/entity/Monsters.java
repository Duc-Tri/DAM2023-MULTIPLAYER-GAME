package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.bagarre.MainGame;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.AStarMap;
import com.mygdx.pathfinding.Vector2int;

import java.util.ArrayList;

//=================================================================================================
// Gestion des monstres (appliquée à une carte et donc un AStar
//=================================================================================================
public class Monsters {

    public static TextureAtlas allMonstersAtlas;

    public enum MonstersMode {
        SOLO_MODE, // MODE SOLO, display ET simulation
        SLAVE_MODE, // MULTIJOUEUR, display seulement, CLIENTS ET MASTER,
        MASTER_MODE // MULTIJOUEUR, simulation seulement, MASTER
    }

    // SOLO: simulationMobs et drawMobs pointent sur les mêmes données
    // MULTIJOUEUR et MASTER : simulationMobs pour la simulation, drawMobs pour l'affichage
    // MULTIJOUEUR et SLAVE : drawMobs pour l'affichage, simulationMobs est nul !
    private static ArrayList<Mob> drawMobs; // update render
    private static ArrayList<Mob> simulationMobs; // update data
    private static Map map;
    private static AStarMap aStarMap;
    private static final int MAX_RANDOM_MONSTERS = 10;
    private static Player targetPlayer;
    private MonstersMode monstersMode;

    public Monsters() {

        // TODO : spawn monsters from map TRIGGERS
        allMonstersAtlas = new TextureAtlas(Gdx.files.internal(MainGame.MONSTERS_ATLAS));

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

    public Monsters(Map m, Player p) {
        this();

        targetPlayer = p;
        aStarMap = new AStarMap(m);
        setMap(m);

        if (MainGame.getInstance().isSoloGameMode()) {
            // MODE SOLO
            monstersMode = MonstersMode.SOLO_MODE;
        } else {
            // MODE MULTIJOUEUR
            monstersMode = (targetPlayer.isMaster() ? Monsters.MonstersMode.MASTER_MODE
                    : Monsters.MonstersMode.SLAVE_MODE);
        }

        switch (monstersMode) {
            case SOLO_MODE:
                drawMobs = simulationMobs = new ArrayList<>(); // IMPORTANT ! pointent sur les même data
                spawnMonsters(map.getMonstersToSpawn());
                setTargetPlayer(p);
                break;
            case SLAVE_MODE:
                drawMobs = new ArrayList<>();
                simulationMobs = null;
                break;
            case MASTER_MODE:
                drawMobs = new ArrayList<>();
                simulationMobs = new ArrayList<>();
                spawnMonsters(map.getMonstersToSpawn());
                setTargetPlayer(p);
                break;
        }
    }

    public static Mob getMob(int i) {
        return drawMobs.get(i);
    }

    private String[] ids() {
        String[] uids = new String[drawMobs.size()];
        for (int i = 0; i < drawMobs.size(); i++) {
            uids[i] = drawMobs.get(i).getServerUniqueID();
        }
        return uids;
    }

    public void moveRandomly(float deltaTime) {
        for (Mob m : simulationMobs) {
            m.moveToRandomDir(deltaTime);
        }
    }

    public void moveRandomly() {
        for (Mob m : simulationMobs) m.moveToRandomDir();
    }

    public void moveToPlayer() {
        for (Mob m : simulationMobs) m.moveToPlayer();
    }

    public void moveToPlayer(float deltaTime) {
        for (Mob m : simulationMobs) m.moveToPlayer(deltaTime);
    }

    public static void setMap(Map m) {
        map = m;

        if (Mob.getMap() != map) Mob.setMap(map); // carte globale à tous les monstres

        if (aStarMap.getMap() != map) aStarMap.setMap(map);
    }

    public void setTargetPlayer(Player player) {
        targetPlayer = player;
        for (Mob m : simulationMobs) {
            m.setTargetPlayer(player);
            // System.out.println("setTargetPlayer ======================== " + player.uniqueID);
        }
    }

    // Spawn les monstres en nombre demandé au point de Spawn demandé
    //=============================================================================================
    private void spawnMonsters(String monstersToSpawn) {

        simulationMobs.clear();

        String[] monsters = monstersToSpawn.split("\n");
        for (String line : monsters) {

            if (line.contains(";")) {

                // exemple de texte reçu depuis le TiledMap
                //-------------------------------------------------
                // OCTOPUS      ;1 ;spawn_01
                // BLOB         ;3 ;spawn_02
                // LIVING_TREE  ;0 ;spawn_03

                String[] mob_num_spawn = line.split(";"); // TYPE_MONSTRE; NOMBRE; SPAWN_PT

                // -------------------------- le type de monstre à spawn
                Mob.MonsterType type = null;
                // -------------------------- le nombre
                int num = -1;
                // -------------------------- la zone de spawn sur la carte
                String spawnArea = "";

                try {
                    type = Mob.MonsterType.valueOf(mob_num_spawn[0].trim());
                    num = Integer.parseInt(mob_num_spawn[1].trim());
                    spawnArea = mob_num_spawn[2].trim().toUpperCase();
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
                        simulationMobs.add(m);
                    }

                }
            }
        }

    }

    public MonstersMode getMonstersMode() {
        return monstersMode;
    }

    public void reset() {
        simulationMobs.clear();
    }

    public static ArrayList<Mob> getSimulationMobs() {
        return simulationMobs;
    }

    public static ArrayList<Mob> getDrawMobs() {
        return drawMobs;
    }

    public void update(float deltaTime) {
        //moveToPlayer(deltaTime);
        switch (monstersMode) {
            case SOLO_MODE:
                moveToPlayer(deltaTime);
                break;
            case SLAVE_MODE:
                /// retrievemonsters
                break;
            case MASTER_MODE:
                moveToPlayer(deltaTime);
                break;
        }
    }

    public void removeOldMobs(String[] tempMobs) {

    }

    public void removeAllMobs(String[] tempMobs) {
//        simulationMobs.clear();
//        drawMobs.clear();
    }

    public static void createNewMobs(String[] tempMobs) {

        // FLOOD !!!
        //System.out.println("createNewMobs >>>>> tempMobs{" + tempMobs.length + "}=" + String.join("_@_", tempMobs)

        if (tempMobs != null && tempMobs.length != 0) {

            for (String strMob : tempMobs) {

                boolean found = false;

                if (strMob != null && !strMob.isEmpty()) {
                    String[] oneMob = strMob.split(";");
                    String oneMobId = oneMob[0];
                    int life = Integer.parseInt(oneMob[4]);

                    for (int i = drawMobs.size() - 1; i >= 0; i--) {
                        Mob m = drawMobs.get(i);
                        if (oneMobId.equalsIgnoreCase(m.uniqueID)) {
                            found = true;

                            if (life > 0) {
                                m.setFootX(Float.parseFloat(oneMob[1]));
                                m.setY(Float.parseFloat(oneMob[2]));
                                m.setFindRegion(oneMob[3]);
                                m.setCurrentLife(life);
                            } else
                                drawMobs.remove(m);

                            // System.out.println("createNewMobs >>>>> FOUND " + oneMobId);

                            break;
                        }
                    }

                    if (!found && life > 0) {

                        String n = oneMob[3].split("_")[0];

                        // System.out.println("createNewMobs >>>>> NOT FOUND " + n + " / " + oneMobId);

                        Mob newMob = new Mob(Mob.MonsterType.values()[Integer.parseInt(n)]);
                        newMob.initializeSprite();
                        newMob.setUniqueID(oneMobId);
                        newMob.setFootX(Float.parseFloat(oneMob[1]));
                        newMob.setY(Float.parseFloat(oneMob[2]));
                        newMob.setFindRegion(oneMob[3]);
                        newMob.setCurrentLife(Integer.parseInt(oneMob[4]));

                        drawMobs.add(newMob);
                    }

                    // System.out.println("createNewMobs::oneMob=" + strMob);
                }
            }
        }

    }

    public static void killRandom() {
        // juste pour tests........................................................................
        if (simulationMobs.size() < 1)
            return;

        int i = (int) (Math.random() * simulationMobs.size());
        simulationMobs.get(i).setCurrentLife(0);
        //System.out.println("killRandom :::::::::::::::::::::: " + i);
        simulationMobs.remove(i);
    }

//    public void drawAndUpdate(SpriteBatch batch) {
//        if (drawMobs.size() != 0)
//            for (Mob m : drawMobs) {
//                if (m != null) {
//                    m.drawAndUpdate(batch);
//                }
//            }
//    }

}
