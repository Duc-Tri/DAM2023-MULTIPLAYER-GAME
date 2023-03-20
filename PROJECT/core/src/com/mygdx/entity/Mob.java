package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.graphics.LifeBar;
import com.mygdx.graphics.RMXPAtlasGenerator;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.AStarMap;
import com.mygdx.pathfinding.Vector2int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//#################################################################################################
// Monster
//=================================================================================================
//
//#################################################################################################
public class Mob extends LivingEntity {
    private final static String MONSTERS_ATLAS = "characters/RMXP_monsters.atlas";
    private static TextureAtlas allMonstersAtlas; // le même atlas pour tous les monstres

    //public static final boolean DEBUG_MOB = false;
    private static Map map; // la même carte pour tous les monstres

    private static AStarMap aStarMap; // le pathfinding, le même pour tous
    private Player targetPlayer; // chaque monstre peut poursuivre un joueur différent !
    private Vector2int oldTargetPos; // ancienne position du joueur poursuivi
    public String mobID;
    private MonsterType monsterType;
    private TextureRegion textureRegion;
    public Color spriteTint;
    private static int numMob = 0;

    private int WAIT_FRAMES = 0; // OBSOLETE: pour régler la vitesse de moveToPlayer

    public static enum MonsterType {IMP, BAT, DEVIL, SCORPION, OCTOPUS, BLOB, TROLL, LIVING_TREE}

    // NUMÉRO DES MONSTRES SELON L'ORDRE DANS LE FICHIER ATLAS ====================================
    final static HashMap<MonsterType, Integer> MonstersNum = new HashMap<MonsterType, Integer>() {{
        put(MonsterType.IMP, 0);
        put(MonsterType.BAT, 1);
        put(MonsterType.DEVIL, 2);
        put(MonsterType.SCORPION, 3);
        put(MonsterType.OCTOPUS, 4);
        put(MonsterType.BLOB, 5);
        put(MonsterType.TROLL, 6);
        put(MonsterType.LIVING_TREE, 7);
    }};

    // POINTS DE VIE DES MONSTRES =================================================================
    final static HashMap<MonsterType, Integer> MonstersHealth = new HashMap<MonsterType, Integer>() {{
        put(MonsterType.IMP, 50);
        put(MonsterType.BAT, 10);
        put(MonsterType.DEVIL, 20);
        put(MonsterType.SCORPION, 30);
        put(MonsterType.OCTOPUS, 80);
        put(MonsterType.BLOB, 40);
        put(MonsterType.TROLL, 90);
        put(MonsterType.LIVING_TREE, 100);
    }};

    public Mob(MonsterType type) {
        // TEXTURE DE TOUS LES MOBS ---------------------------------------------------------------
        if (allMonstersAtlas == null) {
            allMonstersAtlas = new TextureAtlas(MONSTERS_ATLAS);
        }

        //System.out.println((numMob++) + " / " + type + " =============== CONSTRUCTOR Mob ... " + allMonstersAtlas);

        uniqueID = "mob" + nextUniqueId();

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        // TODO : remove randomness
        // (int) (Math.random() * RMXPMonstersAtlas.MAX_MONSTERS)
        monsterType = type;
        RMXP_CHARACTER = MonstersNum.get(monsterType) + "_";
        findRegion = RMXP_CHARACTER + "DOWN_0";
        maxLife = MonstersHealth.get(monsterType);
        ///////////////////////////////////////////////////////////////
        currentLife = maxLife;
        currentLife = 1 + (int) (Math.random() * (maxLife - 1));
        lifeBar.setBarRatio(maxLife);

        initializeSprite();

        // System.out.println("---------------------------- CONSTRUCTOR Mob " + uniqueID);

        // to position everything well !!!
        setFootX(getFootX());
        setY(getY());

        randomize(); // TODO : remove it after tests
        nextPoint = new Vector2int(999, 999);
        oldTargetPos = new Vector2int(999, 999);
        //sprite.setColor(spriteTint);
    }

    public Mob(float x, float y, float width, float height) {
        entityX = x;
        entityY = y;
        hitbox = new Rectangle(x, y, width, height);
    }

    public Mob(Rectangle rect) {
        this.hitbox = rect;
        entityX = rect.getX();
        entityY = rect.getY();
        hitbox = rect;
    }

    @Override
    public void initializeSprite() {

        // texture statique mob => texture locale de living_entity --------------------------------
        if (textureAtlas == null) {
            textureAtlas = allMonstersAtlas;
        }

        textureRegion = textureAtlas.findRegion(findRegion);
        sprite = new Sprite(textureRegion);

        HITBOX_WIDTH = (int) (sprite.getWidth() - 16); // temp
        HITBOX_XOFFSET = (int) ((sprite.getWidth() - HITBOX_WIDTH) / 2); // X :au mileu
        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        //System.out.println("************** END initializeSprite " + findRegion + " / " + sprite);
    }

