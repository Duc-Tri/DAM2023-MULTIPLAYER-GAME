package com.mygdx.bagarre;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import com.libgdx.entitygestion.Player;
import com.mygdx.client.ClientNewPlayer;
import com.mygdx.client.ClientRetrieveMates;
import com.mygdx.client.ClientRetrievePlayer;
import com.mygdx.client.ClientRetrieveUpdatePlayer;
import com.mygdx.client.ClientUpdatePlayer;
import com.mygdx.component.Joystick;

import entitygestion.Mates;
import entitygestion.Player;
import com.mygdx.firebase.Firebase;
import com.mygdx.map.Map;


public class Game extends ApplicationAdapter implements InputProcessor {
    boolean display=false;
    int refreshValue = 0;
    int speedOfSprite = 3;//Plus c'est grand plus c'est lent
    Map map;
    OrthographicCamera camera;
    Viewport viewport;
    int SCREEN_WIDTH = 0;
    int SCREEN_HEIGHT = 0;
    SpriteBatch batch;
    TextureAtlas textureAtlas;
    Sprite myPlayerSprite;
    Player myPlayer;
    TextureRegion textureRegion;
    int sizeOfStep = 40;
    int calculatedWidth = 0;
    int calculatedHeight = 0;
    Joystick joystick;
    ShapeRenderer shapeRenderer;

    public static boolean lockOnListReadFromDB = false;
    private Mates[] mates;
//    private Player player2;


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void create() {

        Firebase firebase = new Firebase("https://damcorp-bc7bc-default-rtdb.firebaseio.com/","json/key.json");
        firebase.displayJson();
        firebase.connect();
        firebase.updateUser();
        setSCREEN_WIDTH(Gdx.graphics.getWidth());
        setSCREEN_HEIGHT(Gdx.graphics.getHeight());
        createCamera();
        setViewport(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera));
        setJoystick(new Joystick(100,100,200));
        setShapeRenderer(new ShapeRenderer());
        setMap(createGameMap("sampleMap.tmx"));
        map.render();
        setBatch(new SpriteBatch());
        initializeCharacter();
        calculatedHeight();
        calculatedWidth();
        Gdx.input.setInputProcessor(this);


        //communication avec les serveur pour enrgistrer le joueur
        ClientNewPlayer.newPlayer(myPlayer);

