package com.mygdx.bagarre;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.client.NewPlayer;
import com.mygdx.entity.Player;
import com.mygdx.firebase.FirebaseHelper;
import com.mygdx.map.Map;

public class MainGame extends Game {

    // CONSTANTES DU JEU ==========================================================================/
//    public final static String URLServer = "http://localhost:8080/DAMCorp/"; // marche UNIQUEMENT en DESKTOP
//    public final static String URLServer = "http://192.168.1.101:8080/DAMCorp/"; // tri maison 1
    public final static String URLServer = "http://172.16.200.237:8080/DAMCorp/"; // tri maison 2

//    public final static String URLServer = "http://172.16.200.105:8080/DAMCorp/";

    //    public final static String URLServer = "http://91.161.85.206:49153/DAMCorp/"; // philippe maison
//     public final static String URLServer = "http://192.168.0.49:6565/DAMCorp/";
    //---------------------------------------------------------------------------------------------
    private final static String mapFilename = "map/DAMCorp_1.tmx"; //"map/DAMCorp_test.tmx";
    private final static String firebaseURL = "https://damcorp-bc7bc-default-rtdb.firebaseio.com/";
    public final static String PLAYERS_ATLAS = "characters/RMXP_humans.atlas";
    public final static String MONSTERS_ATLAS = "characters/RMXP_monsters.atlas";

    //==============================================================================================

    public enum GameMode {SOLO, MULTIPLAYER, BRAWLER}

    private String config; // "android" or "desktop";
    private final GameMode gameMode;
    private GameScreen gameScreen;
    private static MainGame instance;

    public static MainGame getInstance() {
        if (instance == null) instance = new MainGame();

        return instance;
    }

    private MainGame() {
        // private = singletion design pattern

//        gameMode = GameMode.SOLO;
        gameMode = GameMode.MULTIPLAYER;
    }

    public void setConfig(String c) {
        config = c;
    }

    public boolean runOnAndroid() {
        return config.equalsIgnoreCase("android");
    }

    public boolean runOnDesktop() {
        return config.equalsIgnoreCase("Desktop");
    }

    @Override
    public void create() {
        FirebaseHelper firebaseHelper = new FirebaseHelper(firebaseURL);
        gameScreen = new GameScreen(mapFilename, this);
        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }

    public Map getMap() {
        return GameScreen.getMap();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public boolean isSoloGameMode() {
        return gameMode == GameMode.SOLO;
    }

    public boolean isMultiplayerGameMode() {
        return gameMode == GameMode.MULTIPLAYER;
    }

    public boolean isBrawlerGameMode() {
        return gameMode == GameMode.BRAWLER;
    }

}