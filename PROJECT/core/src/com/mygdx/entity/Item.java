package com.mygdx.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Item implements Entity {

    //private static TextureAtlas allItemsAtlas; // tous les items pointent sur le même atlas

    private TextureRegion textureRegion;
    public Sprite sprite;
    public String uniqueID;
    public Color spriteTint;
    public Rectangle hitbox;
    private static int numItem = 0;

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
}
