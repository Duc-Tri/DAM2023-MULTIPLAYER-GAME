package com.libgdx.pathfinder;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.libgdx.pathfinder.PathFinder;
import com.mygdx.bagarre.Game;
import com.mygdx.graphics.RMXPCharactesAtlasGenerator;
import com.mygdx.graphics.TestCharactersAtlas;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(30);
        config.setTitle("PATHFINDER");
        config.setWindowedMode(1500, 1000);


        // CLIENT + SERVEUR ---------------------------------------------------
        new Lwjgl3Application(new Game(), config);


        // TEST PATHFINDING ---------------------------------------------------
//        new Lwjgl3Application(new PathFinder(), config); // TEST A*


        // TEST LABYRINTHE ----------------------------------------------------
//        new Lwjgl3Application(new LabyTest(), config); // MERGE LABYRINTHE + PATH FINDING


        // TESTS ATLAS PERSONNAGES---------------------------------------------
//        RMXPCharactesAtlasGenerator.printlnAtlas();
//        new Lwjgl3Application(new TestCharactersAtlas(), config);

    }

}
