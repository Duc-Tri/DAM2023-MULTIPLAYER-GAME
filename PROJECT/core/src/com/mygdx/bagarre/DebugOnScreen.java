package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.google.firebase.database.snapshot.Index;

public class DebugOnScreen {
    public static final int MAX_TEXTS = 25;
    private static int SCREEN_HALF_WIDTH, SCREEN_HALF_HEIGHT;
    private static int DEBUG_WIDTH;
    private static int DEBUG_Y;
    private String[] debugTexts = new String[MAX_TEXTS];
    private final SpriteBatch spriteBatch;

    //font.draw(batch, "Score", HUDLeftX, HUDRow1Y, HUDSectionWidth, Align.left, false);

    BitmapFont font;
    ClampedCamera cam;

    public DebugOnScreen(SpriteBatch batch, ClampedCamera clampedCamera) {
        cam = clampedCamera;
        spriteBatch = batch;
        // create bitmap font ------------------------------------------------------------
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("jack_input.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 20;
        fontParameter.borderWidth = 2.6f;
        fontParameter.color = new Color(1, 1, 1, 0.9f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.8f);
        fontParameter.spaceX = 1;

        font = fontGenerator.generateFont(fontParameter);

        //scale font to fit world -----------------------------------------------------------------
        //font.getData().setScale(0.07f); // ??????????

        //calculate HUD margins, etc.

//        HUDVerticalMargin = font.getCapHeight() / 2;
//        HUDLeftX = HUDVerticalMargin;
//        HUDRightX = WORLD_WIDTH * 2 / 3 - HUDLeftX;
//        HUDCentreX = WORLD_WIDTH / 3;
//        HUDRow1Y = WORLD_HEIGHT - HUDVerticalMargin;
//        HUDRow2Y = HUDRow1Y - HUDVerticalMargin - font.getCapHeight();
//        HUDSectionWidth = WORLD_WIDTH / 3;
        DEBUG_Y = Gdx.graphics.getHeight();
        DEBUG_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HALF_WIDTH = DEBUG_WIDTH / 2;
        SCREEN_HALF_HEIGHT = DEBUG_Y / 2;
    }

    public DebugOnScreen(SpriteBatch batch) {
        spriteBatch = batch;
    }

    public void draw(String text, int x, int y) {
        font.draw(spriteBatch, text, x, y, DEBUG_WIDTH, Align.left, true);
    }

    public void drawTexts() {
        StringBuilder sb = new StringBuilder();
        for (String s : debugTexts) {
            if (s != null && !s.trim().isEmpty())
                sb.append("> " + s.trim() + "\n");
        }

        float x = (cam.position.x ) - SCREEN_HALF_WIDTH* cam.zoom;
        float y = (cam.position.y  - 3) + SCREEN_HALF_HEIGHT* cam.zoom;
        font.draw(spriteBatch, sb, x, y, DEBUG_WIDTH, Align.left, true);
    }

    public void setText(int textIndex, String text) {
        if (textIndex > MAX_TEXTS)
            System.err.println("lignes maximum pour DEBUG: " + MAX_TEXTS);
        else
            debugTexts[textIndex] = text;
    }
}