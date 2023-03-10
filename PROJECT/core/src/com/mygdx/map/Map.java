package com.mygdx.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entity.Player;


public class Map {
    private final TiledMapTileLayer obstaclesLayer;
    private final TiledMapTileLayer top1;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    //private TiledMapTileLayer obstaclesLayer;

    public Map(String mapFilename) {
        tiledMap = new TmxMapLoader().load(mapFilename);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        obstaclesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("OBSTACLES");
        obstaclesLayer.setVisible(false);
        top1 = (TiledMapTileLayer) tiledMap.getLayers().get("top1");

        // retrieve TRIGGERS rectangles -----------------------------------------------------------
        Array<RectangleMapObject> mapRectangles = tiledMap.getLayers().get("TRIGGERS").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject r : mapRectangles) {
            System.out.println(r.getName());
        }

        printLayer(obstaclesLayer);
    }

    public void setView(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
    }

    public void render() {

//        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("top1"));

        tiledMapRenderer.renderTileLayer(top1);
    }

    public int mapPixelsHeight() {
        int heightMap = obstaclesLayer.getHeight();
        int tileheight = obstaclesLayer.getTileHeight();
        return heightMap * tileheight;
    }

    public TiledMapTileLayer getObstaclesLayer() {
        return obstaclesLayer;
    }

    public int mapPixelsWidth() {
        int widthMap = obstaclesLayer.getWidth();
        int tilewidth = obstaclesLayer.getTileWidth();
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
        hitbox.set(plHb.x + deltaX, plHb.y + deltaY, plHb.width, plHb.height);

        boolean hitOnCorners = checkObstacle(hitbox.x, hitbox.y) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y) ||
                checkObstacle(hitbox.x, hitbox.y + hitbox.height) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y + hitbox.height);

        return hitOnCorners;
    }
}
