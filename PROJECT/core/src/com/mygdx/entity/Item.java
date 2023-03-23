package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item implements Entity {

    //private static TextureAtlas allItemsAtlas; // tous les items pointent sur le même atlas
    protected static final Texture debugTexture = new Texture("misc/yellow64x64.png");

    private TextureRegion textureRegion;
    public Sprite sprite;
    public String uniqueID;
    public Color spriteTint;
    public Rectangle hitbox;
    private static int numItem = 0;
    protected int HITBOX_YOFFSET = 0; // Y : aux pieds du sprite
    protected int HITBOX_XOFFSET = 0; // X : au mileu ?
    protected int SPRITE_XOFFSET = 0;
    protected int SPRITE_YOFFSET = 0;
    protected int HITBOX_WIDTH = 100;
    public int HITBOX_HEIGHT = 100;

    public Item() {
        uniqueID = "item" + numItem++;
    }

    public Item(float x, float y, float width, float height) {
    }

    public Item(Rectangle rect) {
    }

    /*
    public void setX(float x) {
        hitbox.setX(x + HITBOX_XOFFSET);
        hitbox.setWidth(sprite.getWidth());
        sprite.setX(x);
    }

    public void setY(float y) {
        hitbox.setY(y + HITBOX_YOFFSET);
        hitbox.setHeight(sprite.getHeight());
        sprite.setY(y);
    }
    */

}
