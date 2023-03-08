package com.mygdx.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.Player;


public class Map {
    private final TiledMapTileLayer obstaclesLayer;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    //private TiledMapTileLayer obstaclesLayer;

    public Map(String mapFilename) {
        tiledMap = new TmxMapLoader().load(mapFilename);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        obstaclesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("OBSTACLES");

        printLayer(obstaclesLayer);

    }

    public void setView(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
    }

    public void render() {
        tiledMapRenderer.render();
    }

    public int mapPixelsHeight() {
        int heightMap = Integer.parseInt(tiledMap.getProperties().get("height") + "");
        int tileheight = Integer.parseInt(tiledMap.getProperties().get("tileheight") + "");
        return heightMap * tileheight;
    }

    public int mapPixelsWidth() {
        int widthMap = Integer.parseInt(tiledMap.getProperties().get("width") + "");
        int tilewidth = Integer.parseInt(tiledMap.getProperties().get("tilewidth") + "");
        return widthMap * tilewidth;
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

    private void printLayer(TiledMapTileLayer layer) {
        int layerHeight = layer.getHeight();
        int layerWidth = layer.getWidth();

        String temp = "";
        for (int cellY = layerHeight; cellY >= 0; cellY--) {
            for (int cellX = 0; cellX < layerWidth; cellX++) {

                temp += (layer.getCell(cellX, cellY) == null ? " " : "#");

            }
            temp += "\n";
        }
        System.out.println(temp);
    }

    public boolean checkObstacle(float pixelX, float pixelY) {
        int tileX = (int) (pixelX / obstaclesLayer.getTileWidth());
        int tileY = (int) (pixelY / obstaclesLayer.getTileHeight());

        boolean res = (obstaclesLayer.getCell(tileX, tileY) != null);
        //System.out.println("movePlayer === " + pixelX + "/" + pixelY + " --- " + tileX + "/" + tileY + " --- " + res);

        return res;
    }

    Rectangle hitbox = new Rectangle();

    public boolean checkObstacle(Player player, int deltaX, int deltaY) {
        Rectangle plHb = player.getHitbox();
        hitbox.set(plHb.x+deltaX,plHb.y+deltaY,plHb.width, plHb.height);

        boolean hitOnCorners = checkObstacle(hitbox.x, hitbox.y) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y) ||
                checkObstacle(hitbox.x, hitbox.y + hitbox.height) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y + hitbox.height);

        return hitOnCorners;
    }
}
