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

public class Mob extends LivingEntity {

    private static TextureAtlas allMonstersAtlas;

    private TextureRegion textureRegion;
    public String mobID;
    public Color spriteTint;
    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;

    private String currentDir;
    private static Map map;


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
        this.hitbox = new Rectangle(this.x, this.y, 8, 8); // be precise ?

        initializeSprite();
        HITBOX_WIDTH = (int) ((sprite.getWidth() - 32) / 2); // temp
        HITBOX_XOFFSET = (int) ((sprite.getWidth() - HITBOX_WIDTH) / 2); // X :au mileu

        //System.out.println("---------------------------- CONSTRUCTOR Mob");

        // to position everything well ----------
        setX(getX());
        setY(getY());

        randomize(); // TODO : remove it after tests
    }

    public Mob(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle(x, y, width, height);
    }

    public Mob(Rectangle rect) {
        this.hitbox = rect;
        this.x = rect.getX();
        this.y = rect.getY();
        this.width = rect.getWidth();
        this.height = rect.getHeight();
    }

    @Override
    public void initializeSprite() {

        // TEXTURE LOCAL DE LIVINGENTITY --------------------------------------
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
        randomDir = RMXPAtlasGenerator.randomDir();

        // pour la version moveToRandomDir(float deltaTime)
        moveTimeRandom = 0;
        targetTimeRandom = (float) (Math.random() * 5);

        // pour la version moveToRandomDir()
        totalSteps = 0;
        maximumSteps = (int) (Math.random() * 200);
    }

    // move to random direction, until time reached
    public void moveToRandomDir(float deltaTime) {
        moveTimeRandom += deltaTime;
        if (moveTimeRandom > targetTimeRandom) {
            randomize();
        }
        moveMob(randomDir);
    }

    public void moveToRandomDir() {
        totalSteps++;
        if (totalSteps > maximumSteps) {
            randomize();
        }
        moveMob(randomDir);
    }

    private void moveMob(String dirKeyword) {

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

        if (map.checkObstacle(this, deltaX, deltaY))
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
    }

    public static Map getMap() {
        return map;
    }
}


