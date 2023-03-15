package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bagarre.GameScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactersAtlas;

// Entity qui
public class LivingEntity implements Entity {

    protected TextureAtlas textureAtlas;

    // TEMPORAIRE : un personnage fait 32x48 pixels, la hitbox est très petite, elle est aux pieds
    private static final int CHAR_WIDTH = 32;
    protected int HITBOX_WIDTH = 8;
    protected int HITBOX_HEIGHT = 8;
    protected int HITBOX_YOFFSET = 0; // Y : aux pieds du sprite
    protected int HITBOX_XOFFSET = (CHAR_WIDTH - HITBOX_WIDTH) / 2; // X :au mileu

    protected Rectangle hitbox;
    protected MainGame mainGame;
    protected int compteurUp = 0;
    protected int compteurDown = 0;
    protected int compteurLeft = 0;
    protected int compteurRight = 0;

    protected TextureRegion textureRegion;
    protected Sprite sprite;
    protected float entityX = -1;
    protected float entityY = -1;

    public String uniqueID;
    public Color spriteTint; // from unique ID

    String findRegion = "";

    protected String serverUniqueID;

    protected String RMXP_CHARACTER; // non de la région texture dans l'atlas

    public LivingEntity() {
        //System.out.println("########## CONSTRUCTOR LivingEntity");
    }

    @Override
    public void initializeSprite() {
    }

    @Override
    public void animate(String string) {
        float tempSpriteX = sprite.getX();
        float tempSpriteY = sprite.getY();

        if (string.contentEquals("LEFT")) {
            compteurDown = 0;
            compteurUp = 0;
            compteurRight = 0;
            compteurLeft++;
            if (compteurLeft == RMXPCharactersAtlas.ANIM_FRAMES) {
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
            if (compteurRight == RMXPCharactersAtlas.ANIM_FRAMES) {
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
            if (compteurUp == RMXPCharactersAtlas.ANIM_FRAMES) {
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
            if (compteurDown == RMXPCharactersAtlas.ANIM_FRAMES) {
                compteurDown = 0;
            }
            findRegion = RMXP_CHARACTER + "DOWN_" + compteurDown;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        sprite.setRegion(textureRegion);
    }

    public float getX() {
        return entityX;
    }

    public float getY() {
        return entityY;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
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

    private MainGame getGame() {
        return mainGame;
    }

    private void setGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setX(float x) {
        this.entityX = x;
        hitbox.setX(x + HITBOX_XOFFSET);
        sprite.setX(x);
    }

    public void setY(float y) {
        this.entityY = y;
        hitbox.setY(y + HITBOX_YOFFSET);
        sprite.setY(y);
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
        return MainGame.PLAYERS_ATLAS;
    }

    public String getServerUniqueID() {
        return serverUniqueID;
    }

    public void setServerUniqueID(String serverUniqueID) {
        this.serverUniqueID = serverUniqueID;
    }

    public void drawAndUpdate(SpriteBatch batch) {
        if (batch == null || sprite == null || sprite.getTexture() == null) return;

        sprite.draw(batch);
    }

    public void debug(ShapeRenderer renderer) {

        // DONT WORK !

        Vector3 camXYZ = GameScreen.getCamera().position;
        Vector3 screenXYZ = GameScreen.getCamera().project(camXYZ);

        float screenHitboxX = hitbox.x + screenXYZ.x; // - GameScreen.SCREEN_WIDTH / 2.0f
        float screenHitboxY = hitbox.y + screenXYZ.y; //- GameScreen.SCREEN_HEIGHT / 2.0f

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        renderer.rect(screenHitboxX, screenHitboxY, hitbox.width, hitbox.height);
        renderer.end();
    }

}
