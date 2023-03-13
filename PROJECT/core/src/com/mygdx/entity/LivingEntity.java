package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactersAtlasGenerator;

// Entity qui
public class LivingEntity implements Entity {

    protected static TextureAtlas textureAtlas;

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

    protected String RMXP_CHARACTER; // non de la région dans l'atlas

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
            if (compteurLeft == RMXPCharactersAtlasGenerator.ANIM_FRAMES) {
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
            if (compteurRight == RMXPCharactersAtlasGenerator.ANIM_FRAMES) {
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
            if (compteurUp == RMXPCharactersAtlasGenerator.ANIM_FRAMES) {
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
            if (compteurDown == RMXPCharactersAtlasGenerator.ANIM_FRAMES) {
                compteurDown = 0;
            }
            findRegion = RMXP_CHARACTER + "DOWN_" + compteurDown;
            textureRegion = textureAtlas.findRegion(findRegion);
        }
        sprite.setRegion(textureRegion);
    }
}
