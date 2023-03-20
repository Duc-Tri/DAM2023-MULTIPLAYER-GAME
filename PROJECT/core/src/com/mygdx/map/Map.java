package com.mygdx.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.entity.LivingEntity;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;
import com.mygdx.entity.LivingEntityComparator;
import com.mygdx.pathfinding.Vector2int;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//#################################################################################################
// Map: wrapper d'un TiledMap.
// Structure attendue d'une carte (TiledMap) fait avec TILED :
//=================================================================================================
// - un calque OBLIGATOIRE TRIGGERS (qui contient les points de spawn START, EXIT & ENEMY)
//
// - un calque optionnel PLAYERS (tuiles au même niveau que les joueurs)
//
// - des calques optionnels GROUNDxxx (xxx nombre entier >= 0)
//
// - des calques optionnels TOPxxx (xxx nombre entier >= 0)
//
// - un calque optionnel OBSTACLES
//#################################################################################################
public class Map {

    public final static boolean DEBUG_MAP = true; // true = AFFICHE LE LAYER OBSTACLES (=> TOPMAP)
    private final TiledMap tiledMap;
    private final int mapNumberLayers;
    private TiledMap groundMap; // contient que les sols, en dessous des joueurs
    private TiledMap topMap; // contient que les top, au dessus des joueurs
    private final TiledMapTileLayer obstaclesLayer; // calque des tuiles d'obstacles => collisions
    private final TiledMapTileLayer playersLayer; // contient les tuiles au même niveau que les joueurs
    private TiledMapRenderer groundMapRenderer;
    private TiledMapRenderer topMapRenderer;
    private final SpriteBatch batch;
    private List<LivingEntity> livingEntitiesList; // liste des entités à afficher
    public final int mapWidthInTiles;
    public final int mapHeightInTiles;
    public final int tileWidth;
    public final int tileHeight;
    public final String monstersToSpawn;
    private HashMap<String, Rectangle> spawnAreas;

    public Map(String mapFilename, SpriteBatch batch) {
        this.batch = batch;
        livingEntitiesList = new ArrayList<>();

        // Les maps + renderers ===================================================================
        tiledMap = new TmxMapLoader().load(mapFilename);

        // on part du principe que le text contient les noms de monstres à spawn
        monstersToSpawn = readTextFromMapObjectGroup(mapFilename);

        groundMap = new TiledMap();
        groundMapRenderer = new OrthogonalTiledMapRenderer(groundMap);
        topMap = new TiledMap();
        topMapRenderer = new OrthogonalTiledMapRenderer(topMap);

        // Les layers =============================================================================
        playersLayer = (TiledMapTileLayer) getLayerAnyCase("PLAYERS");

        obstaclesLayer = (TiledMapTileLayer) getLayerAnyCase("OBSTACLES");
        obstaclesLayer.setVisible(DEBUG_MAP);
        //printLayer(obstaclesLayer);
        renameLayersToUpperCase();

        // les autres données de la carte =========================================================
        mapNumberLayers = tiledMap.getLayers().size();
        mapWidthInTiles = obstaclesLayer.getWidth();
        mapHeightInTiles = obstaclesLayer.getHeight();
        tileWidth = obstaclesLayer.getTileWidth();
        tileHeight = obstaclesLayer.getTileHeight();

        // Reconstruction des sets de layers (ground / top), triggers ==============================
        constructGroundMap();
        constructTopMap();
        retrieveTriggerObjects();
    }

    private String readTextFromMapObjectGroup(String mapFilename) {
        XmlReader xmlreader = new XmlReader();
        XmlReader.Element rootElement = xmlreader.parse(Gdx.files.internal(mapFilename));

        //System.out.println(rootElement.toString());

        // on prend le 1ER OBJECTGROUP seulement
        String textMessage = rootElement.getChildrenByNameRecursively("objectgroup").first()
                .getChildrenByNameRecursively("text").first().getText();

        /*
        System.out.println("########################## " + textMessage);
        if (monstersToSpawn.toLowerCase().startsWith("spawn monsters"))
            return monstersToSpawn;
        else             return null;
         */

        return textMessage;
    }

