package com.mygdx.bagarre;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.client.NewPlayer;
import com.mygdx.entity.Player;

public class MainGame extends Game {

    // CONSTANTES DU JEU ==========================================================================
//    public final static String URLServer = "http://localhost:8080/DAMCorp/"; // marche UNIQUEMENT en DESKTOP
    public final static String URLServer = "http://192.168.42.21:8080/DAMCorp/"; // tri maison

//    public final static String URLServer = "http://172.16.200.104:8080/DAMCorp/"; // mathias greta

//    public final static String URLServer = "http://91.161.85.206:49153/DAMCorp/"; // philippe maison

    //---------------------------------------------------------------------------------------------
    private final static String mapFilename = "map/DAMCorp_1.tmx"; //"map/DAMCorp_test.tmx";

    private final static String firebaseURL = "https://damcorp-bc7bc-default-rtdb.firebaseio.com/";
    //==============================================================================================

    private static String config; // "android" or "desktop";
    GameScreen gameScreen;

    Player player;

    private static MainGame instance;

    public static MainGame getInstance() {
        if (instance == null) instance = new MainGame();

        return instance;
    }

    private MainGame() {
        // SINGLETION design pattern
    }

    public static void setConfig(String c) {
        config = c;
    }

    public static boolean runOnAndroid() {
        return config.equalsIgnoreCase("android");
    }

    public static boolean runOnDesktop() {
        return config.equalsIgnoreCase("Desktop");
    }

    @Override
    public void create() {
        player = new Player();
        NewPlayer.requestServer(player);

        gameScreen = new GameScreen(mapFilename, player);
        setScreen(gameScreen);

        Gdx.input.setInputProcessor(gameScreen);

//        System.out.println("Gdx.graphics.getWidth() " + Gdx.graphics.getWidth());
//        System.out.println("Gdx.graphics.getWidth() " + Gdx.graphics.getHeight());

        //setViewport(new FitViewport(100, 100, camera));
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

}