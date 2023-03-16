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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public final class RMXPMonstersAtlas extends RMXPAtlasGenerator {

    static final String PNG_FILENAME = "RMXP_monsters.png";

    // public pour lecture du testeur
    public static int MAX_MONSTERS = 7; // rendre dynamique !!!!!!!!!!!!!!!!!!!
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

        // chauve-souris
        monstersFramesSet.add(new Rectangle(2014, 2, 128, 192));

        // diable bleu ailé
        monstersFramesSet.add(new Rectangle(1164, 198, 192, 256));

        // scorpion rouge
        monstersFramesSet.add(new Rectangle(1680, 198, 256, 256));

        // insecte monstrueux ailé (TROP LAID)
//        monstersFramesSet.add(new Rectangle(1938, 198, 320, 256));

        // molusque hargneux
        monstersFramesSet.add(new Rectangle(2, 458, 256, 320));

        // gélatine bleutée
        monstersFramesSet.add(new Rectangle(1744, 586, 192, 192));

        // troll blanc
        monstersFramesSet.add(new Rectangle(2, 782, 320, 384));

        // serpent multicolore (mouvement bizarre !)
//        monstersFramesSet.add(new Rectangle(646, 782, 384, 384));

        // arbre maléfique
        monstersFramesSet.add(new Rectangle(902, 1298, 384, 384));

        MAX_MONSTERS = monstersFramesSet.size();
    }

    //=========================================================================
    // Copier-coller le texte obtenu dans la console => fichier .atlas
    //=========================================================================
    public static void printlnAtlas(boolean writeTofile) {

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

                            .append("\tsize: " + frameWidth + ", " + frameHeight + "\n")
                            .append("\torig: " + frameWidth + ", " + frameHeight + "\n")
                            .append("\toffset: 0, 0\n")
                            .append("\tindex: -1\n"); /* /!\ PAS DE DOUBLE NEW_LINE /!\ */
                }

            }
            nChar++;

        }

        if (writeTofile)
            writeTextFile("C:\\Users\\DAM_007\\Documents\\GitHub\\DAM2023-MULTIPLAYER-GAME\\PROJECT\\assets\\characters\\RMXP_monsters.atlas", sbAtlas.toString());

        System.out.println(sbAtlas);
    }

    public static void writeTextFile(String filePath, String atlasContent) {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            File logFile = new File(timeLog);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            System.out.println("============================= filePath=" + filePath);
            writer = new BufferedWriter(new FileWriter(filePath)); // logfile
            writer.write(atlasContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }


}

