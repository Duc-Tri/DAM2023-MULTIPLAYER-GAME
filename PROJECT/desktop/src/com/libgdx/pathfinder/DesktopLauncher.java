package com.libgdx.pathfinder;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.bagarre.GameScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactesAtlasGenerator;
import com.mygdx.graphics.TestCharactersAtlas;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(30);
        config.setTitle("PATHFINDER");
        config.setWindowedMode(1024, 768);

        MainGame.setConfig("desktop");


        // CLIENT + SERVEUR ---------------------------------------------------
        new Lwjgl3Application(MainGame.getInstance(), config);


        // TEST PATHFINDING ---------------------------------------------------
//        new Lwjgl3Application(new PathFinder(), config); // TEST A*


        // TEST LABYRINTHE ----------------------------------------------------
//        new Lwjgl3Application(new LabyTest(), config); // MERGE LABYRINTHE + PATH FINDING


        // TESTS ATLAS PERSONNAGES---------------------------------------------
//        RMXPCharactesAtlasGenerator.printlnAtlas();
//        new Lwjgl3Application(new TestCharactersAtlas(), config);

    }

}