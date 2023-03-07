package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.bagarre.Game;
import com.mygdx.graphics.RMXPCharactesAtlasGenerator;


public class Player implements Entity {
    Game game;
    int compteurUp = 0;
    int compteurDown = 0;
    int compteurLeft = 0;
    int compteurRight = 0;
    private Rectangle box;
    //    private SpriteBatch batch;
    private static TextureAtlas textureAtlas;
    private TextureRegion textureRegion;
    public Sprite sprite;
    private float x = 0;
    private float y = 0;

    public String uniqueID;
    public Color spriteTint; // from unique ID

    String findRegion = "";

    private String textureAtlasPath = "characters/RMXP_humans.atlas"; //"tiny_16x16.atlas";


    float scale = 2.0f;
    String serverUniqueID;

    String RMXP_CHARACTER; // le personnage dans la feuille de sprites

    public Player(Game game) {
        this.game = game;

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "player" + R + V + B;
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        RMXP_CHARACTER = (int)(Math.random() * RMXPCharactesAtlasGenerator.MAX_CHARACTERS) + "_";
        findRegion = RMXP_CHARACTER + "UP_0";
    }

    public void initializeSprite() {
        box = new Rectangle(0, 0, 0, 0);

        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal(this.textureAtlasPath));
        }

        System.out.println(" === " + findRegion);
        textureRegion = textureAtlas.findRegion(findRegion);

        sprite = new Sprite(textureRegion);
        sprite.scale(scale);
        //sprite.setColor(spriteTint);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setX(float x) {
        this.x = x;
        box.setX(x);
        sprite.setX(x);
    }

    public void setY(float y) {
        this.y = y;
        box.setY(y);
        sprite.setY(y);
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

    public Rectangle getBox() {
        return box;
    }

    public void setBox(Rectangle box) {
        this.box = box;
    }

    public static void setTextureAtlas(TextureAtlas textureAtlas) {
        Player.textureAtlas = textureAtlas;
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

    public void setTextureAtlasPath(String textureAtlasPath) {
        this.textureAtlasPath = textureAtlasPath;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getServerUniqueID() {
        return serverUniqueID;
    }

    public void setServerUniqueID(String serverUniqueID) {
        this.serverUniqueID = serverUniqueID;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public float getRealX() {
        float relativePlayerX = x - game.getSCREEN_WIDTH() / 2.0f + game.getCamera().position.x;
        return relativePlayerX;
    }

    public float getRealY() {
        float relativePlayerY = y - game.getSCREEN_HEIGHT() / 2.0f + game.getCamera().position.y + 10;
        return relativePlayerY;
    }

    public void setXFromRealX(float parseFloat) {

        float temp = parseFloat - game.getCamera().position.x + game.getSCREEN_WIDTH() / 2.0f + 10;
        setX(temp);
    }

    public void setYFromRealY(float parseFloat) {

        float temp = parseFloat - game.getCamera().position.y + game.getSCREEN_HEIGHT() / 2.0f;
        setY(temp);
    }

}
