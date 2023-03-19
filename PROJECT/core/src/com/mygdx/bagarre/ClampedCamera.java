package com.mygdx.bagarre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.entity.Player;
import com.mygdx.map.Map;

public class ClampedCamera extends OrthographicCamera {
    float CLAMP_X_MIN;
    float CLAMP_X_MAX;
    float CLAMP_Y_MIN;
    float CLAMP_Y_MAX;
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

        float widthZoomed = map.mapPixelsWidth() / zoom;
        float heightZoomed = map.mapPixelsHeight() / zoom;

        if (widthZoomed < viewportWidth)
            CLAMP_X_MIN = CLAMP_X_MAX = map.mapPixelsWidth() / 2; // taille en pixels non zoomée !
        else {
            CLAMP_X_MIN = zoom * viewportWidth / 2; // ex: 256 * 2 = 1024 * 0.5
            CLAMP_X_MAX = map.mapPixelsWidth() - CLAMP_X_MIN;
        }

        if (heightZoomed < viewportHeight)
            CLAMP_Y_MIN = CLAMP_Y_MAX = map.mapPixelsHeight() / 2; // taille en pixels non zoomée !
        else {
            CLAMP_Y_MIN = zoom * viewportHeight / 2; // ex: 192 * 2 = 768 * 0.5
            CLAMP_Y_MAX = map.mapPixelsHeight() - CLAMP_Y_MIN;
        }

//        System.out.println("### clampCamera : CLAMP_X_MIN=" + CLAMP_X_MIN +
//                " / CLAMP_X_MAX=" + CLAMP_X_MAX +
//                " / CLAMP_Y_MIN=" + CLAMP_Y_MIN +
//                " / CLAMP_Y_MAX=" + CLAMP_Y_MAX +
//                " / mpW=" + map.mapPixelsWidth() + " GdxW=" + Gdx.graphics.getWidth() + " WZ=" + widthZoomed +
//                " / mpH=" + map.mapPixelsHeight() + " GdxH=" + Gdx.graphics.getHeight() + " HZ=" + heightZoomed);
    }

    public void centerOnPlayer() {
        position.x = player.getFootX();
        position.y = player.getY() + player.getSprite().getHeight() / 2;

        // clamp camera position ==============================================

        if (position.x < CLAMP_X_MIN) position.x = CLAMP_X_MIN;

        if (position.y < CLAMP_Y_MIN) position.y = CLAMP_Y_MIN;

        if (position.x > CLAMP_X_MAX) position.x = CLAMP_X_MAX;

        if (position.y > CLAMP_Y_MAX) position.y = CLAMP_Y_MAX;

        update();
    }

}
