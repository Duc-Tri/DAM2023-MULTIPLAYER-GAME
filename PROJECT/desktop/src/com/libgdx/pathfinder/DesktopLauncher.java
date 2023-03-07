package com.libgdx.pathfinder;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.bagarre.MainGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(30);
        config.setTitle("PATHFINDER");
        config.setWindowedMode(640, 480);

        // CLIENT + SERVEUR ---------------------------------------------------
        new Lwjgl3Application(MainGame.getInstance(), config);

        com.badlogic.gdx.Game g;

        // TEST PATHFINDING ---------------------------------------------------
//        new Lwjgl3Application(new PathFinder(), config); // TEST A*


        // TEST LABYRINTHE ----------------------------------------------------
//        new Lwjgl3Application(new LabyTest(), config); // MERGE LABYRINTHE + PATH FINDING


        // TESTS ATLAS PERSONNAGES---------------------------------------------
//        RMXPCharactesAtlasGenerator.printlnAtlas();
//        new Lwjgl3Application(new TestCharactersAtlas(), config);

    }

}
