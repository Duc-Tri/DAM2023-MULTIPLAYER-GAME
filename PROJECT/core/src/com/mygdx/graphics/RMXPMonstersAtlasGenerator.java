package com.mygdx.graphics;

/*
===============================================================================
Génération de l'atlas pour les monstres de la sprite sheet de RPG MAKER XP.

- les monstres ont 4 directions de marche et d'iddle

- les monstres ont des tailles différentes, d'où des ensembles de frames de dimensions différentes

- à l'intérieur d'un ensemble, les frames ont la même taille

- chaque animation de marche a 4 frames, (iddle / pas1 / iddle / pas2)

- chaque monstre a donc 16 frames (4 en colonnes, 4 en lignes)

- pour chaque ligne de frames, on a: dir_frame0, dir_frame1, dir_frame2 et dir_frame3

- dir_frame0 et dir_frame2 sont le même sprite... ce n'est donc pas optimal (d'origine)

- nous n'allons pas traiter tous les monstres de la feuille de sprites, seulement certains

La sprite sheet en ligne :
https://www.spriters-resource.com/pc_computer/rpgmakerxp/sheet/100488/
===============================================================================
*/

public class RMXPMonstersAtlasGenerator {

    static final String PNG_FILENAME = "RMXP_monsters.png";

    // public pour lecture du testeur
    public static final int MAX_CHARACTERS = 98; // nb. personnages "valides" selon notre format défini
    static final int FRAME_WIDTH = 32;
    static final int FRAME_HEIGHT = 48;
    static final int HORI_PAD = 2; // marge et padding horizontal
    static final int VERT_PAD = 4; // marge et padding vertical

    // public pour lecture du testeur
    public static final int ANIM_FRAMES = 4;

    // dans l'ordre de la spritesheet, de haut en bas
    static final String DIRS[] = {"DOWN", "LEFT", "RIGHT", "UP"};

    static final String IDDLE = "IDDLE";
    static final int CHAR_HORI = 10; // nb de persos en horizontal
    static final int CHAR_VERT = 10; // nb de persos en vertical

    //=========================================================================
    // Copier-coller le texte obtenu dans un fichier .atlas
    //=========================================================================
    public static void printlnAtlas() {

        // on génère les persos de gauche à droite, de haut en bas
        // example du format :
        //        95_LEFT_0
        //        rotate: false
        //        xy: 1204,1020
        //        size: 32, 48
        //        orig: 32, 48
        //        offset: 0, 0
        //        index: -1

        StringBuilder sbAtlas = new StringBuilder();

        // entête -------------------------------
        sbAtlas.append(PNG_FILENAME + "\n")
                .append("size: 1302,2160\n")
                .append("format: RGBA8888\n")
                .append("filter Nearest,Nearest\n")
                .append("repeat: none\n");

        int nChar = 0; // indice global du personnage
        for (int ychar = 0; ychar < CHAR_VERT; ychar++) {

            for (int xchar = 0; xchar < CHAR_HORI; xchar++) {

                if (nChar >= MAX_CHARACTERS)
                    break;

                for (int iDir = 0; iDir < DIRS.length; iDir++) {

                    String currentDir = DIRS[iDir];

                    // ANIM_FRAMES+1 car la frame iddle est en position 0
                    for (int frame = 0; frame < ANIM_FRAMES; frame++) {

                        // après la frame iddle, on fait repartir le # frame de 0
                        //sbAtlas.append(nChar + "_" + currentDir + "_" + ((frame == 0) ? "IDDLE" : (frame - 1)) + "\n");

                        sbAtlas.append(nChar + "_" + currentDir + "_" + frame + "\n");

                        sbAtlas.append("\trotate: false\n")
                                .append("\txy: " +
                                        ((xchar + 1) * HORI_PAD + xchar * (ANIM_FRAMES) * FRAME_WIDTH + frame * FRAME_WIDTH) +
                                        "," +
                                        ((ychar + 1) * VERT_PAD + ychar * (DIRS.length) * FRAME_HEIGHT + iDir * FRAME_HEIGHT) + "\n")
                                .append("\tsize: " + FRAME_WIDTH + ", " + FRAME_HEIGHT + "\n")
                                .append("\torig: " + FRAME_WIDTH + ", " + FRAME_HEIGHT + "\n")
                                .append("\toffset: 0, 0\n")
                                .append("\tindex: -1\n"); /* /!\ PAS DE DOUBLE NEW_LINE /!\ */
                    }

                }
                nChar++;
            }

        }

        System.out.println(sbAtlas);
    }

}

