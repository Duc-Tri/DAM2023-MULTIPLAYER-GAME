package com.mygdx.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.LivingEntity;

import java.util.HashMap;

public class Sword extends Weapon {

    private static final Texture debugTarget8 = new Texture("misc/target8x8.png");

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
        put(SwordType.EVIL_SWORD, 50);
        put(SwordType.BROADSWORD, 20);
        put(SwordType.SCIMITAR, 15);
        put(SwordType.CLAYMORE, 12);
        put(SwordType.BASTARD, 8);
        put(SwordType.GLADIUS, 5);
    }};

    public Sword(LivingEntity owner) {
        // random sword
        this(owner, SwordType.values()[(int) (Math.random() * SwordType.values().length)]);
    }

    public Sword(LivingEntity owner, SwordType type) {
        super(owner);
        if (allSwordsAtlas == null) {
            allSwordsAtlas = new TextureAtlas(SWORDS_ATLAS);
        }
        this.owner = owner;
        swordType = type;

        initializeSprite();
    }

    @Override
    public void initializeSprite() {
        sprite = new Sprite(allSwordsAtlas.findRegion("" + SwordsNum.get(swordType)));
        sprite.setOrigin(sprite.getWidth() / 2, 0);

        HITBOX_WIDTH = 60; //(int) (sprite.getWidth() - 16); // temp
        HITBOX_XOFFSET = 0; // (int) ((sprite.getWidth() - HITBOX_WIDTH) / 2); // X :au mileu
        HITBOX_HEIGHT = 20; //(int) (sprite.getHeight() - 16); // temp
        HITBOX_YOFFSET = 0;
        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        HITBOXES.put("UP", new Rectangle(0, 0, HITBOX_HEIGHT, HITBOX_WIDTH));
        HITBOXES.put("DOWN", new Rectangle(0, 0, -HITBOX_HEIGHT, -HITBOX_WIDTH));
        HITBOXES.put("RIGHT", new Rectangle(0, 0, HITBOX_WIDTH, -HITBOX_HEIGHT));
        HITBOXES.put("LEFT", new Rectangle(0, 0, -HITBOX_WIDTH, -HITBOX_HEIGHT));

        HITBOXES_XOFFSET.put("UP", -HITBOX_HEIGHT / 2);
        HITBOXES_XOFFSET.put("DOWN", HITBOX_HEIGHT / 2);
        HITBOXES_XOFFSET.put("RIGHT", 0);
        HITBOXES_XOFFSET.put("LEFT", 0);

        // On scale le sprite de l'épée pour que sa taille correspond au Hitbox !!!
        float scale = Math.min((float) HITBOX_WIDTH / sprite.getRegionHeight(), (float) HITBOX_HEIGHT / sprite.getRegionWidth());
        sprite.setScale(scale, scale);

//        System.out.println("srH=" + sprite.getRegionHeight() + " / srW=" + sprite.getRegionWidth() +
//                " / HW=" + HITBOX_WIDTH + " / sW=" + sprite.getWidth() + " ********** END initializeSprite " + scale);
    }

    @Override
    public void animate(String string) {
        super.animate(string);

    }

    public void drawAndUpdate(SpriteBatch batch) {
        update();
        if (slashOn) {

            //sprite.setRotation(90); //(float) (Math.random()*360));
//            batch.draw(sprite, sprite.getX(), sprite.getY());
            sprite.draw(batch);
            if (DEBUG_HITBOX)
                batch.draw(debugTarget8, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }
    }

}
