package com.mygdx.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.LivingEntity;
import com.mygdx.pathfinding.Vector2int;

import java.util.HashMap;

//#################################################################################################
// Sword < Weapon < Item (Entity)
//=================================================================================================
//
//#################################################################################################
public final class Sword extends Weapon {
    private final static String SWORDS_ATLAS = "weapons/swords.atlas";
    private static TextureAtlas allSwordsAtlas; // le même atlas pour toutes les épées
    private final SwordType swordType;

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
        put(SwordType.EVIL_SWORD, 20);
        put(SwordType.BROADSWORD, 16);
        put(SwordType.SCIMITAR, 8);
        put(SwordType.CLAYMORE, 4);
        put(SwordType.BASTARD, 2);
        put(SwordType.GLADIUS, 1);
    }};

    public Sword(LivingEntity owner) {
        // TODO: random sword, change it !
        this(owner, SwordType.values()[(int) (Math.random() * SwordType.values().length)]);
    }

    public Sword(LivingEntity owner, SwordType type) {
        super(owner);
        if (allSwordsAtlas == null) {
            allSwordsAtlas = new TextureAtlas(SWORDS_ATLAS);
        }
        this.owner = owner;
        swordType = type;
        damage = SwordsDamage.get(type);

        initializeSprite();
    }

    @Override
    public void initializeSprite() {
        sprite = new Sprite(allSwordsAtlas.findRegion("" + SwordsNum.get(swordType)));
        sprite.setOrigin(sprite.getWidth() / 2, 0); // IMPORTANT

        HITBOX_WIDTH = 60; // fixe
        HITBOX_HEIGHT = 20; // fixe

        HITBOX_XOFFSET = 0; // recalculé à chaque changement de direction
        HITBOX_YOFFSET = 0; // recalculé à chaque changement de direction
        hitbox = null; // recalculé à chaque changement de direction

        HITBOXES.put("UP", new Rectangle(0, 0, HITBOX_HEIGHT, HITBOX_WIDTH));
        HITBOXES.put("DOWN", new Rectangle(0, 0, HITBOX_HEIGHT, HITBOX_WIDTH));
        HITBOXES.put("RIGHT", new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT));
        HITBOXES.put("LEFT", new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT));

        HITBOX_OFFSETS.put("UP", new Vector2int(-HITBOX_HEIGHT / 2, 0));
        HITBOX_OFFSETS.put("DOWN", new Vector2int(-HITBOX_HEIGHT / 2, -HITBOX_WIDTH));
        HITBOX_OFFSETS.put("RIGHT", new Vector2int(0, -HITBOX_HEIGHT));
        HITBOX_OFFSETS.put("LEFT", new Vector2int(-HITBOX_WIDTH, -HITBOX_HEIGHT));

        SPRITE_YOFFSETS.put("UP", 0);
        SPRITE_YOFFSETS.put("DOWN", 0);
        SPRITE_YOFFSETS.put("RIGHT", -HITBOX_HEIGHT / 2);
        SPRITE_YOFFSETS.put("LEFT", -HITBOX_HEIGHT / 2);

        // On scale le sprite de l'épée pour que sa taille correspond au Hitbox !!!
        float scale = Math.min((float) HITBOX_WIDTH / sprite.getRegionHeight(), (float) HITBOX_HEIGHT / sprite.getRegionWidth());
        sprite.setScale(scale, scale);

//        System.out.println("srH=" + sprite.getRegionHeight() + " / srW=" + sprite.getRegionWidth() +
//                " / HW=" + HITBOX_WIDTH + " / sW=" + sprite.getWidth() + " ********** END initializeSprite " + scale);
    }

    public void drawAndUpdate(SpriteBatch batch) {
        update();
        if (activated) {
            sprite.draw(batch);
            if (DEBUG_HITBOX)
                batch.draw(debugTexture, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }
    }

}
