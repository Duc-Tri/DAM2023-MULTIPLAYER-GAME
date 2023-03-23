package com.mygdx.bagarre;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.entity.Mob;
import com.mygdx.entity.Player;
import com.mygdx.map.Map;

public class MainGame extends Game {

    // CONSTANTES DU JEU ==========================================================================
//    public final static String URLServer = "http://localhost:8080/DAMCorp/"; // marche UNIQUEMENT en DESKTOP
//    public final static String URLServer = "http://192.168.1.101:8080/DAMCorp/"; // tri maison 1
    public final static String URLServer = "http://172.16.200.105:8080/DAMCorp/"; // tri greta

    //    public final static String URLServer = "http://91.161.85.206:49153/DAMCorp/"; // philippe maison
//     public final static String URLServer = "http://192.168.0.49:6565/DAMCorp/";
    //---------------------------------------------------------------------------------------------
    private final static String mapFilename = "map/DAMCorp_0.tmx"; //"map/DAMCorp_test.tmx";
    private final static String firebaseURL = "https://damcorp-bc7bc-default-rtdb.firebaseio.com/";

    //==============================================================================================

    public enum GameMode {SOLO, MULTIPLAYER, BRAWLER}

    private String config; // "android" or "desktop";
    private GameMode gameMode;
    private GameScreen gameScreen;
    private WelcomeScreen welcomeScreen;
    private static MainGame instance;

    public static MainGame getInstance() {
        if (instance == null) instance = new MainGame();

        return instance;
    }

    // private = singletion design pattern
    private MainGame() {
//        gameMode = GameMode.SOLO;
        gameMode = GameMode.MULTIPLAYER;
//        gameMode = null;
    }

    @Override
    public void create() {
        //FirebaseHelper firebaseHelper = new FirebaseHelper(firebaseURL);

        // chargement des atlas ici, ce qui évite certains bugs par la suite...
        Mob.allMonstersAtlas = new TextureAtlas(Gdx.files.internal(Mob.MONSTERS_ATLAS));
        Player.allPlayersAtlas = new TextureAtlas(Gdx.files.internal(Player.PLAYERS_ATLAS));

        if (runOnDesktop()) {
            showGameScreen(GameMode.MULTIPLAYER);
        } else {
            welcomeScreen = new WelcomeScreen(this);
            setScreen(welcomeScreen);
        }

        //Gdx.input.setInputProcessor(lobbiesScreen); // NON !
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
    public void dispose() {
        //gameScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        //gameScreen.resize(width, height);
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

    public void showGameScreen(GameMode mode) {

        if (welcomeScreen != null)
            welcomeScreen.dispose();

        gameMode = mode;
        gameScreen = new GameScreen(mapFilename, this);
        setScreen(gameScreen);
        Gdx.input.setInputProcessor(gameScreen);
    }

}