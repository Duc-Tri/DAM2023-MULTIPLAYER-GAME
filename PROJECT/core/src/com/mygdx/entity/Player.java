package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.GameScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactesAtlasGenerator;


public class Player implements Entity {

    private final static String textureAtlasPath = "characters/RMXP_humans.atlas"; //"tiny_16x16.atlas";
    private static TextureAtlas textureAtlas;

    // TEMPORAIRE : un personnage fait 32x48 pixels, la hitbox est très petite, elle est aux pieds
    private static final int CHAR_WIDTH = 32;
    private static final int HITBOX_WIDTH = 8;
    private static final int HITBOX_HEIGHT = 8;
    private static final int HITBOX_YOFFSET = 0; // Y : aux pieds du sprite
    private static final int HITBOX_XOFFSET = (CHAR_WIDTH - HITBOX_WIDTH) / 2; // X :au mileu

    private Rectangle hitbox;
    private MainGame mainGame;
    private int compteurUp = 0;
    private int compteurDown = 0;
    private int compteurLeft = 0;
    private int compteurRight = 0;
    private TextureRegion textureRegion;
    private Sprite sprite;
    private float x = -1;
    private float y = -1;

    private float realX = -1;
    private float realY = -1;
    public String uniqueID;
    public Color spriteTint; // from unique ID

    String findRegion = "";

    //////////float scale = 2.0f;
    private String serverUniqueID;

    private String RMXP_CHARACTER; // le personnage dans la feuille de sprites




    public Player() {

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "player" + R + V + B;
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        RMXP_CHARACTER = (int) (Math.random() * RMXPCharactesAtlasGenerator.MAX_CHARACTERS) + "_";
        findRegion = RMXP_CHARACTER + "DOWN_0";

        initializeSprite();

        // to position everything well ----------
        setX(getX());
        setY(getY());
    }

    public void initializeSprite() {
        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal(this.textureAtlasPath));
        }
        textureRegion = textureAtlas.findRegion(findRegion);

        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        sprite = new Sprite(textureRegion);
        //sprite.scale(scale);
        //sprite.setColor(spriteTint);
    }

    private Sprite getSprite() {
        return sprite;
    }

    public void setX(float playerX) {
        this.x = playerX;
        hitbox.setX(playerX + HITBOX_XOFFSET);
        sprite.setX(playerX);
    }

    public void setY(float playerY) {
        this.y = playerY;
        hitbox.setY(playerY + HITBOX_YOFFSET);
        sprite.setY(playerY);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void animate(String string) {
        float tempSpriteX = sprite.getX();
        float tempSpriteY = sprite.getY();

        if (string.contentEquals("LEFT")) {
            compteurDown = 0;
            compteurUp = 0;
            compteurRight = 0;
            compteurLeft++;
            if (compteurLeft == RMXPCharactesAtlasGenerator.ANIM_FRAMES) {
                compteurLeft = 0;
            }
            findRegion = RMXP_CHARACTER + "LEFT_" + compteurLeft;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        if (string.contentEquals("RIGHT")) {
            compteurDown = 0;
            compteurUp = 0;
            compteurLeft = 0;
            compteurRight++;
            if (compteurRight == RMXPCharactesAtlasGenerator.ANIM_FRAMES) {
                compteurRight = 0;
            }
            findRegion = RMXP_CHARACTER + "RIGHT_" + compteurRight;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        if (string.contentEquals("UP")) {
            compteurDown = 0;
            compteurRight = 0;
            compteurLeft = 0;
            compteurUp++;
            if (compteurUp == RMXPCharactesAtlasGenerator.ANIM_FRAMES) {
                compteurUp = 0;
            }
            findRegion = RMXP_CHARACTER + "UP_" + compteurUp;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        if (string.contentEquals("DOWN")) {
            compteurUp = 0;
            compteurRight = 0;
            compteurLeft = 0;
            compteurDown++;
            if (compteurDown == RMXPCharactesAtlasGenerator.ANIM_FRAMES) {
                compteurDown = 0;
            }
            findRegion = RMXP_CHARACTER + "DOWN_" + compteurDown;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        getSprite().setRegion(textureRegion);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Color getSpriteTint() {
        return spriteTint;
    }

    public void setSpriteTint(Color spriteTint) {
        this.spriteTint = spriteTint;
    }

    public String getFindRegion() {
        return findRegion;
    }

    public void setFindRegion(String findRegion) {
        this.findRegion = findRegion;
        textureRegion = textureAtlas.findRegion(this.findRegion);
        getSprite().setRegion(textureRegion);
    }

    public String getTextureAtlasPath() {
        return textureAtlasPath;
    }

    public String getServerUniqueID() {
        return serverUniqueID;
    }

    public void setServerUniqueID(String serverUniqueID) {
        this.serverUniqueID = serverUniqueID;
    }

    public void drawAndUpdate(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public int getCompteurUp() {
        return compteurUp;
    }

    public void setCompteurUp(int compteurUp) {
        this.compteurUp = compteurUp;
    }

    public int getCompteurDown() {
        return compteurDown;
    }

    public void setCompteurDown(int compteurDown) {
        this.compteurDown = compteurDown;
    }

    public int getCompteurLeft() {
        return compteurLeft;
    }

    public void setCompteurLeft(int compteurLeft) {
        this.compteurLeft = compteurLeft;
    }

    public int getCompteurRight() {
        return compteurRight;
    }

    public void setCompteurRight(int compteurRight) {
        this.compteurRight = compteurRight;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    private float calculateRealX() {
        float relativePlayerX = x - GameScreen.SCREEN_WIDTH / 2.0f + GameScreen.getCamera().position.x;
        return relativePlayerX;
    }

    private float calculateRealY() {
        float relativePlayerY = y - GameScreen.SCREEN_HEIGHT / 2.0f + GameScreen.getCamera().position.y;// + 10;
        return relativePlayerY;
    }

    public void setYFromRealY() {
        float temp = calculateRealY() - GameScreen.getCamera().position.y + GameScreen.SCREEN_HEIGHT / 2.0f;
        setY(temp);
    }

    public void setXFromRealX() {
        float temp = calculateRealX() - GameScreen.getCamera().position.x + GameScreen.SCREEN_WIDTH / 2.0f; // + 10;
        setX(temp);
    }
    private MainGame getGame() {
        return mainGame;
    }

    private void setGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    private void debug(ShapeRenderer renderer) {
        float relativeHitboxX = hitbox.x + GameScreen.getCamera().position.x; // - GameScreen.SCREEN_WIDTH / 2.0f
        float relativeHitboxY = hitbox.y + GameScreen.getCamera().position.y; //- GameScreen.SCREEN_HEIGHT / 2.0f

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        renderer.rect(relativeHitboxX, relativeHitboxY, hitbox.width, hitbox.height);
        renderer.end();
    }


    public float getRealX() {
        return realX;
    }

    public void setRealX(float realX) {
        this.realX = realX;
    }

    public float getRealY() {
        return realY;
    }

    public void setRealY(float realY) {
        this.realY = realY;
    }
}