    // Assemble les layers
    private void constructTopMap() {

        // si debug, on met les obstacles dans le topMap, pour qu'il soit au dessus de tout
        if (DEBUG_MAP)
            topMap.getLayers().add(obstaclesLayer);
        else
            addLayersToMap("TOP", topMap);
    }

    private void constructGroundMap() {
        if (!DEBUG_MAP) addLayersToMap("GROUND", groundMap);
    }

    private void addLayersToMap(String prefixName, TiledMap map) {
        addLayerToMap(prefixName, map);

        for (int i = 0; i < mapNumberLayers; i++) addLayerToMap(prefixName + i, map);
    }

    private void addLayerToMap(String name, TiledMap map) {
        MapLayer layer = getLayerAnyCase(name);
        if (layer != null)
            map.getLayers().add(layer);
    }

    public void setView(OrthographicCamera camera) {
        groundMapRenderer.setView(camera);
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

    public TiledMapRenderer getGroundMapRenderer() {
        return groundMapRenderer;
    }

    public void setGroundMapRenderer(TiledMapRenderer groundMapRenderer) {
        this.groundMapRenderer = groundMapRenderer;
    }

    public boolean checkObstacle(float pixelX, float pixelY) {
        int tileX = (int) (pixelX / obstaclesLayer.getTileWidth());
        int tileY = (int) (pixelY / obstaclesLayer.getTileHeight());

        return isTileObstacle(tileX, tileY);
    }

    public boolean isTileObstacle(int tileX, int tileY) {
        boolean tilePresent = (obstaclesLayer.getCell(tileX, tileY) != null);

        // System.out.println("isTileObstacle ... " + tileX + "/" + tileY + " --- " + tilePresent);

        return tilePresent;
    }

    private Rectangle hitbox = new Rectangle();

    // Check collision avec la FUTURE position de l'entity
    //---------------------------------------------------------------------------------------------
    public boolean checkObstacle(LivingEntity entity, int deltaX, int deltaY) {
        Rectangle plHb = entity.getHitbox();
        hitbox.set(plHb.x + deltaX, plHb.y + deltaY, plHb.width, plHb.height);

        boolean hitOnCorners = checkObstacle(hitbox.x, hitbox.y) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y) ||
                checkObstacle(hitbox.x, hitbox.y + hitbox.height) ||
                checkObstacle(hitbox.x + hitbox.width, hitbox.y + hitbox.height);

        return hitOnCorners;
    }

    public void renderGround() {
        groundMapRenderer.render();
    }

    public void renderTop() {
        topMapRenderer.render();
    }

    private LivingEntityComparator livingEntityComparator = new LivingEntityComparator();

    public void renderAllLivingEntitiesAndTiles(Player player, Mates mates, Monsters monsters) {

        // Met à jour de la liste de tous les entités
        //-------------------------------------------------
        livingEntitiesList.clear();
        if (monsters != null) livingEntitiesList.addAll(monsters.getDrawMobs());

        if (mates != null) livingEntitiesList.addAll(Mates.getMates());

        if (player != null) livingEntitiesList.add(player);

        if (!DEBUG_MAP) renderGround();

        // on trie les entités selon leur Y, pour faciliter la comparaison avec les tuiles
        Collections.sort(livingEntitiesList, livingEntityComparator);

        int realY = mapHeightInTiles * tileHeight;

        // on commence par afficher le haut -------------------------------------------------------
        for (int tileY = mapHeightInTiles; tileY >= 0; tileY--) {

            // on affiche les sprites qui sont au dessus de ce realY ------------------------------
            for (int ent = livingEntitiesList.size() - 1; ent >= 0; ent--) {
                LivingEntity currentEntity = livingEntitiesList.get(ent);
                if (currentEntity != null && currentEntity.getY() > realY) {
                    currentEntity.drawAndUpdate(batch);
                    livingEntitiesList.remove(currentEntity);
                } else
                    break; // si personne n'est au dessus de ce Y, on sort !
            }

            // affiche les tuiles de cette rangée tileY -------------------------------------------
            int realX = 0;
            if (!DEBUG_MAP && playersLayer != null) {
                for (int tileX = 0; tileX < mapWidthInTiles; tileX++) {
                    Cell c = playersLayer.getCell(tileX, tileY);
                    if (c != null) {
                        batch.draw(c.getTile().getTextureRegion(), realX, realY);
                    }

                    realX += tileWidth;
                }
            }

            realY -= tileHeight; // on "va vers le bas" pour l'affichage
        }

        renderTop(); // on affiche le top
    }