    public String getMobID() {
        return mobID;
    }

    // TEST DATA ==================================================================================
    private String randomDir;
    private int spriteStep = 4;
    private int totalSteps; // pour timer le mouvement
    private int maximumSteps; // pour timer le mouvement
    private float currentTime; // pour timer le mouvement, maj ENTRE CHAQUE APPEL
    private float moveDelay; // pour timer le mouvement

    private void randomize() {
        WAIT_FRAMES = 2 + (int) (Math.random() * 2);

        randomDir = RMXPAtlasGenerator.randomDir();

        // pour la version movetoPLayer / moveToRandomDir(float deltaTime) ------------------------
        moveDelay = 0.01f + (float) (Math.random() * 0.1f);
        currentTime = 0;

        // pour la version movetoPLayer() / moveToRandomDir() -------------------------------------
        totalSteps = 0;
        maximumSteps = (int) (Math.random() * 200);
        spriteStep = 2 + (int) (Math.random() * 6);
    }

    // move to random direction, until time reached
    public void moveToRandomDir(float deltaTime) {
        currentTime += deltaTime;
        if (currentTime > moveDelay) {
            randomize();
        }

        moveMob(randomDir, true);
    }

    public void moveToRandomDir() {
        totalSteps++;
        if (totalSteps > maximumSteps) {
            randomize();
        }

        moveMob(randomDir, true);
    }

    private void moveMob(String dirKeyword, boolean collideWithObstacle) {

        int deltaX = 0;
        int deltaY = 0;

        switch (dirKeyword) {
            case "LEFT":
                deltaX = -spriteStep;
                break;
            case "RIGHT":
                deltaX = spriteStep;
                break;
            case "UP":
                deltaY = spriteStep;
                break;
            case "DOWN":
                deltaY = -spriteStep;
                break;
        }

        animate(dirKeyword);

        //System.out.println(this.uniqueID + " d=" + dirKeyword + " t=" + currentTime + "/" + moveDelay);

        if (collideWithObstacle && map.checkObstacle(this, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        if (deltaX != 0) {
            setFootX(getFootX() + deltaX);
        }

        if (deltaY != 0) {
            setY(getY() + deltaY);
        }
    }

    public static void setMap(Map m) {
        map = m;

        if (aStarMap == null)
            aStarMap = new AStarMap(map);
        else
            aStarMap.setMap(map);
    }

    public static Map getMap() {
        return map;
    }

    private Vector2int aStarCurrentPos = new Vector2int(0, 0);
    private Vector2int aStarGoal = new Vector2int(0, 0);
    private List<Vector2int> mapPathToTarget;
    private Vector2int nextPoint = new Vector2int(-1, -1);

    public List<Vector2int> pathToTarget(LivingEntity target) {

        //System.out.println("pathToTarget " + target.uniqueID);

        if (target == null) return null;

        aStarCurrentPos = map.pixelsToMapTile(getFootX(), entityY);
        aStarGoal = map.pixelsToMapTile(target.getFootX(), target.getY());

        List<Vector2int> path = aStarMap.findPath(aStarCurrentPos, aStarGoal);

        if (path == null) {
            // pour avoir une liste non null et y ajouter la position de la target
            path = new ArrayList<>();
        } else {
            // on enlève le 1er point, pour mettre un meilleur :) =================================
            path.remove(0);

            // évite un tremblement sur place quand le joueur a bougé !!! =========================
            if (path.size() != 0)
                path.remove(path.size() - 1);
        }
        path.add(0, new Vector2int(target.getFootX(), target.getY()));

//        System.out.println("pathToTarget ################################################ " +
//                (path == null ? "PATH NULL" : Arrays.toString(path.toArray())));

        return path;
    }

    private void processPathToPlayer(boolean forceNewPath) {
        if (forceNewPath || mapPathToTarget == null || mapPathToTarget.size() == 0) {

            mapPathToTarget = pathToTarget(targetPlayer);
            //System.out.println("processPathToPlayer --- NEW PATH --- " + mapPathToTarget.size());
        }

        if (mapPathToTarget != null) {

            nextPoint = mapPathToTarget.get(mapPathToTarget.size() - 1);
            mapPathToTarget.remove(mapPathToTarget.size() - 1);
            //System.out.println("processPathToPlayer -- same path -- " + mapPathToTarget.size());
        }
    }

    int waitBeforeMove = WAIT_FRAMES + 1;

    public void moveToPlayer() {
        if (playerReached() || (waitBeforeMove++ < WAIT_FRAMES))
            return;
        else
            waitBeforeMove = 0;

        float deltaX = nextPoint.x - getFootX();
        float deltaY = nextPoint.y - getY();

        // on a atteint le point courant => point suivant
        // ou recalcul du pathfinding si aucun point restant
        //---------------------------------------------------------------------
        boolean moved = playerHasMoved();
        if (moved || (Math.abs(deltaX) < spriteStep && Math.abs(deltaY) < spriteStep)) {

            // System.out.println("moveToPlayer :::::: " + moved +
            //         " dx=" + Math.abs(deltaX) + " dy=" + Math.abs(deltaY) + " step=" + spriteStep);

            processPathToPlayer(moved);
        }

        // Calcul de la direction, et mouvement effectif
        //---------------------------------------------------------------------
        String dir;
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // déplacement horizontal
            dir = (deltaX > 0 ? "RIGHT" : "LEFT");
        } else {
            // déplacement vertical
            dir = (deltaY > 0 ? "UP" : "DOWN");
        }
        moveMob(dir, false);

        // TEST : téléportation --------------------
        //        setX(nextPoint.x);        //        setY(nextPoint.y);

        //        System.out.println(mapPathToTarget.size() + ") moveToPlayer ............ " +
        //                " entity={" + entityX + "/" + entityY + "} " +
        //                " nextP={" + nextPoint.x + "/" + nextPoint.y + "}");
    }

    public void moveToPlayer2(float deltaTime) {
    }

    public void moveToPlayer(float deltaTime) {

//        System.out.println(" moveToPlayer ............ mP2t=" + mapPathToTarget + " / m=" + map +
//                " / pr=" + playerReached() + " / Ct=" + currentTime + " / pl=" + targetPlayer.uniqueID);

        if (mapPathToTarget == null || map == null || playerReached() || ((currentTime += deltaTime) < moveDelay))
            return;

        currentTime = moveDelay - currentTime;

        float deltaX = nextPoint.x - getFootX();
        float deltaY = nextPoint.y - getY();

        // on a atteint le point courant => point suivant
        // ou recalcul du pathfinding si aucun point restant
        //---------------------------------------------------------------------
        boolean moved = playerHasMoved();
        if (moved || (Math.abs(deltaX) < spriteStep && Math.abs(deltaY) < spriteStep)) {

//            System.out.println("moveToPlayer :::::: " + moved +
//                    " dx=" + Math.abs(deltaX) + " dy=" + Math.abs(deltaY) + " step=" + spriteStep);

            processPathToPlayer(moved);
        }

        // Calcul de la direction, et mouvement effectif
        //---------------------------------------------------------------------
        String dir;
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // déplacement horizontal
            dir = (deltaX > 0 ? "RIGHT" : "LEFT");
        } else {
            // déplacement vertical
            dir = (deltaY > 0 ? "UP" : "DOWN");
        }
        moveMob(dir, false);

        // TEST : téléportation --------------------
        //        setX(nextPoint.x);        //        setY(nextPoint.y);

//        System.out.println(mapPathToTarget.size() + ") moveToPlayer ............ " +
//                " entity={" + entityX + "/" + entityY + "} " +
//                " nextP={" + nextPoint.x + "/" + nextPoint.y + "}");
    }

