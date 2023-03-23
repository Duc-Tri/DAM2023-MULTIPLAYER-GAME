package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.hud.DebugOnScreen;
import com.mygdx.hud.HUDManager;
import com.mygdx.input.Joystick;

//#################################################################################################
// GameScreen (Screen, InputProcessor)
//=================================================================================================
// - gère l'affichage générale
//
// - gère les inputs (tactile & clavier)
//#################################################################################################
public class LobbiesScreen implements Screen, InputProcessor {
    private final MainGame mainGame;
    private static OrthographicCamera clampedCamera;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private boolean showDebugTexts = true;

    private SpriteBatch batch;
    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    public static boolean lockOnListReadFromDB = false;

    private Texture testImage = new Texture("misc/bloody_screen2.png");

    private static float cameraZoom = 1; // plus c'est gros, plus on est loin
    private DebugOnScreen debugOS;
    private HUDManager hudManager;

    int threadPoolSize = 15;

    public LobbiesScreen(MainGame game) {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        mainGame = game;

        batch = new SpriteBatch();

        clampedCamera = new OrthographicCamera(); // ClampedCamera( MainGame.getInstance().runOnDesktop() ? 1f : 0.5f);
        batch.setProjectionMatrix(clampedCamera.combined);

        shapeRenderer = new ShapeRenderer();

        joystick = new Joystick(100, 100, MainGame.getInstance().runOnAndroid() ? 200 : 100);

        debugOS = DebugOnScreen.getInstance();
        hudManager = HUDManager.getInstance();
    }

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render(float deltaTime) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(clampedCamera.combined);

        batch.begin(); //======================================================

        if (showDebugTexts) debugOnScreen(); // TOUT A LA FIN !!!

        hudManager.drawHUD(batch);

        batch.end(); //========================================================

    }

    private void debugOnScreen() {
//        debugOS.setText(0, mainGame.getGameMode() + " / " + monstersInstance.getMonstersMode());
//        debugOS.setText(1, player.getUniqueID() + " / " + player.getNumLobby() + " / " + player.getLobbyPlayerId());
//        debugOS.setText(25, "" + System.currentTimeMillis());

//        for (int i = 2; i < DebugOnScreen.MAX_TEXTS; i++)
//            debugOS.setText(i, i + "/" + System.currentTimeMillis());

        debugOS.drawTexts(batch);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.ESCAPE:
                mainGame.showGameScreen();
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);
    }

    public static void setCameraZoom(float cameraZoom) {
        LobbiesScreen.cameraZoom = cameraZoom;
    }


}

/*
    public static boolean isLockOnListReadFromDB() {
        return lockOnListReadFromDB;
    }

    public static void setLockOnListReadFromDB(boolean lockOnListReadFromDB) {
        MainGame.lockOnListReadFromDB = lockOnListReadFromDB;
    }
 */