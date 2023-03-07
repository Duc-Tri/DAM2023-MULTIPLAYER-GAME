package com.mygdx.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Cursor {
    static final Texture spriteCursor = new Texture("test/cursor.png");
    private int framesCursorVisible;
    public int x, y;
    public Cursor(Color spriteTint) {
        framesCursorVisible = 0;
        x = 200;
        y = 700;
    }
    public void draw(Batch batch) {
        if (framesCursorVisible > 0) {
            batch.draw(spriteCursor, x, y);
            framesCursorVisible--;
        }
    }
    public void setPosition(int screenX, int screenY) {
        x = screenX - spriteCursor.getWidth() / 2;
        y = screenY - spriteCursor.getHeight() / 2;
        framesCursorVisible = 50;
    }


}