        mates = new Mates[0];

//        this.player2  = ClientRetrievePlayer.retrievePlayer(myPlayer);
//        player2.setY(800f);
//        player2.initializeSprite();



    }

    private Map createGameMap(String s) {
        TiledMap tempTiledMap = new TmxMapLoader().load(s);
        return new Map(tempTiledMap,new OrthogonalTiledMapRenderer(tempTiledMap));
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void calculatedWidth() {
        int heightMap = Integer.parseInt(map.getTiledMap().getProperties().get("height") + "");
        int tileheight = Integer.parseInt(map.getTiledMap().getProperties().get("tileheight") + "");
        calculatedHeight = heightMap * tileheight;
    }

    private void calculatedHeight() {
        int widthMap = Integer.parseInt(map.getTiledMap().getProperties().get("width") + "");
        int tilewidth = Integer.parseInt(map.getTiledMap().getProperties().get("tilewidth") + "");
        calculatedWidth = widthMap * tilewidth;
    }

    private void initializeCharacter() {
        myPlayer = new Player(this);
        myPlayer.initializeSprite();
        myPlayerSprite = myPlayer.getSprite();
        textureAtlas = myPlayer.getTextureAtlas();
        textureRegion = myPlayer.getTextureRegion();
        myPlayerSprite.setPosition(0, 0);
    }



    @Override
    public void render() {
        displayJoystick();
        if(Gdx.input.isTouched(0) ) {
            refreshValue++;
              if(refreshValue==speedOfSprite){


//                  System.out.print("myPlayer x " + myPlayer.getX());
//                  System.out.println("       y " + myPlayer.getY());
//                  System.out.print("player2  x  " + player2.getX());
//                  System.out.println("       y  " + player2.getY());
                refreshValue=0;
                movePlayer(joystick.getDirectionInput());
            }
        }
        else{

            display = false;
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.getTiledMapRenderer().setView(camera);
        map.getTiledMapRenderer().render();
        batch.begin();

     //   System.out.println("player2.getServerUniqueID() " +player2.getServerUniqueID());
//        System.out.println("myPlayer.getFindRegion()"  + myPlayer.getFindRegion());
        ClientUpdatePlayer.updatePlayer(myPlayer);

//        ClientRetrieveUpdatePlayer.updatePlayer(player2);
//            player2.getSprite().draw(batch);

        ClientRetrieveMates.retrieveMate(myPlayer);
        for(int i = 0 ; i < mates.length; i++){
            System.out.println("player getServerUniqueID = "+  myPlayer.getServerUniqueID());
            System.out.println("player X = "+ myPlayer.getX());
            System.out.println("player Y = "+ myPlayer.getY());
            System.out.println("mates["+i+"] getServerUniqueID = "+  mates[i].getServerUniqueID());
            System.out.println("mates["+i+"] X = "+ mates[i].getX());
            System.out.println("mates["+i+"] Y = "+ mates[i].getY());
            ClientRetrieveUpdatePlayer.updatePlayer(mates[i]);
            mates[i].getSprite().draw(batch);
        }
        myPlayerSprite.draw(batch);
        batch.end();
        if(display){
           joystick.render(shapeRenderer);
        }
    }
    private void displayJoystick() {
        if(Gdx.input.isTouched(0)){
            if(!display){
                if(!joystick.isPositionFixe()){
                    joystick.setPosition(Gdx.input.getX(),SCREEN_HEIGHT-Gdx.input.getY());
                }
            }
            display = true;
        }

        update();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        display =true;
        movePlayer(keycode);
        return false;
    }
    private void movePlayer(int keycode) {
        if (keycode == Input.Keys.LEFT) {
           myPlayer.animate("LEFT");
             myPlayer.setX(myPlayer.getX() - sizeOfStep);
            if (myPlayer.getX() < SCREEN_WIDTH * 1.0 / 4.0) {
                if (camera.position.x < SCREEN_WIDTH * 1.0 / 4.0) {
                    if (myPlayer.getX() > 0) {
                        myPlayer.setX(myPlayer.getX() + sizeOfStep);
                    }
                } else {
                    myPlayer.setX(myPlayer.getX() + sizeOfStep);

                    camera.position.x -= sizeOfStep;

                }
            }
        }
        if (keycode == Input.Keys.RIGHT) {
            myPlayer.animate("RIGHT");
            myPlayer.setX(myPlayer.getX() + sizeOfStep);
            if (myPlayer.getX() > SCREEN_WIDTH * 3.0 / 4.0) {
                if (camera.position.x > calculatedWidth - SCREEN_WIDTH * 1.0 / 4.0) {
                    if (myPlayer.getX() < SCREEN_WIDTH) {
                        myPlayer.setX(myPlayer.getX() - sizeOfStep);
                    }
                } else {
                    myPlayer.setX(myPlayer.getX() - sizeOfStep);
                    camera.position.x += sizeOfStep;
                }
            }
        }
        if (keycode == Input.Keys.UP) {
            myPlayer.animate("UP");
            myPlayer.setY(myPlayer.getY() + sizeOfStep);
            if (myPlayer.getY() > SCREEN_HEIGHT * 3.0 / 4.0) {
                if (camera.position.y > calculatedHeight - SCREEN_HEIGHT * 1.0 / 4.0) {
                    if (myPlayer.getY() < SCREEN_HEIGHT) {
                        myPlayer.setY(myPlayer.getY() - sizeOfStep);
                    }
                } else {
                    myPlayer.setY(myPlayer.getY() - sizeOfStep);
                    camera.position.y += sizeOfStep;
                }
            }
        }
        if (keycode == Input.Keys.DOWN) {
            myPlayer.animate("DOWN");
            myPlayer.setY(myPlayer.getY() - sizeOfStep);
            if (myPlayer.getY() < SCREEN_HEIGHT * 1.0 / 4.0) {
                if (camera.position.y < SCREEN_HEIGHT * 1.0 / 4.0) {
                    if (myPlayer.getY() > 0) {
                        myPlayer.setY(myPlayer.getY() + sizeOfStep);
                    }
                } else {
                    myPlayer.setY(myPlayer.getY() + sizeOfStep);
                    camera.position.y -= sizeOfStep;
                }
            }
        }
        batch.begin();
        myPlayerSprite = myPlayer.getSprite();
        myPlayerSprite.draw(batch);
        batch.end();
        camera.update(false);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void dispose(){
        batch.dispose();
        shapeRenderer.dispose();
    }

    public void update(){
        Vector3 vector = new Vector3();
        camera.unproject(vector.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        joystick.update(vector.x, vector.y);

    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getRefreshValue() {
        return refreshValue;
    }

    public void setRefreshValue(int refreshValue) {
        this.refreshValue = refreshValue;
    }

    public int getSpeedOfSprite() {
        return speedOfSprite;
    }

    public void setSpeedOfSprite(int speedOfSprite) {
        this.speedOfSprite = speedOfSprite;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public int getSCREEN_WIDTH() {
        return SCREEN_WIDTH;
    }

    public void setSCREEN_WIDTH(int SCREEN_WIDTH) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
    }

    public int getSCREEN_HEIGHT() {
        return SCREEN_HEIGHT;
    }

    public void setSCREEN_HEIGHT(int SCREEN_HEIGHT) {
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public Sprite getMyPlayerSprite() {
        return myPlayerSprite;
    }

    public void setMyPlayerSprite(Sprite myPlayerSprite) {
        this.myPlayerSprite = myPlayerSprite;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public int getSizeOfStep() {
        return sizeOfStep;
    }

    public void setSizeOfStep(int sizeOfStep) {
        this.sizeOfStep = sizeOfStep;
    }

    public int getCalculatedWidth() {
        return calculatedWidth;
    }

    public void setCalculatedWidth(int calculatedWidth) {
        this.calculatedWidth = calculatedWidth;
    }

    public int getCalculatedHeight() {
        return calculatedHeight;
    }

    public void setCalculatedHeight(int calculatedHeight) {
        this.calculatedHeight = calculatedHeight;
    }

    public Joystick getJoystick() {
        return joystick;
    }

    public void setJoystick(Joystick joystick) {
        this.joystick = joystick;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public static boolean isLockOnListReadFromDB() {
        return lockOnListReadFromDB;
    }

    public static void setLockOnListReadFromDB(boolean lockOnListReadFromDB) {
        Game.lockOnListReadFromDB = lockOnListReadFromDB;
    }
    public Mates[] getMates() {
        return mates;
    }

    public void setMates(Mates[] mates) {
        this.mates = mates;
    }
}