    private boolean playerHasMoved() {
        if (targetPlayer == null) return true;

        Vector2int pos = map.pixelsToMapTile(targetPlayer.getFootX(), targetPlayer.getY());
        if (pos.equals(oldTargetPos)) {
            return false;
        }

        // le joueur a bougé
        oldTargetPos = pos;
//        System.out.println("playerHasMoved ###########################################");
        return true;
    }

    private boolean playerReached() {
        if (targetPlayer == null) return false;

        return hitbox.overlaps(targetPlayer.hitbox) ||

                (Math.abs(entityX - targetPlayer.entityX) < spriteStep &&
                        Math.abs(entityY - targetPlayer.entityY) < spriteStep) ||

                (Math.abs(getMiddleOfHitboxX() - targetPlayer.getMiddleOfHitboxX()) < spriteStep &&
                        Math.abs(getMiddleOfHitboxY() - targetPlayer.getMiddleOfHitboxY()) < spriteStep);
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }


    public void setTargetPlayer(Player player) {
        mapPathToTarget = null;
        oldTargetPos.x = -999;

        targetPlayer = player;
        if (map != null && targetPlayer != null) {
            mapPathToTarget = pathToTarget(targetPlayer);
        }
    }

    static final Texture debugTarget16 = new Texture("misc/target16x16.png");
    static final Texture debugTarget8 = new Texture("misc/target8x8.png");

    public void drawAndUpdate(SpriteBatch batch) {
        super.drawAndUpdate(batch);

        if (AStarMap.DEBUG_ASTAR && mapPathToTarget != null) {
            for (Vector2int v : mapPathToTarget) batch.draw(debugTarget16, v.x, v.y);
        }
    }

}