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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Player;
import com.mygdx.input.Joystick;
import com.mygdx.map.Map;

public class GameScreen implements Screen, InputProcessor {

    private final Player player;
    private Mates mates;
    private boolean showJoystick = false;
    private int refreshValue = 0;
    private int speedOfSprite = 3; //Plus c'est grand plus c'est lent
    private static Map map;
    private static OrthographicCamera camera;
    private Viewport viewport;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private SpriteBatch batch;
    private int sizeOfStep = 8;

    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    int mapPixelsWidth = 0;
    int mapPixelsHeight = 0;

    public static boolean lockOnListReadFromDB = false;

    private final static String testImageFile = "test/kenney_tinytown.png";
    private Texture testImage = new Texture(testImageFile);

    private static float cameraZoom = 1; // plus c'est gros, plus on est loin

    public GameScreen(String mapFilename, Player pl) {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        player = pl;
        mates = new Mates(player);

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.zoom = MainGame.runOnDesktop() ? 0.5f : 0.25f; //cameraZoom;
        camera.position.set(player.getPlayerX(), player.getPlayerY(), 0);
        camera.update();

        shapeRenderer = new ShapeRenderer();

        map = new Map(mapFilename);
        map.setView(camera);
        map.render();
        mapPixelsHeight = map.mapPixelsHeight();
        mapPixelsWidth = map.mapPixelsWidth();

        joystick = new Joystick(100, 100, MainGame.runOnAndroid() ? 200 : 100);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public static Map getMap() {
        return map;
    }

    @Override
    // deltaTime = temps depuis la dernière frame
    public void render(float deltaTime) {
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

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);

        batch.begin(); //======================================================

        // une image test pour situer le 0/0
        //batch.draw(testImage, 0, 0);

        map.setView(camera);
        map.render();

        // dessine le player et les mates -------

        player.drawAndUpdate(batch);
        mates.drawAndUpdate(batch);

        batch.end(); //========================================================
        // player.debug(shapeRenderer);


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
        return camera;
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

        if (MainGame.getMap().checkObstacle(player, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        player.animate(dirKeyword);
        if (deltaX != 0) {

            player.setPlayerX(player.getPlayerX() + deltaX);
            camera.position.x = player.getPlayerX();

        }

        if (deltaY != 0) {
            player.setPlayerY(player.getPlayerY() + deltaY);
            camera.position.y = player.getPlayerY();
        }

        camera.update();

        /*

            player.animate("LEFT");
            player.setX(player.getX() - sizeOfStep);
            if (player.getX() < SCREEN_WIDTH * 1.0 / 4.0) {
                if (camera.position.x < SCREEN_WIDTH * 1.0 / 4.0) {
                    if (player.getX() > 0) {
                        player.setX(player.getX() + sizeOfStep);
                    }
                } else {
                    player.setX(player.getX() + sizeOfStep);
                    camera.position.x -= sizeOfStep;
                }
            }


                    player.animate("RIGHT");
            player.setX(player.getX() + sizeOfStep);
            if (player.getX() > SCREEN_WIDTH * 3.0 / 4.0) {
                if (camera.position.x > calculatedWidth - SCREEN_WIDTH * 1.0 / 4.0) {
                    if (player.getX() < SCREEN_WIDTH) {
                        player.setX(player.getX() - sizeOfStep);
                    }
                } else {
                    player.setX(player.getX() - sizeOfStep);
                    camera.position.x += sizeOfStep;
                }
            }

            player.animate("UP");
            player.setY(player.getY() + sizeOfStep);
            if (player.getY() > SCREEN_HEIGHT * 3.0 / 4.0) {
                if (camera.position.y > calculatedHeight - SCREEN_HEIGHT * 1.0 / 4.0) {
                    if (player.getY() < SCREEN_HEIGHT) {
                        player.setY(player.getY() - sizeOfStep);
                    }
                } else {
                    player.setY(player.getY() - sizeOfStep);
                    camera.position.y += sizeOfStep;
                }
            }

                    player.animate("DOWN");
            player.setY(player.getY() - sizeOfStep);
            if (player.getY() < SCREEN_HEIGHT * 1.0 / 4.0) {
                if (camera.position.y < SCREEN_HEIGHT * 1.0 / 4.0) {
                    if (player.getY() > 0) {
                        player.setY(player.getY() + sizeOfStep);
                    }
                } else {
                    player.setY(player.getY() + sizeOfStep);
                    camera.position.y -= sizeOfStep;
                }
            }

         */
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

    public static void setCameraZoom(float cameraZoom) {
        GameScreen.cameraZoom = cameraZoom;
    }

}

/*

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getRefreshValue() {
        return refreshValue;
    }

    public void setRefreshValue(int refreshValue) {
        this.refreshValue = refreshValue;
    }

    public int getSpeedOfSprite() {
        return speedOfSprite;
    }

    public void setSpeedOfSprite(int speedOfSprite) {
        this.speedOfSprite = speedOfSprite;
    }

    public Map getMap() {
        return map;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public int getSizeOfStep() {
        return sizeOfStep;
    }

    public void setSizeOfStep(int sizeOfStep) {
        this.sizeOfStep = sizeOfStep;
    }

    public static boolean isLockOnListReadFromDB() {
        return lockOnListReadFromDB;
    }

    public static void setLockOnListReadFromDB(boolean lockOnListReadFromDB) {
        MainGame.lockOnListReadFromDB = lockOnListReadFromDB;
    }

 */