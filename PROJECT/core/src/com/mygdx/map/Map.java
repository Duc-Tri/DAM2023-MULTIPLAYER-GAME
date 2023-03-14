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
    private final TiledMap tiledMap;
    private final int mapNumberLayers;
    private TiledMap floorMap; // contient que les sols, en dessous des joueurs
    private TiledMap topMap; // contient que les top, au dessus des joueurs
    private final TiledMapTileLayer obstaclesLayer; // tuiles d'obstacles
    private final TiledMapTileLayer playersLayer; // contient les tuiles au même niveau que les joueurs
    private TiledMapRenderer floorMapRenderer;
    private TiledMapRenderer topMapRenderer;
    private final SpriteBatch batch;
    private List<Player> playersList;
    private final int mapWidthInTiles;
    private final int mapHeightInTiles;
    private final int tileWidth;
    private final int tileHeight;

    private PlayerComparator playerComparator = new PlayerComparator();
    private Rectangle hitbox = new Rectangle();

    public Map(String mapFilename, SpriteBatch batch) {
        tiledMap = new TmxMapLoader().load(mapFilename);

        floorMap = new TiledMap();
        topMap = new TiledMap();
        floorMapRenderer = new OrthogonalTiledMapRenderer(floorMap);
        topMapRenderer = new OrthogonalTiledMapRenderer(topMap);

        this.batch = batch;

        playersLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PLAYERS");

        obstaclesLayer = (TiledMapTileLayer) tiledMap.getLayers().get("OBSTACLES");
        obstaclesLayer.setVisible(false);
        printLayer(obstaclesLayer);

        mapWidthInTiles = obstaclesLayer.getWidth();
        mapHeightInTiles = obstaclesLayer.getHeight();
        tileWidth = obstaclesLayer.getTileWidth();
        tileHeight = obstaclesLayer.getTileHeight();
        mapNumberLayers = tiledMap.getLayers().size();

        constructFloorMap();
        constructTopMap();

        playersList = new ArrayList<>();

        // retrieve TRIGGERS rectangles -----------------------------------------------------------
        Array<RectangleMapObject> mapRectangles = tiledMap.getLayers().get("TRIGGERS").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject r : mapRectangles)
            System.out.println(r.getName());
    }

    private void constructTopMap() {
        for (int i = 0; i < mapNumberLayers; i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("top" + i); // get(i) ne marche pas !!!
            if (layer != null)
                topMap.getLayers().add(layer);
        }
    }

    private void constructFloorMap() {
        for (int i = 0; i < mapNumberLayers; i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("floor" + i); // get(i) ne marche pas !!!
            if (layer != null)
                floorMap.getLayers().add(layer);
        }
    }

    public void setView(OrthographicCamera camera) {
        floorMapRenderer.setView(camera);
        topMapRenderer.setView(camera);
    }

    public int mapPixelsHeight() {
        return mapHeightInTiles * tileHeight;
    }

    public TiledMapTileLayer getObstaclesLayer() {
        return obstaclesLayer;
    }

    public int mapPixelsWidth() {
        return mapWidthInTiles * tileWidth;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
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

    public void renderTop() {
        topMapRenderer.render();
    }

    public void renderAllPlayersAndTiles(Player player, Mates mates) {

        playersList.clear();
        playersList.add(player);
        playersList.addAll(Mates.getMates());

        // ON FAIT LE RENDU DES TUILES EN MÊME TEMPS QUE LES JOUEURS, EN TRIANT SUR LEUR Y

        Collections.sort(playersList, playerComparator);

        int realY = mapHeightInTiles * tileHeight;

        // on commence par afficher le haut ! -----------------------------------------------------
        for (int tileY = mapHeightInTiles; tileY >= 0; tileY--) {

            // affiche les sprites qui sont au dessus de ce realY ---------------------------------
            for (int pi = playersList.size() - 1; pi >= 0; pi--) {
                Player currentPlayer = playersList.get(pi);
                if (currentPlayer != null && currentPlayer.getY() > realY) {
                    currentPlayer.drawAndUpdate(batch);
                    playersList.remove(currentPlayer);
                } else
                    break;
            }

            // affiche les tuiles de cette rangée tileY -------------------------------------------
            int realX = 0;

            for (int tileX = 0; tileX < mapWidthInTiles; tileX++) {
                Cell c = playersLayer.getCell(tileX, tileY);
                if (c != null) {
                    batch.draw(c.getTile().getTextureRegion(), realX, realY);
                }

                realX += tileWidth;
            }

            realY -= tileHeight;
        }

    }

    public void render() {
        // obsolete
    }
}

