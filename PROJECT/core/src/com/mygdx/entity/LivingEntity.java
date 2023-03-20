package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.LifeBar;
import com.mygdx.graphics.RMXPCharactersAtlas;

//#################################################################################################
// Entity qui possède des points de vie, des sprites, etc.
//=================================================================================================
//
//#################################################################################################
public class LivingEntity implements Entity {
    public static final boolean DEBUG_HITBOX = true;
    protected TextureAtlas textureAtlas;

    // TEMPORAIRE : un personnage fait 32x48 pixels, la hitbox est très petite, elle est aux pieds
    private static final int CHAR_WIDTH = 32;
    protected int HITBOX_WIDTH = 16;
    protected int HITBOX_HEIGHT = 16;
    protected int HITBOX_YOFFSET = 0; // Y : aux pieds du sprite
    protected int HITBOX_XOFFSET = (CHAR_WIDTH - HITBOX_WIDTH) / 2; // X :au mileu

    protected Rectangle hitbox;
    protected int compteurUp = 0;
    protected int compteurDown = 0;
    protected int compteurLeft = 0;
    protected int compteurRight = 0;

    protected TextureRegion textureRegion;
    protected Sprite sprite;
    protected float entityX = -1;
    protected float entityY = -1;

    protected String uniqueID; // accessible aux classes filles
    public Color spriteTint; // from unique ID

    String findRegion = "";
    protected String serverUniqueID;
    protected String RMXP_CHARACTER; // non de la région texture dans l'atlas
    private static int numLivingEntity = 0;
    protected int currentLife = 1;
    protected int maxLife = 1;
    private static MainGame mainGame;
    protected LifeBar lifeBar;

    public LivingEntity() {
        if (mainGame == null) mainGame = MainGame.getInstance();

        //System.out.println("########## CONSTRUCTOR LivingEntity");
        numLivingEntity++;
        lifeBar = new LifeBar(this);
    }

    @Override
    public void initializeSprite() {
    }

    @Override
    public void animate(String string) {
        if (string.contentEquals("LEFT")) {
            compteurLeft = (compteurLeft + 1) % RMXPCharactersAtlas.ANIM_FRAMES;
            findRegion = RMXP_CHARACTER + "LEFT_" + compteurLeft;
        } else if (string.contentEquals("RIGHT")) {
            compteurRight = (compteurRight + 1) % RMXPCharactersAtlas.ANIM_FRAMES;
            findRegion = RMXP_CHARACTER + "RIGHT_" + compteurRight;
        } else if (string.contentEquals("UP")) {
            compteurUp = (compteurUp + 1) % RMXPCharactersAtlas.ANIM_FRAMES;
            findRegion = RMXP_CHARACTER + "UP_" + compteurUp;
        } else if (string.contentEquals("DOWN")) {
            compteurDown = (compteurDown + 1) % RMXPCharactersAtlas.ANIM_FRAMES;
            findRegion = RMXP_CHARACTER + "DOWN_" + compteurDown;
        }

        textureRegion = textureAtlas.findRegion(findRegion);
        //System.out.println("animate _______________ " + findRegion);
        sprite.setRegion(textureRegion);
    }

    public float getX() {
        return entityX;
    }

    public float getFootX() {
        if (sprite == null) return 0;

        return entityX + sprite.getWidth() / 2;
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

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setX(float x) {
        entityX = x;
        hitbox.setX(x + HITBOX_XOFFSET);
        sprite.setX(x);
    }

    public void setY(float y) {
        entityY = y;
        if (hitbox != null)
            hitbox.setY(y + HITBOX_YOFFSET);
        if (sprite != null)
            sprite.setY(y);
    }

    public void setFootX(float x) {
        if (sprite == null) return;

        setX(x - sprite.getWidth() / 2);
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String id) {
        uniqueID = id;
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

    private static final Texture debugTarget8 = new Texture("misc/target8x8.png");

    public void drawAndUpdate(SpriteBatch batch) {
        if (batch == null || sprite == null || sprite.getTexture() == null) return;

        sprite.draw(batch);
        lifeBar.draw(batch);

        if (DEBUG_HITBOX)
            batch.draw(debugTarget8, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public float getMiddleOfHitboxX() {
        return hitbox.x + hitbox.width / 2; // HITBOX_XOFFSET
    }

    public float getMiddleOfHitboxY() {
        return hitbox.y + hitbox.height / 2; // HITBOX_YOFFSET
    }

    protected int nextUniqueId() {

        // protected = uniquement pour les classes filles
        //return Math.abs(System.currentTimeMillis() + (int) (Math.random() * 1000000));
        return numLivingEntity + (int) (Math.random() * 10000);
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(int l) {
        currentLife = l;
    }

    public int getMaxLife() {
        return maxLife;
    }

}
