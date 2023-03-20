package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;

//#################################################################################################
// Debug à l'écran
//=================================================================================================
// - nécessite le chargement d'une FONT (jack_input)
//
// - organisé en lignes séparées, on peut donc "réserver" une ligne pour un log spécifique
//#################################################################################################
public class DebugOnScreen {
    public static final int MAX_LINES = 30;
    private static int SCREEN_HALF_WIDTH, SCREEN_HALF_HEIGHT;
    private static int WIDTH;
    private static int Y;
    private String[] debugTexts = new String[MAX_LINES];

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
        // create bitmap font ----------------------------------------------------------------------
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("misc/jack_input.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 20;
        fontParameter.borderWidth = 2.6f;
        fontParameter.color = new Color(1, 1, 1, 0.9f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.8f);
        fontParameter.spaceX = 1;

        font = fontGenerator.generateFont(fontParameter);
        cam = clampedCamera;

        //scale font to fit world ?
        // font.getData().setScale(0.07f); // ??????????
        // HUDVerticalMargin = font.getCapHeight() / 2;

        Y = Gdx.graphics.getHeight();
        WIDTH = Gdx.graphics.getWidth();
        SCREEN_HALF_WIDTH = WIDTH / 2;
        SCREEN_HALF_HEIGHT = Y / 2;
    }

    public void draw(SpriteBatch spriteBatch, String text, int x, int y) {
        font.draw(spriteBatch, text, x, y, WIDTH, Align.left, true);
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
        font.draw(batch, sb, x, y, WIDTH, Align.left, true);
    }

    public void setText(int textIndex, String text) {
        if (textIndex > MAX_LINES)
            System.err.println("lignes maximum pour DEBUG: " + MAX_LINES);
        else
            debugTexts[textIndex] = text;
    }

}