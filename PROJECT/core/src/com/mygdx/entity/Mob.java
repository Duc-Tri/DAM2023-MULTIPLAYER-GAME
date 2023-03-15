package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPAtlasGenerator;
import com.mygdx.graphics.RMXPMonstersAtlas;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.AStarMap;
import com.mygdx.pathfinding.Vector2int;

import java.util.Arrays;
import java.util.List;

public class Mob extends LivingEntity {

    private static Map map; // la même carte pour tous les monstres
    private static AStarMap aStarMap; // le pathfinding, le même pour tous
    private Player targetPlayer; // chaque monstre peut poursuivre un joueur différent !
    private static TextureAtlas allMonstersAtlas; // le même atlas pour tous les monstres
    private int WAIT_FRAMES = 0; // pour régler la vitesse de moveToPlayer
    private TextureRegion textureRegion;
    public String mobID;
    public Color spriteTint;
    private float width = 0;
    private float height = 0;

    public Mob() {
        if (allMonstersAtlas == null) {
            System.out.println("initializeSprite .......... " + MainGame.MONSTERS_ATLAS);
            allMonstersAtlas = new TextureAtlas(Gdx.files.internal(MainGame.MONSTERS_ATLAS));
        }

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "mob" + R + V + B;
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        // TODO : remove randomness
        RMXP_CHARACTER = (int) (Math.random() * RMXPMonstersAtlas.MAX_MONSTERS) + "_";

        findRegion = RMXP_CHARACTER + "DOWN_0";
        this.hitbox = new Rectangle(entityX, entityY, 8, 8); // be precise ?

        initializeSprite();
        HITBOX_WIDTH = (int) ((sprite.getWidth() - 32) / 2); // temp
        HITBOX_XOFFSET = (int) ((sprite.getWidth() - HITBOX_WIDTH) / 2); // X :au mileu

        //System.out.println("---------------------------- CONSTRUCTOR Mob");

        // to position everything well ----------
        setX(getX());
        setY(getY());

        randomize(); // TODO : remove it after tests
        nextPoint = new Vector2int(0, 0);
    }

    public Mob(float x, float y, float width, float height) {
        entityX = x;
        entityY = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle(x, y, width, height);
    }

    public Mob(Rectangle rect) {
        this.hitbox = rect;
        entityX = rect.getX();
        entityY = rect.getY();
        this.width = rect.getWidth();
        this.height = rect.getHeight();
    }

    @Override
    public void initializeSprite() {

        // TEXTURE LOCAL DE LIVINGENTITY --------------------------------
        if (textureAtlas == null) {
            textureAtlas = allMonstersAtlas;
        }

        textureRegion = textureAtlas.findRegion(findRegion);

        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        sprite = new Sprite(textureRegion);

        //System.out.println("************** END initializeSprite " + findRegion + " / " + sprite);
    }

    public String getMobID() {
        return mobID;
    }

    // TEST DATA ==================================================================================
    private int totalSteps;
    private int maximumSteps;
    private String randomDir;
    private float targetTimeRandom;
    private float moveTimeRandom;
    private int spriteStep = 4;

    private void randomize() {
        WAIT_FRAMES = 2 + (int) (Math.random() * 2);

        randomDir = RMXPAtlasGenerator.randomDir();

        // pour la version moveToRandomDir(float deltaTime) -------
        moveTimeRandom = 0;
        targetTimeRandom = (float) (Math.random() * 5);

        // pour la version moveToRandomDir() ----------------------
        totalSteps = 0;
        maximumSteps = (int) (Math.random() * 200);
        spriteStep = 2 + (int) (Math.random() * 6);
    }

    // move to random direction, until time reached
    public void moveToRandomDir(float deltaTime) {
        moveTimeRandom += deltaTime;
        if (moveTimeRandom > targetTimeRandom) {
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

//        System.out.println(this.uniqueID + " d=" + dirKeyword + " t=" + moveTimeRandom + "/" + targetTimeRandom);

        if (collideWithObstacle && map.checkObstacle(this, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        if (deltaX != 0) {
            setX(getX() + deltaX);
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

        aStarCurrentPos = map.pixelsToMapTile(entityX, entityY);
        aStarGoal = map.pixelsToMapTile(target.entityX, target.entityY);

        List<Vector2int> path = aStarMap.findPath(aStarCurrentPos, aStarGoal);
        if (path != null)
            path.add(0, new Vector2int(target.entityX, target.entityY));

//        System.out.println("pathToTarget ################################################ " +
//                (path == null ? "PATH NULL" : Arrays.toString(path.toArray())));

        return path;
    }

    private void initPathToPlayer() {
        if (mapPathToTarget == null || mapPathToTarget.size() == 0) {
            mapPathToTarget = pathToTarget(targetPlayer);
        }

        if (mapPathToTarget != null) {
            nextPoint = mapPathToTarget.get(mapPathToTarget.size() - 1);
            mapPathToTarget.remove(mapPathToTarget.size() - 1);
        }
    }

    int waitBeforeMove = WAIT_FRAMES + 1;

    public void moveToPlayer() {

        if (playerReached() || waitBeforeMove++ < WAIT_FRAMES)
            return;
        else
            waitBeforeMove = 0;

        float deltaX = nextPoint.x - entityX;
        float deltaY = nextPoint.y - entityY;

        // on a atteint le point courant => point suivant
        // ou recalcul du pathfinding si plus de point
        //-----------------------------------------------------
        if (nextPoint.x == 0 || nextPoint.y == 0 || (Math.abs(deltaX) < spriteStep && Math.abs(deltaY) < spriteStep)) {

//            if (nextPoint.x > 0 && nextPoint.y > 0) {
//                setX(nextPoint.x);
//                setY(nextPoint.y);
//            }

            initPathToPlayer();
        }

//        System.out.println(mapPathToTarget.size() + ") moveToPlayer ............ " +
//                " entity={" + entityX + "/" + entityY + "} " +
//                " nextP={" + nextPoint.x + "/" + nextPoint.y + "}");

        // TEST : téléportation ---------------
//        setX(nextPoint.x);
//        setY(nextPoint.y);

        // Calcul de la direction, et mouvement effectif
        //--------------------------------------------------
        String dir;
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // déplacement horizontal
            dir = (deltaX > 0 ? "RIGHT" : "LEFT");
        } else {
            // déplacement vertical
            dir = (deltaY > 0 ? "UP" : "DOWN");
        }
        moveMob(dir, false);
    }

    private boolean playerReached() {
        return hitbox.overlaps(targetPlayer.hitbox) ||
                (Math.abs(entityX - targetPlayer.entityX) < spriteStep &&
                        Math.abs(entityY - targetPlayer.entityY) < spriteStep) ||
                (Math.abs(getMiddleOfHitboxX() - targetPlayer.getMiddleOfHitboxX()) < spriteStep &&
                        Math.abs(getMiddleOfHitboxY() - targetPlayer.getMiddleOfHitboxY()) < spriteStep);
    }

    public Player getTargetPlµayer() {
        return targetPlayer;
    }

    Vector2int oldTargetPos;

    public void setTargetPlayer(Player player) {
        targetPlayer = player;
        if (map != null)
            oldTargetPos = map.pixelsToMapTile(targetPlayer.getMiddleOfHitboxX(), targetPlayer.getMiddleOfHitboxY());
    }

}