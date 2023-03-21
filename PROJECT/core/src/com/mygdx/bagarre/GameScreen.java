package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.client.NewPlayer;
import com.mygdx.client.RetrieveMate;
import com.mygdx.client.RetrieveMonsters;
import com.mygdx.client.RetrieveUpdatePlayer;
import com.mygdx.client.UpdateMonsters;
import com.mygdx.client.UpdatePlayer;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;
import com.mygdx.hud.DebugOnScreen;
import com.mygdx.hud.HUDManager;
import com.mygdx.input.Joystick;
import com.mygdx.map.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//#################################################################################################
// GameScreen (Screen, InputProcessor)
//=================================================================================================
// - gère l'affichage générale
//
// - gère les inputs (tactile & clavier)
//#################################################################################################
public class GameScreen implements Screen, InputProcessor {
    private static Player player; // main player
    private final MainGame mainGame;
    private Mates mates;
    private Monsters monstersInstance;
    private boolean showJoystick = false;
    private static Map map;
    private static ClampedCamera clampedCamera;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private boolean showDebugTexts = true;

    private SpriteBatch batch;
    private int sizeOfStep = 8;
    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    public static boolean lockOnListReadFromDB = false;

    private Texture testImage = new Texture("test/tiny_16x16.png");

    private static float cameraZoom = 1; // plus c'est gros, plus on est loin
    private DebugOnScreen debugOS;
    private HUDManager hudManager;


    int threadPoolSize = 15;
    ThreadPoolExecutor threadPoolExecutor0 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor3 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor4 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    UpdatePlayer updatePlayer;
    RetrieveMate retrieveMate;
    RetrieveUpdatePlayer retrieveUpdatePlayer;
    private UpdateMonsters updateMonsters;
    private RetrieveMonsters retrieveMonsters;

    public GameScreen(String mapFilename, MainGame game) {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        mainGame = game;

        batch = new SpriteBatch();
        map = loadMap(mapFilename, batch);
        createPlayer();
        clampedCamera = new ClampedCamera(player, map, MainGame.getInstance().runOnDesktop() ? 1f : 0.5f);
        batch.setProjectionMatrix(clampedCamera.combined);

        shapeRenderer = new ShapeRenderer();

        joystick = new Joystick(100, 100, MainGame.getInstance().runOnAndroid() ? 200 : 100);

        debugOS = DebugOnScreen.getInstance();
        hudManager = HUDManager.getInstance();

        monstersInstance = Monsters.getInstance();
        monstersInstance.init(map, player);

        if (mainGame.isMultiplayerGameMode()) {
            mates = new Mates(player);
            createThreadsPool();
        }
    }

    private void createPlayer() {
        player = new Player(map);
        if (mainGame.isMultiplayerGameMode())
            NewPlayer.requestServer(player);
    }

    private Map loadMap(String mapFilename, SpriteBatch sb) {
        Map m = new Map(mapFilename, sb);

        return m;
    }

    private void createThreadsPool() {
        updatePlayer = new UpdatePlayer(player);
        threadPoolExecutor0.submit(updatePlayer);

        retrieveMate = new RetrieveMate(player);
        threadPoolExecutor1.submit(retrieveMate);

        retrieveUpdatePlayer = new RetrieveUpdatePlayer(player);
        threadPoolExecutor2.submit(retrieveUpdatePlayer);


        // MASTER ONLY
        if (player.isMaster()) {
            System.out.println(player.getUniqueID() + " IS MASTER **********************");
            updateMonsters = new UpdateMonsters(player, monstersInstance);
            threadPoolExecutor4.submit(updateMonsters);
        }

        // SLAVES & MASTER
        retrieveMonsters = new RetrieveMonsters(player, monstersInstance);
        threadPoolExecutor3.submit(retrieveMonsters);
    }

    public static Map getMap() {
        return map;
    }

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render(float deltaTime) {

        if (mainGame.isMultiplayerGameMode())
            submitThreadJobs();

        displayJoystick();


        if (Gdx.input.isTouched(0))
            movePlayer(joystick.getDirectionInput(), deltaTime);
        else {
            showJoystick = false;
            movePlayer(lastKeyCode, deltaTime);
        }

        monstersInstance.update(deltaTime);

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(clampedCamera.combined);

        batch.begin(); //======================================================

        // dessine le PLAYER,  les MATES et les LAYERS ------------------------
        map.setView(clampedCamera);
        map.renderAllLivingEntitiesAndTiles(player, mates, monstersInstance);

        if (showDebugTexts) debugOnScreen(); // TOUT A LA FIN !!!

        hudManager.drawHUD(batch);

        batch.end(); //========================================================

        if (showJoystick) {
            joystick.render(shapeRenderer);
        }
    }

