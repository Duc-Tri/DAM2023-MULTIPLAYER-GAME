package com.mygdx.graphics;

public class RMXPAtlasGenerator {
    // dans l'ordre de la spritesheet, de haut en bas
    public static final String DIRS[] = {"DOWN", "LEFT", "RIGHT", "UP"};

    static final String IDDLE = "IDDLE";

    public static final int ANIM_FRAMES = 4;

    public static String randomDir() {
        return DIRS[(int)(Math.random() * DIRS.length)];
    }
}
