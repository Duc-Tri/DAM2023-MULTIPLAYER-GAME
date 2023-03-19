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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.client.NewPlayer;
import com.mygdx.client.RetrieveMate;
import com.mygdx.client.RetrieveUpdatePlayer;
import com.mygdx.client.UpdatePlayer;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;
import com.mygdx.input.Joystick;
import com.mygdx.map.Map;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class GameScreen implements Screen, InputProcessor {

    private static Player player; // main player
    private final MainGame mainGame;
    private Mates mates;
    private Monsters monsters;
    private boolean showJoystick = false;
    private static Map map;
    private static ClampedCamera clampedCamera;
    private Viewport viewport;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private SpriteBatch batch;
    private int sizeOfStep = 8;
    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    public static boolean lockOnListReadFromDB = false;

    private final static String testImageFile = "test/kenney_tinytown.png";
    private Texture testImage = new Texture(testImageFile);

    private static float cameraZoom = 1; // plus c'est gros, plus on est loin
    private DebugOnScreen debugOS;

    int threadPoolSize = 15;
    ThreadPoolExecutor threadPoolExecutor0 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    ThreadPoolExecutor threadPoolExecutor2 = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    UpdatePlayer updatePlayer;
    RetrieveMate retrieveMate;
    RetrieveUpdatePlayer retrieveUpdatePlayer;

    public GameScreen(String mapFilename, MainGame game) {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        mainGame = game;
        createPlayer();
        mates = new Mates(player);

        batch = new SpriteBatch();

        map = loadMap(mapFilename, batch);

        shapeRenderer = new ShapeRenderer();

        clampedCamera = new ClampedCamera(player, map, MainGame.getInstance().runOnDesktop() ? 1f : 0.25f);

        joystick = new Joystick(100, 100, MainGame.getInstance().runOnAndroid() ? 200 : 100);

        batch.setProjectionMatrix(clampedCamera.combined);
        debugOS = new DebugOnScreen(batch,clampedCamera);

        monsters = new Monsters(map);
        if (MainGame.getInstance().isSoloGameMode()) {
            monsters.spawnMonsters(map.getMonstersToSpawn());
            monsters.setTargetPlayer(player); // ici tous les monstres poursuivent le même joueur
        }

        createThreadsPool();
    }

    private void createPlayer() {
        player = new Player();
        player.setX(100); // temp
        player.setY(100); // temp
        NewPlayer.requestServer(player);
        System.out.println();
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
    }

    public static Map getMap() {
        return map;
    }

    public float currentTime = 0;
    public final float PLAYER_MOVE_DELAY = 0.05f; // en secondes

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render(float deltaTime) {

        if (mainGame.isMultiplayerGameMode())
            submitThreadJobs();

        displayJoystick();

        currentTime += deltaTime;
        if (currentTime > PLAYER_MOVE_DELAY) {
            currentTime = currentTime - PLAYER_MOVE_DELAY;

            if (Gdx.input.isTouched(0))
                movePlayer(joystick.getDirectionInput());
            else {
                showJoystick = false;
                movePlayer(lastKeyCode);
            }
        }

        monsters.moveToPlayer(deltaTime);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(clampedCamera.combined);

        batch.begin(); //======================================================

        // une image test pour situer le 0/0
        //batch.draw(testImage, 0, 0);

        // dessine le PLAYER,  les MATES et les LAYERS ------------------------
        map.setView(clampedCamera);
        map.renderAllLivingEntitiesAndTiles(player, mates, monsters);

        debugOnScreen(); // TOUT A LA FIN !!!

        batch.end(); //========================================================


        if (showJoystick) {
            joystick.render(shapeRenderer);
        }
    }

    private void debugOnScreen() {
        debugOS.setText(0, player.getUniqueID());
        debugOS.setText(1, "" + System.currentTimeMillis());
        debugOS.drawTexts();
        //debugOS.draw("Score AZER AZETGEZA REZA ", 0, 0);
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
            System.out.println("updatePlayer    RESTART");
            threadPoolExecutor0.submit(updatePlayer);
        }
        if (threadPoolExecutor1.getActiveCount() < 1) {
            System.out.println("retrieveMate    RESTART");
            threadPoolExecutor1.submit(retrieveMate);
        }
        if (threadPoolExecutor2.getActiveCount() < 1) {
            //System.out.println("retrieveUpdatePlayer    RESTART");
            threadPoolExecutor2.submit(retrieveUpdatePlayer);
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

    public static OrthographicCamera getCamera() {
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

    private void movePlayer(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            movePlayer("LEFT", -sizeOfStep, 0);
        } else if (keycode == Input.Keys.RIGHT) {
            movePlayer("RIGHT", +sizeOfStep, 0);
        } else if (keycode == Input.Keys.UP) {
            movePlayer("UP", 0, +sizeOfStep);
        } else if (keycode == Input.Keys.DOWN) {
            movePlayer("DOWN", 0, -sizeOfStep);
        }
    }

    private void movePlayer(String dirKeyword, int deltaX, int deltaY) {

        player.animate(dirKeyword);

        if (MainGame.getInstance().getMap().checkObstacle(player, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        if (deltaX != 0) {
            player.setX(player.getX() + deltaX);
        }

        if (deltaY != 0) {
            player.setY(player.getY() + deltaY);
        }

        clampedCamera.centerOnPlayer();

    }

    //boolean[] keyOns = new boolean[200];
    int lastKeyCode = Input.Keys.PRINT_SCREEN; // pour faire simple ...

    @Override
    public boolean keyDown(int keycode) {
        showJoystick = false;
        //keyOns[keycode] = true;
        lastKeyCode = keycode;
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

}

/*

    public Map getMap() {
        return map;
    }

    public static boolean isLockOnListReadFromDB() {
        return lockOnListReadFromDB;
    }

    public static void setLockOnListReadFromDB(boolean lockOnListReadFromDB) {
        MainGame.lockOnListReadFromDB = lockOnListReadFromDB;
    }

 */