    private void debugOnScreen() {
        //debugOS.draw("Score AZER AZETGEZA REZA ", 0, 0);
        debugOS.setText(0, mainGame.getGameMode() + " / " + monstersInstance.getMonstersMode());
        debugOS.setText(1, player.getUniqueID() + " / " + player.getNumLobby() + " / " + player.getLobbyPlayerId());
        debugOS.setText(25, "" + System.currentTimeMillis());

//        for (int i = 2; i < DebugOnScreen.MAX_TEXTS; i++)
//            debugOS.setText(i, i + "/" + System.currentTimeMillis());

        debugOS.drawTexts(batch);
    }

    private void submitThreadJobs() {
//        System.out.println("threadPoolExecutor.getActiveCount()   " + threadPoolExecutor.getActiveCount());
//        if (threadPoolExecutor.getActiveCount() < 3) {
////            System.out.println("threadPoolExecutor.getActiveCount()   " + threadPoolExecutor.getActiveCount());
//
//            threadPoolExecutor.submit(retrieveUpdatePlayer);
//            threadPoolExecutor.submit(updatePlayer);
//            threadPoolExecutor.submit(retrieveMate);
//
//        }
        if (threadPoolExecutor0.getActiveCount() < 1) {
            //System.out.println("updatePlayer    RESTART");
            threadPoolExecutor0.submit(updatePlayer);
        }
        if (threadPoolExecutor1.getActiveCount() < 1) {
            //System.out.println("retrieveMate    RESTART");
            threadPoolExecutor1.submit(retrieveMate);
        }
        if (threadPoolExecutor2.getActiveCount() < 1) {
            //System.out.println("retrieveUpdatePlayer    RESTART");
            threadPoolExecutor2.submit(retrieveUpdatePlayer);
        }

        if (threadPoolExecutor3.getActiveCount() < 1) {
            //System.out.println("retrieveMonsters    RESTART");
            threadPoolExecutor3.submit(retrieveMonsters);
        }
        if (player.isMaster() && threadPoolExecutor4.getActiveCount() < 1) {
            //System.out.println("updateMonsters    RESTART");
            threadPoolExecutor4.submit(updateMonsters);
        }

//        retrieveMate = new RetrieveMate(player);
//        threadPoolExecutor1.submit(retrieveMate);
//
//        retrieveUpdatePlayer = new RetrieveUpdatePlayer(player);
//        threadPoolExecutor2.submit(retrieveUpdatePlayer);
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

    public static ClampedCamera getCamera() {
        return clampedCamera;
    }

    private void displayJoystick() {
        if (Gdx.input.isTouched(0)) {
            if (!showJoystick) {
                if (!joystick.isPositionFixe()) {
                    joystick.setPosition(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY());
                }
            }
            showJoystick = true;
        }
        updateJoystick();
    }

    public void updateJoystick() {
        //Vector3 vector = new Vector3();
        //camera.unproject(vector.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        //joystick.update(vector.x, vector.y);

        joystick.update(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY());
    }

    private void movePlayer(int keycode, float deltaTime) {
        if (keycode == Input.Keys.LEFT) {
            movePlayer("LEFT", -sizeOfStep, 0, deltaTime);
        } else if (keycode == Input.Keys.RIGHT) {
            movePlayer("RIGHT", +sizeOfStep, 0, deltaTime);
        } else if (keycode == Input.Keys.UP) {
            movePlayer("UP", 0, +sizeOfStep, deltaTime);
        } else if (keycode == Input.Keys.DOWN) {
            movePlayer("DOWN", 0, -sizeOfStep, deltaTime);
        }
    }

    private void movePlayer(String dirKeyword, int deltaX, int deltaY, float deltaTime) {
        player.move(dirKeyword, deltaX, deltaY, deltaTime);
        clampedCamera.centerOnPlayer();
    }

    //boolean[] keyOns = new boolean[200];
    int lastKeyCode = 999; // pour faire simple ...

    @Override
    public boolean keyDown(int keycode) {
        showJoystick = false;

        switch (keycode) {
            case Input.Keys.ESCAPE:
                showDebugTexts = !showDebugTexts;
                break;
            case Input.Keys.CONTROL_LEFT:
                player.attack();
                break;
            default:
                //keyOns[keycode] = true;
                lastKeyCode = keycode;
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        showJoystick = false;
        //keyOns[keycode] = false;
        lastKeyCode = Input.Keys.PRINT_SCREEN;
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
        GameScreen.cameraZoom = cameraZoom;
    }

    public static Player getPlayer() {
        return player;
    }

    public SpriteBatch getBatch() {
        return batch;
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