package com.mygdx.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

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

    public Map(String mapFilename) {
        tiledMap = new TmxMapLoader().load(mapFilename);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
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

    public int calculatedHeight() {
        int heightMap = Integer.parseInt(tiledMap.getProperties().get("height") + "");
        int tileheight = Integer.parseInt(tiledMap.getProperties().get("tileheight") + "");
        return heightMap * tileheight;
    }

    public int calculatedWidth() {
        int widthMap = Integer.parseInt(tiledMap.getProperties().get("width") + "");
        int tilewidth = Integer.parseInt(tiledMap.getProperties().get("tilewidth") + "");
        return widthMap * tilewidth;
    }

}
