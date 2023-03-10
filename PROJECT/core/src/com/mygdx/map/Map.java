package com.mygdx.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Player;
import com.mygdx.entity.PlayerComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Map {
    private final TiledMapTileLayer obstaclesLayer;
    private final TiledMapTileLayer playersLayer;
    private TiledMap tiledMap;
    private TiledMap floorMap; // contient que les sols, en dessous des joueurs
    private TiledMap topMap; // contient que les top, au dessus des joueurs
    public TiledMapRenderer floorMapRenderer;
    private TiledMapRenderer topMapRenderer;
    private final SpriteBatch batch;
    private List<Player> playersList;
    private final int mapWidthInsTiles;
    private final int mapHeightInsTiles;
    private final int mapTileWidth;
    private final int mapTileHeight;


    public Map(String mapFilename, SpriteBatch batch) {
        tiledMap = new TmxMapLoader().load(mapFilename);

        floorMap = new TiledMap();
        topMap = new TiledMap();
        floorMapRenderer = new OrthogonalTiledMapRenderer(floorMap);
        topMapRenderer = new OrthogonalTiledMapRenderer(topMap);

        this.batch = batch;

        obstaclesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("OBSTACLES");
        obstaclesLayer.setVisible(false);
        playersLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PLAYERS");

        mapWidthInsTiles = obstaclesLayer.getWidth();
        mapHeightInsTiles = obstaclesLayer.getHeight();
        mapTileWidth = obstaclesLayer.getTileWidth();
        mapTileHeight = obstaclesLayer.getTileHeight();

        constructFloorMap();
        constructTopMap();

        // retrieve TRIGGERS rectangles -----------------------------------------------------------
        Array<RectangleMapObject> mapRectangles = tiledMap.getLayers().get("TRIGGERS").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject r : mapRectangles) {
            System.out.println(r.getName());
        }

        printLayer(obstaclesLayer);
    }

    private void constructTopMap() {
        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("top" + i);
            if (floorLayer != null)
                topMap.getLayers().add(floorLayer);
        }
    }

    private void constructFloorMap() {
        for (int i = 0; i < 4; i++) {
            TiledMapTileLayer floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("sol" + i);
            if (floorLayer != null)
                floorMap.getLayers().add(floorLayer);
        }
    }

    public void setView(OrthographicCamera camera) {
        floorMapRenderer.setView(camera);
        topMapRenderer.setView(camera);
    }

    public void render() {
//        floorMapRenderer.render();
//        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get(0));

//        return;

        /*
        if (batch == null || tiledMap == null || GameScreen.getCamera() == null)
            return;

//        batch.begin();
//        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("top1"));

//        tiledMapRenderer.setView(GameScreen.getCamera());
        if (top1 != null)
            tiledMapRenderer.renderTileLayer(top1);

//        batch.end();

         */
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

    public TiledMapRenderer getFloorMapRenderer() {
        return floorMapRenderer;
    }

    public void setFloorMapRenderer(TiledMapRenderer floorMapRenderer) {
        this.floorMapRenderer = floorMapRenderer;
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

    public void renderFloor() {
        floorMapRenderer.render();
    }

    PlayerComparator playerComparator = new PlayerComparator();

    public void renderPlayersAndTiles(Player player, Mates mates) {
//        if (obstaclesLayer == null)            return;

        playersList = new ArrayList<>();
        playersList.add(player);
        playersList.addAll(Mates.getMates());

        Collections.sort(playersList, playerComparator);

//        for (Player p : playersList) p.drawAndUpdate(batch);

        //topMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get(0));
        //render();

        TiledMapTileLayer t = ((TiledMapTileLayer) (tiledMap.getLayers().get(3)));


        int realY = mapHeightInsTiles * mapTileHeight;

        // on commence par afficher le haut ! -----------------------------------------------------
        for (int tileY = mapHeightInsTiles; tileY >= 0; tileY--) {

            // affiche les sprites qui sont au dessus de ce realY ---------------------------------
            for (int pi = playersList.size() - 1; pi >= 0; pi--) {
                Player currentPlayer = playersList.get(pi);
                if (currentPlayer.getY() > realY) {
                    currentPlayer.drawAndUpdate(batch);
                    playersList.remove(currentPlayer);
                } else break;
            }

            int realX = 0;
            for (int tileX = 0; tileX < mapWidthInsTiles; tileX++) {
                Cell c = playersLayer.getCell(tileX, tileY);
                if (c != null)
                    batch.draw(c.getTile().getTextureRegion(), realX, realY);

                realX += mapTileWidth;
            }

            realY -= mapTileHeight;
        }

    }

    public void renderTop() {

        topMapRenderer.render();

    }

}

