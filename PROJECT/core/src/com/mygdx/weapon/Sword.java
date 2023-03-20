package com.mygdx.weapon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.Mob;

import java.util.HashMap;

public class Sword extends Weapon {

    private final static String SWORDS_ATLAS = "weapons/swords.atlas";
    private static TextureAtlas allSwordsAtlas; // le même atlas pour toutes les épées

    public static enum SwordType {EVIL_SWORD, BROADSWORD, SCIMITAR, CLAYMORE, BASTARD, GLADIUS}

    // NUMÉRO DES ÉPÉES SELON L'ORDRE DANS LE FICHIER ATLAS =======================================
    final static HashMap<SwordType, Integer> SwordsNum = new HashMap() {{
        put(SwordType.EVIL_SWORD, 0);
        put(SwordType.BROADSWORD, 1);
        put(SwordType.SCIMITAR, 2);
        put(SwordType.CLAYMORE, 3);
        put(SwordType.BASTARD, 4);
        put(SwordType.GLADIUS, 5);
    }};

    // POINTS DE DOMMAGES DES ÉPÉES ===============================================================
    final static HashMap<SwordType, Integer> SwordsDamage = new HashMap() {{
        put(SwordType.EVIL_SWORD, 50);
        put(SwordType.BROADSWORD, 20);
        put(SwordType.SCIMITAR, 15);
        put(SwordType.CLAYMORE, 12);
        put(SwordType.BASTARD, 8);
        put(SwordType.GLADIUS, 5);
    }};

    public Sword(SwordType type) {
        if (allSwordsAtlas == null) {
            allSwordsAtlas = new TextureAtlas(SWORDS_ATLAS);
        }

        sprite = new Sprite();
        hitbox = new Rectangle();
    }
}
