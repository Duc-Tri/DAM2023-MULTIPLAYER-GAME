package com.mygdx.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

public class Map {
    TiledMap tiledMap;

    TiledMapRenderer tiledMapRenderer;
    public Map(TiledMap tiledMap) {
        this(tiledMap, null);
    }
    public Map(TiledMap tiledMap, TiledMapRenderer tiledMapRenderer) {
        this.tiledMap = tiledMap;
        this.tiledMapRenderer = tiledMapRenderer;
    }


    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }


    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public void setTiledMapRenderer(TiledMapRenderer tiledMapRenderer) {
        this.tiledMapRenderer = tiledMapRenderer;
    }

    public void render() {
        getTiledMapRenderer().render();
    }
}
