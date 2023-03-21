package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;


public class DebugOnScreen {
    public static final int MAX_TEXTS = 30;
    private static int SCREEN_HALF_WIDTH, SCREEN_HALF_HEIGHT;
    private static int DEBUG_WIDTH;
    private static int DEBUG_Y;
    private String[] debugTexts = new String[MAX_TEXTS];

    //private final SpriteBatch spriteBatch;

    BitmapFont font;
    ClampedCamera cam;

    private static DebugOnScreen instance;

    public static DebugOnScreen getInstance() {
        if (instance == null) {
            instance = new DebugOnScreen(GameScreen.getCamera());
        }

        return instance;
    }

    private DebugOnScreen(ClampedCamera clampedCamera) {
        cam = clampedCamera;
        //spriteBatch = batch;
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

    public void draw(SpriteBatch spriteBatch, String text, int x, int y) {
        font.draw(spriteBatch, text, x, y, DEBUG_WIDTH, Align.left, true);
    }

    public void drawTexts(SpriteBatch batch) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < debugTexts.length; i++) {
            String s = debugTexts[i];
            if (s != null && !s.trim().isEmpty())
                sb.append(String.format("%02d", i) + "> " + s.trim() + "\n");
//            else sb.append(String.format("%02d", i) + "> _\n");
        }

        float x = (cam.position.x) - SCREEN_HALF_WIDTH * cam.zoom;
        float y = (cam.position.y - 3) + SCREEN_HALF_HEIGHT * cam.zoom;
        font.draw(batch, sb, x, y, DEBUG_WIDTH, Align.left, true);
    }

    public void setText(int textIndex, String text) {
        if (textIndex > MAX_TEXTS)
            System.err.println("lignes maximum pour DEBUG: " + MAX_TEXTS);
        else
            debugTexts[textIndex] = text;
    }

    //font.draw(batch, "Score", HUDLeftX, HUDRow1Y, HUDSectionWidth, Align.left, false);
}