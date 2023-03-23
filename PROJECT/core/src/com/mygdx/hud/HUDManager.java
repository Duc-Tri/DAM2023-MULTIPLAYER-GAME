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
import com.mygdx.entity.Monsters;

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
    private static Sprite gameoverSprite;

    private static Sprite victorySprite;
    private static final String gameOverText = "Vous êtes mort !";
    private static final String victoryText = "Bravo ! Vous avez tué tous les monstres !";
    private static final Texture gameOverScreen = new Texture("misc/bloody_screen2.png");
    private static final Texture victoryScreen = new Texture("misc/clouds.png");

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
        if (cam != null)
            fontParameter.size = (int) (60 * cam.zoom);
        else
            fontParameter.size = 60;
        fontParameter.borderWidth = 2.6f;
        fontParameter.color = Color.WHITE;
        fontParameter.borderColor = new Color(0, 0, 0, 0.8f);
        fontParameter.spaceX = 1;

        fontDead = fontGenerator.generateFont(fontParameter);

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        SCREEN_HALF_WIDTH = SCREEN_WIDTH / 2f;
        SCREEN_HALF_HEIGHT = SCREEN_HEIGHT / 2f;

        // GAME OVER ==========================================================
        gameoverSprite = new Sprite(gameOverScreen);
        gameoverSprite.setOriginCenter();
        gameoverSprite.setScale(cam.zoom * (SCREEN_WIDTH / gameOverScreen.getWidth()), cam.zoom * (SCREEN_HEIGHT / gameOverScreen.getHeight()));
        gameoverSprite.setScale(1.4f, 1.4f);

        victorySprite = new Sprite(victoryScreen);
        victorySprite.setOriginCenter();
        victorySprite.setScale(cam.zoom * (SCREEN_WIDTH / victoryScreen.getWidth()), cam.zoom * (SCREEN_HEIGHT / victoryScreen.getHeight()));
        victorySprite.setScale(4f, 4f);
    }

    public void drawHUD(SpriteBatch batch) {
        if (!GameScreen.getPlayer().isAlive())
            gameOverScreen(batch);
        else if (Monsters.getInstance().allMonstersKilled())
            victoryScreen(batch);
    }

    private void gameOverScreen(SpriteBatch batch) {
        gameoverSprite.setX(cam.position.x - SCREEN_HALF_WIDTH + 50); // pourquoi 50 ???
        gameoverSprite.setY(cam.position.y - SCREEN_HALF_HEIGHT);
        gameoverSprite.draw(batch, 0.4f);

        float x = cam.position.x - SCREEN_HALF_WIDTH * cam.zoom;
        float y = cam.position.y;
        fontDead.draw(batch, gameOverText, x, y, cam.zoom * SCREEN_WIDTH, Align.center, true);
    }

    private void victoryScreen(SpriteBatch batch) {
        victorySprite.setX(cam.position.x - SCREEN_HALF_WIDTH + 50); // pourquoi 50 ???
        victorySprite.setY(cam.position.y - SCREEN_HALF_HEIGHT);
        victorySprite.draw(batch, 0.6f);

        float x = cam.position.x - SCREEN_HALF_WIDTH * cam.zoom;
        float y = cam.position.y;
        fontDead.draw(batch, victoryText, x, y, cam.zoom * SCREEN_WIDTH, Align.center, true);
    }


    public void setText(int textIndex, String text) {
    }
}
