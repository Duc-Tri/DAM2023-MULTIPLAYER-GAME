package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item implements Entity {

    //private static TextureAtlas allItemsAtlas; // tous les items pointent sur le même atlas
    private static final Texture debugTarget8 = new Texture("misc/target8x8.png");


    private TextureRegion textureRegion;
    public Sprite sprite;
    public String uniqueID;
    public Color spriteTint;
    public Rectangle hitbox;
    private static int numItem = 0;
    protected int HITBOX_YOFFSET = 0; // Y : aux pieds du sprite
    protected int HITBOX_XOFFSET = 0; // X :au mileu


    public Item() {
        uniqueID = "item" + numItem++;
    }

    public Item(float x, float y, float width, float height) {

    }

    public Item(Rectangle rect) {

    }

    @Override
    public void initializeSprite() {
    }

    @Override
    public void animate(String string) {
    }

    public void setX(float x) {
        hitbox.setX(x + HITBOX_XOFFSET);
//        hitbox.setWidth(sprite.getWidth());
        sprite.setX(x);
    }

    public void setY(float y) {
        hitbox.setY(y + HITBOX_YOFFSET);
//        hitbox.setHeight(sprite.getHeight());
        sprite.setY(y);

    }

}
