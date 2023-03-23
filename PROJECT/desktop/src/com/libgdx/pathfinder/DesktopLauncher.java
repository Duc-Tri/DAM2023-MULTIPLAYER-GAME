package com.libgdx.pathfinder;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPMonstersAtlas;
import com.mygdx.test.TestAtlas;
import com.mygdx.test.TestMonstersScreen;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("DESKTOP");
        config.setWindowedMode(1800, 900);

        MainGame.getInstance().setConfig("desktop");


        // JEU CLIENT + SERVEUR ###################################################################
        new Lwjgl3Application(MainGame.getInstance(), config);


        // TEST PATHFINDING ---------------------------------------------------
//        new Lwjgl3Application(new PathFinder(), config); // TEST A*


        // TEST LABYRINTHE ----------------------------------------------------
//        new Lwjgl3Application(new LabyTest(), config); // MERGE LABYRINTHE + PATH FINDING


        // TESTS ATLAS PERSONNAGES --------------------------------------------
//        config.setTitle("TESTS ATLAS PERSONNAGES");
//        RMXPCharactersAtlas.printlnAtlas();
//        RMXPMonstersAtlas.printlnAtlas(false);
//        new Lwjgl3Application(new TestAtlas(), config);


        // TESTS MONSTRES + PLAYER --------------------------------------------
//        config.setTitle("TEST MONSTERS (solo)");
//        new Lwjgl3Application(new TestMonstersScreen(), config);

    }

}
