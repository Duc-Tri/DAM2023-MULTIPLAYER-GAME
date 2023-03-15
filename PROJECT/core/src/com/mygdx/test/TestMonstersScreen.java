package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.bagarre.ClampedCamera;
import com.mygdx.bagarre.MainGame;
import com.mygdx.client.NewPlayer;
import com.mygdx.entity.Mob;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;
import com.mygdx.input.Joystick;
import com.mygdx.map.Map;

public class TestMonstersScreen extends ApplicationAdapter implements InputProcessor {

    public static Player player; // tout moche ! pour tests uniquement
    private Monsters monsters;
    private boolean showJoystick = false;
    private int refreshValue = 0;
    private int speedOfSprite = 3; //Plus c'est grand plus c'est lent
    private static Map map;
    private static ClampedCamera clampedCamera;
    private Viewport viewport;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private SpriteBatch batch;
    private int sizeOfStep = 8;
    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    private static float cameraZoom = 1; // plus c'est gros, plus on est loin
    private final String mapFilename = "map/DAMCorp_1.tmx";


    public void create() {
        System.out.println("TEST MONSTERS (solo) ################################################");
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        createPlayer();

        map = new Map(mapFilename, batch);
        monsters = new Monsters();
        monsters.setTargetPlayer(player); // ici tous les monstres poursuivent le même joueur

        loadMap(mapFilename, batch);
        Mob.setMap(map);

        shapeRenderer = new ShapeRenderer();

        clampedCamera = new ClampedCamera(player, map, MainGame.runOnDesktop() ? 0.5f : 0.25f);

        joystick = new Joystick(100, 100, MainGame.runOnAndroid() ? 200 : 100);

        batch.setProjectionMatrix(clampedCamera.combined);
    }

    private void createPlayer() {
        player = new Player();
        player.setX(300); // temp
        player.setY(300); // temp
        NewPlayer.requestServer(player);
    }

    private void loadMap(String mapFilename, SpriteBatch sb) {
        map = new Map(mapFilename, sb);
        //map.setView(clampedCamera);
    }

    public static Map getMap() {
        return map;
    }

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render() {

        displayJoystick();

        if (Gdx.input.isTouched(0)) {
            refreshValue++;
            if (refreshValue == speedOfSprite) {
                refreshValue = 0;
                movePlayer(joystick.getDirectionInput());
            }
        } else {
            showJoystick = false;
        }

        //monsters.moveRandomly();
        monsters.moveToPlayer();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(clampedCamera.combined);

        batch.begin(); //======================================================

        // une image test pour situer le 0/0
        //batch.draw(testImage, 0, 0);

        // dessine le PLAYER,  les MATES et les LAYERS ------------------------
        map.setView(clampedCamera);
        map.renderFloor();
        map.renderAllLivingEntitiesAndTiles(player, null, monsters);
        map.renderTop();

        batch.end(); //========================================================


        if (showJoystick) {
            joystick.render(shapeRenderer);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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

        if (map.checkObstacle(player, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        if (deltaX != 0) {
            player.setX(player.getX() + deltaX);
        }

        if (deltaY != 0) {
            player.setY(player.getY() + deltaY);
        }

        clampedCamera.centerOnPlayer();
    }

    @Override
    public boolean keyDown(int keycode) {
        showJoystick = true;
        movePlayer(keycode);
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