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
        config.setForegroundFPS(60);
        config.setTitle("PATHFINDER");
//        config.setWindowedMode(1500, 1000);

        RMXPCharactesAtlasGenerator.printlnAtlas();
        new Lwjgl3Application(new TestCharactersAtlas(), config); // TEST CHARACTERS

//        new Lwjgl3Application(new PathFinder(), config); // TEST A*
//        new Lwjgl3Application(new LabyTest(), config); // MERGE LABYRINTHE + PATH FINDING
//        new Lwjgl3Application(new Game(), config); // CLIENT + SERVEUR

    }

}
