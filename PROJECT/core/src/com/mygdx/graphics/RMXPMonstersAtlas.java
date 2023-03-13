package com.mygdx.graphics;

/*
===============================================================================
Génération de l'atlas pour les monstres de la sprite sheet de RPG MAKER XP.
DUPLICATION HONTEUSE DE RMXPCharactersAtlasGenerator, à optimiser, mutualiser ...

- les monstres ont 4 directions de marche et d'iddle

- les monstres ont des tailles différentes, d'où des ensembles de frames de dimensions différentes

- à l'intérieur d'un ensemble, les frames ont la même taille

- chaque animation de marche a 4 frames, (iddle / pas1 / iddle / pas2)

- chaque monstre a donc 16 frames (4 en colonnes, 4 en lignes)

- pour chaque ligne de frames, on a: dir_frame0, dir_frame1, dir_frame2 et dir_frame3

- dir_frame0 et dir_frame2 sont le même sprite... ce n'est donc pas optimal (d'origine)

- nous n'allons pas traiter tous les monstres de la feuille de sprites, seulement un best of

La sprite sheet en ligne :
https://www.spriters-resource.com/pc_computer/rpgmakerxp/sheet/100488/
===============================================================================
*/

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public final class RMXPMonstersAtlas extends RMXPAtlasGenerator {

    static final String PNG_FILENAME = "RMXP_monsters.png";

    // public pour lecture du testeur
    public static int MAX_CHARACTERS = -1; // nb. de monstres traités
    int FRAME_WIDTH = -1; // selon le monstre
    int FRAME_HEIGHT = -1; // selon le monstre


    // dans l'ordre de la spritesheet, de haut en bas

    // ensemble des frames (x, y, width, height) de différents monstres
    static ArrayList<Rectangle> monstersFramesSet;

    private static void populateMonstersFramesData() {
        System.out.println("populateMonstersFramesData ################################ " + PNG_FILENAME);
        monstersFramesSet = new ArrayList<>();
        // diablotin bleu aux yeux rouges
        monstersFramesSet.add(new Rectangle(910, 2, 128, 192));

        MAX_CHARACTERS = monstersFramesSet.size();
    }


    //=========================================================================
    // Copier-coller le texte obtenu dans la console => fichier .atlas
    //=========================================================================
    public static void printlnAtlas() {

        // on génère les monstres de gauche à droite, de haut en bas
        // example du format :
        //        95_LEFT_0
        //        rotate: false
        //        xy: 1204,1020
        //        size: 32, 48
        //        orig: 32, 48
        //        offset: 0, 0
        //        index: -1

        populateMonstersFramesData();
        StringBuilder sbAtlas = new StringBuilder();

        // entête -------------------------------
        sbAtlas.append(PNG_FILENAME + "\n")
                .append("size: 2576, 1684\n")
                .append("format: RGBA8888\n")
                .append("filter Nearest,Nearest\n")
                .append("repeat: none\n");

        int nChar = 0; // indice global du monstre

        for (Rectangle monsterFrames : monstersFramesSet) {

            int frameWidth = (int) (monsterFrames.width / ANIM_FRAMES); // normalement division entière !
            int frameHeight = (int) (monsterFrames.height / DIRS.length); // normalement division entière !

            for (int iDir = 0; iDir < DIRS.length; iDir++) {

                String currentDir = DIRS[iDir];

                // ANIM_FRAMES+1 car la frame iddle est en position 0
                for (int frame = 0; frame < ANIM_FRAMES; frame++) {

                    sbAtlas.append(nChar + "_" + currentDir + "_" + frame + "\n");

                    // AU CAS PAR CAS
                    sbAtlas.append("\trotate: false\n")
                            .append("\txy: " +
                                    (int) (monsterFrames.x + frame * frameWidth) +
                                    "," +
                                    (int) (monsterFrames.y + iDir * frameHeight) + "\n")

                            .append("\tsize: " + (int) monsterFrames.width + ", " + (int) monsterFrames.height + "\n")
                            .append("\torig: " + (int) monsterFrames.width + ", " + (int) monsterFrames.height + "\n")
                            .append("\toffset: 0, 0\n")
                            .append("\tindex: -1\n"); /* /!\ PAS DE DOUBLE NEW_LINE /!\ */
                }

            }
            nChar++;


        }

        System.out.println(sbAtlas);
    }


}