    // Indice tuile => Coordonées pixels
    public Vector2int mapTileToPixels(Vector2int tile) {
        return new Vector2int(tile.x * tileWidth + tileWidth / 2, tile.y * tileHeight + tileHeight / 2);
    }

    // Coordonées pixels => Indice tuile
    public Vector2int pixelsToMapTile(Vector2int pos) {
        return new Vector2int(pos.x / tileWidth, pos.y / tileHeight);
    }

    // Coordonées pixels => Indice tuile
    public Vector2int pixelsToMapTile(float x, float y) {
        return new Vector2int(x / tileWidth, y / tileHeight);
    }

    // Renvoie un MapLayer, on castera si besoin dans l'appelant
    private MapLayer getLayerAnyCase(String layerName) {
        MapLayer layer = tiledMap.getLayers().get(layerName.toUpperCase());
        if (layer == null) {
            layer = tiledMap.getLayers().get(layerName.toLowerCase());
        }

//        System.out.println("getLayerAnyCase ___" + layerName + "_____" +
//                (layer == null ? " <not found>" : layer.getName()));

        return layer;
    }

    private void renameLayersToUpperCase() {
        for (MapLayer layer : tiledMap.getLayers()) {
            layer.setName(layer.getName().toUpperCase());
            // System.out.println("renameLayers ____________________________ " + layer.getName());
        }
    }

    // Charge les rectangles TRIGGERS
    private void retrieveTriggerObjects() {
        // si le calque TRIGGERS n'existe pas , ça plante !!!

        spawnAreas = new HashMap<>();

        MapObjects triggers = getLayerAnyCase("TRIGGERS").getObjects();

        Array<RectangleMapObject> mapRectangles = triggers.getByType(RectangleMapObject.class);
        for (RectangleMapObject area : mapRectangles) {
//            System.out.println("retrieveTriggerObjects ############## rectTrigger=" + r.getName());

            String areaName = area.getName().trim().toUpperCase();
            if (areaName.startsWith("SPAWN_")) {
                spawnAreas.put(areaName, area.getRectangle());
            }

            for (Iterator<String> it = area.getProperties().getKeys(); it.hasNext(); ) {
                String k = it.next();
//                System.out.println("     ========== " + k);
            }
        }

    }

    public String getMonstersToSpawn() {
        return monstersToSpawn;
    }

    // Renvoie la position du centre d'un spawnArea
    //---------------------------------------------------------------------------------------------
    public Vector2int centerPointAtSpawnArea(String spawnArea) {
        Rectangle area = spawnAreas.get(spawnArea.toUpperCase());
        if (area != null)
            return new Vector2int(area.x + area.width / 2, area.y + area.height / 2);

        return new Vector2int(0, 0);
    }

    // Renvoie une position aléatoire à l'intérieur d'un spawnArea
    //---------------------------------------------------------------------------------------------
    public Vector2int randomPointAtSpawnArea(String spawnArea) {
        return new Vector2int(0, 0);
    }

    // imprime dans la console le layer OBSTACLES
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

}

