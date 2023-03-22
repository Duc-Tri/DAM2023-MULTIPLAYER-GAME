package com.mygdx.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.mygdx.bagarre.ClampedCamera;
import com.mygdx.bagarre.GameScreen;

//#################################################################################################
// Affichage du texte à l'écran
//=================================================================================================
// - nécessite le chargement d'une FONT (jack_input)
//
// - organisé en lignes séparées, on peut donc "réserver" une ligne pour un log spécifique
//#################################################################################################
public class HUDManager {
    private static float SCREEN_HEIGHT, SCREEN_WIDTH;
    private static float SCREEN_HALF_WIDTH, SCREEN_HALF_HEIGHT;
    private static BitmapFont fontDead;
    private static ClampedCamera cam;

    // GAME OVER --------------------------------
    private static Sprite bloodyScreen;
    private static final String gameOverText = "Vous êtes mort !";
    private static final Texture gameOverScreen = new Texture("misc/bloody_screen2.png");

    private static HUDManager instance;

    public static HUDManager getInstance() {
        if (instance == null) {
            instance = new HUDManager(GameScreen.getCamera());
        }

        return instance;
    }

    private HUDManager(ClampedCamera clampedCamera) {
        cam = clampedCamera;

        // create bitmap font ----------------------------------------------------------------------
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("misc/jack_input.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = (int) (60 * cam.zoom);
        fontParameter.borderWidth = 2.6f;
        fontParameter.color = Color.RED;
        fontParameter.borderColor = new Color(0, 0, 0, 0.8f);
        fontParameter.spaceX = 1;

        fontDead = fontGenerator.generateFont(fontParameter);

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        SCREEN_HALF_WIDTH = SCREEN_WIDTH / 2f;
        SCREEN_HALF_HEIGHT = SCREEN_HEIGHT / 2f;

        // GAME OVER ==========================================================
        bloodyScreen = new Sprite(gameOverScreen);
        bloodyScreen.setOriginCenter();
        bloodyScreen.setScale(cam.zoom * (SCREEN_WIDTH / bloodyScreen.getWidth()), cam.zoom * (SCREEN_HEIGHT / bloodyScreen.getHeight()));
        bloodyScreen.setScale(1.4f, 1.4f);
    }

    public void drawHUD(SpriteBatch batch) {
        if (!GameScreen.getPlayer().isAlive())
            gameOverScreen(batch);
    }

    private void gameOverScreen(SpriteBatch batch) {
        bloodyScreen.setX(cam.position.x - SCREEN_HALF_WIDTH + 50); // pourquoi 50 ???
        bloodyScreen.setY(cam.position.y - SCREEN_HALF_HEIGHT);
        bloodyScreen.draw(batch, 0.4f);

        float x = cam.position.x - SCREEN_HALF_WIDTH * cam.zoom;
        float y = cam.position.y;
        fontDead.draw(batch, gameOverText, x, y, cam.zoom * SCREEN_WIDTH, Align.center, true);
    }


    public void setText(int textIndex, String text) {
    }
}