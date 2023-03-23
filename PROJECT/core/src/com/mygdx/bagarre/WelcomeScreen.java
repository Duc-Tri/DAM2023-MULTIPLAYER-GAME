package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

//#################################################################################################
// GameScreen (Screen, InputProcessor)
//=================================================================================================
// - gère l'affichage générale
//
// - gère les inputs (tactile & clavier)
//#################################################################################################
public class WelcomeScreen implements Screen, InputProcessor {
    private final MainGame mainGame;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private boolean showDebugTexts = true;

    private SpriteBatch batch;

    private Texture background = new Texture("misc/ocean.png");


    //===========================================================================================
    Stage stage;
    TextButton buttonSolo;
    TextButton buttonMulti;
    TextButton buttonLobby1;
    TextButton buttonLobby2;
    TextButton buttonLobby3;
    TextButton buttonLobby4;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;


    public WelcomeScreen(MainGame game) {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        mainGame = game;

        batch = new SpriteBatch();



        initStageButtons();

    }

    private void initStageButtons() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();

        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("misc/buttons.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");

        buttonSolo = new TextButton("JEU SOLO", textButtonStyle);
        buttonSolo.setPosition(SCREEN_WIDTH / 2 - buttonSolo.getWidth() / 2, SCREEN_HEIGHT / 2);

        buttonMulti = new TextButton("JEU MULTIJOUEUR", textButtonStyle);
        buttonMulti.setPosition(buttonSolo.getX(), buttonSolo.getY() + 200);

        stage.addActor(buttonSolo);
        stage.addActor(buttonMulti);

        buttonSolo.addListener(new EventListener() {

            @Override
            public boolean handle(Event event) {
                //Handle the input event.
                System.out.println(buttonSolo.getName() + " DOWN !");
                mainGame.showGameScreen(MainGame.GameMode.SOLO);
                return false;
            }
        });

        buttonMulti.addListener(new EventListener() {

            @Override
            public boolean handle(Event event) {
                //Handle the input event.
                System.out.println(buttonMulti.getName() + " DOWN !");
                mainGame.showGameScreen(MainGame.GameMode.MULTIPLAYER);
                return false;
            }
        });

    }

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render(float deltaTime) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin(); //======================================================

        //if (showDebugTexts) debugOnScreen(); // TOUT A LA FIN !!!
        batch.draw(background, 0, 0,SCREEN_WIDTH,SCREEN_HEIGHT);

        //hudManager.drawHUD(batch);

        batch.end(); //========================================================

        stage.draw();
    }

    private void debugOnScreen() {
//        debugOS.setText(0, mainGame.getGameMode() + " / " + monstersInstance.getMonstersMode());
//        debugOS.setText(1, player.getUniqueID() + " / " + player.getNumLobby() + " / " + player.getLobbyPlayerId());
//        debugOS.setText(25, "" + System.currentTimeMillis());

//        for (int i = 2; i < DebugOnScreen.MAX_TEXTS; i++)
//            debugOS.setText(i, i + "/" + System.currentTimeMillis());

//        debugOS.drawTexts(batch);
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
//        shapeRenderer.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.SPACE:
                //mainGame.showGameScreen();
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


}

/*
    public static boolean isLockOnListReadFromDB() {
        return lockOnListReadFromDB;
    }

    public static void setLockOnListReadFromDB(boolean lockOnListReadFromDB) {
        MainGame.lockOnListReadFromDB = lockOnListReadFromDB;
    }
 */