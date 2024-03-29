package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.map.CustomTiledMap;
import com.mygdx.pathfinding.Vector2int;

import java.util.ArrayList;
import java.util.List;

public class LabyTest extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture pixelsTexture;

    TiledMap myBloc;
    TiledMap holder;
    TiledMapRenderer blocRenderer;
    TiledMapRenderer testMapRenderer;
    OrthographicCamera camera;
    private CustomTiledMap customTiledMap;
    TextureRegion[][] splitTiles;

    @Override
    public void create() {

        tiles = new Texture("test/tiny_16x16.png");
        splitTiles = TextureRegion.split(tiles, 16, 16);

        createHolderMap();
        createLayers();
        createMyTiledMap();

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 640, 480); // TRUE = yDOWN
        //camera.translate(320, 240);
        camera.update();

        batch = new SpriteBatch();

        myBloc = new TmxMapLoader().load("test/BlocMap/Bloc1.tmx");

        blocRenderer = new OrthogonalTiledMapRenderer(myBloc);

//		img = new Texture("tiny_16x16.png");

    }

    private void createHolderMap() {
//        holder = new TiledMap();
//        holder.getLayers().get(0);
//
//        MapLayer layer =new MapLayer();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        batch.begin();
//		batch.draw(img, 0, 0);
        camera.update();

//        myLayersRenderer.setView(camera);
//        myLayersRenderer.render();
//
//        blocRenderer.setView(camera);
//        blocRenderer.render();

        testMapRenderer.setView(camera);
        testMapRenderer.render();

        if (customTiledMap.solution != null) {
            drawSolutionInPixels(batch);
        }

        batch.end();
    }

    public void drawSolutionInPixels(SpriteBatch batch) {

        for (Vector2int v : customTiledMap.solution) {
            batch.draw(splitTiles[5][0], v.x * 16, v.y * 16);
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
//        img.dispose();
    }

    public void createMyTiledMap() {
        List<String> blocFileNames = new ArrayList<String>() {
            {
                add("test/BlocMap/Bloc1.tmx");
                add("test/BlocMap/Bloc2.tmx");
                add("test/BlocMap/Bloc3.tmx");
                add("test/BlocMap/Bloc4.tmx");
                add("test/BlocMap/Bloc5.tmx");
                add("test/BlocMap/Bloc6.tmx");
                add("test/BlocMap/Bloc7.tmx");
                add("test/BlocMap/Bloc8.tmx");
                add("test/BlocMap/Bloc9.tmx");
            }
        };

        customTiledMap = new CustomTiledMap(blocFileNames, 3, 3);

        testMapRenderer = new OrthogonalTiledMapRenderer(customTiledMap.getTiledMap());
    }

//    ArrayList<String> gfg = new ArrayList<String>() {
//        {
//            add("Geeks");
//            add("for");
//            add("Geeks");
//        }
//    };


    private TiledMap map;
    private TiledMapRenderer myLayersRenderer;

    //    private OrthoCamController cameraController;
    private AssetManager assetManager;
    private Texture tiles;
    private Texture texture;

    //    private BitmapFont font;
    public void createLayers() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.update();

//        cameraController = new OrthoCamController(camera);
//        Gdx.input.setInputProcessor(cameraController);

//        font = new BitmapFont();
//        batch = new SpriteBatch();

        map = new TiledMap(); // NOUVEL OBJET TILEMAP

        MapLayers layers = map.getLayers();
        for (int l = 0; l < 2; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(150, 100, 16, 16);

            for (int x = 0; x < 150; x++) {
                for (int y = 0; y < 100; y++) {
                    int ty = (int) (Math.random() * splitTiles.length);
                    int tx = (int) (Math.random() * splitTiles[ty].length);

                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(splitTiles[ty][tx]));

                    layer.setCell(x, y, cell);
                }
            }
            layers.add(layer);
        }

        myLayersRenderer = new OrthogonalTiledMapRenderer(map);
    }

}
