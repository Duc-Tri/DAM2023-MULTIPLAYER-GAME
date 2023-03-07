package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.client.RetrieveMate;
import com.mygdx.client.RetrievePlayer;
import com.mygdx.client.RetrieveUpdatePlayer;
import com.mygdx.client.UpdatePlayer;
import com.mygdx.entity.Mate;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Player;
import com.mygdx.input.Joystick;
import com.mygdx.map.Map;

import java.util.ArrayList;

public class GameScreen implements Screen, InputProcessor {

    private final Player player;
    private Mates mates;
    private boolean display = false;
    private int refreshValue = 0;
    private int speedOfSprite = 1;//Plus c'est grand plus c'est lent
    private Map map;
    private static OrthographicCamera camera;
    private Viewport viewport;
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    private SpriteBatch batch;
    private int sizeOfStep = 10;

    private Joystick joystick;
    private ShapeRenderer shapeRenderer;

    int calculatedWidth = 0;
    int calculatedHeight = 0;

    public static boolean lockOnListReadFromDB = false;

    public GameScreen(String mapFilename, Player pl) {
        player = pl;
        mates = new Mates(player);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        map = new Map(mapFilename);
        map.render();
        calculatedHeight = map.calculatedHeight();
        calculatedWidth = map.calculatedWidth();

        joystick = new Joystick(100, 100, 200);

        batch = new SpriteBatch();
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
            display = false;
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.getTiledMapRenderer().setView(camera);
        map.getTiledMapRenderer().render();

        batch.begin();

        // dessine le player et les mates -------------------------------------
        player.drawAndUpdate(batch);
        mates.drawAndUpdate(batch);

        batch.end();

        if (display) {
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
            if (!display) {
                if (!joystick.isPositionFixe()) {
                    joystick.setPosition(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY());
                }
            }
            display = true;
        }
        updateJoystick();
    }

    public void updateJoystick() {
        Vector3 vector = new Vector3();
        camera.unproject(vector.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        joystick.update(vector.x, vector.y);
    }

    private void movePlayer(int keycode) {
        if (keycode == Input.Keys.LEFT) {
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
        }
        if (keycode == Input.Keys.RIGHT) {
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
        }
        if (keycode == Input.Keys.UP) {
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
        }
        if (keycode == Input.Keys.DOWN) {
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
        }
        batch.begin();
        player.drawAndUpdate(batch);
        batch.end();
        camera.update(false);
    }

    @Override
    public boolean keyDown(int keycode) {
        display = true;
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