package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.entity.Player;
import com.mygdx.map.Map;

public class ClampedCamera extends OrthographicCamera {

    int CLAMP_X_MIN;
    int CLAMP_X_MAX;
    int CLAMP_Y_MIN;
    int CLAMP_Y_MAX;
    private final Player player;
    private final Map map;

    public ClampedCamera(Player player, Map map, float zoom) {
        super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.player = player;
        this.zoom = zoom;
        this.map = map;

        this.map.setView(this);
        computeClampedLimits();
        centerOnPlayer();
    }

    private void computeClampedLimits() {
        // ex: res 1024*768 mapW=32*30=960 mapH=32*15=480 zoom=0.5f

        CLAMP_X_MIN = (int) (zoom * viewportWidth / 2); // ex: 256 * 2 = 1024 * 0.5
        CLAMP_X_MAX = (int) (map.mapPixelsWidth() - CLAMP_X_MIN);
        CLAMP_Y_MIN = (int) (zoom * viewportHeight / 2); // ex: 192 * 2 = 768 * 0.5
        CLAMP_Y_MAX = (int) (map.mapPixelsHeight() - CLAMP_Y_MIN);

//        System.out.println("### clampCamera : CLAMP_X_MIN=" + CLAMP_X_MIN +
//                " / CLAMP_X_MAX=" + CLAMP_X_MAX +
//                " / CLAMP_Y_MIN=" + CLAMP_Y_MIN +
//                " / CLAMP_Y_MAX=" + CLAMP_Y_MAX);
    }

    public void centerOnPlayer() {
        position.x = player.getX() + player.getSprite().getWidth() / 2;
        position.y = player.getY() + player.getSprite().getHeight() / 2;

        // clamp camera position ==============================================

        if (position.x < CLAMP_X_MIN) position.x = CLAMP_X_MIN;

        if (position.y < CLAMP_Y_MIN) position.y = CLAMP_Y_MIN;

        if (position.x > CLAMP_X_MAX) position.x = CLAMP_X_MAX;

        if (position.y > CLAMP_Y_MAX) position.y = CLAMP_Y_MAX;

        update();
    }

    public void translateAndCenter() {

    }
}
