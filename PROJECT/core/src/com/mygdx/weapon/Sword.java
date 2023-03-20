package com.mygdx.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.LivingEntity;
import com.mygdx.entity.Mob;

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
        hitbox = new Rectangle();

        initializeSprite();
    }

    @Override
    public void initializeSprite() {
        sprite = new Sprite(allSwordsAtlas.findRegion("" + SwordsNum.get(swordType)));
        sprite.setOrigin(sprite.getWidth() / 2, 0);
        sprite.setScale(0.55f,0.55f);
//        final static int HITBOX_W = 60;
//        final static int HITBOX_H = 20;

        HITBOX_WIDTH = 60; //(int) (sprite.getWidth() - 16); // temp
        //HITBOX_XOFFSET = (int) ((sprite.getWidth() - HITBOX_WIDTH) / 2); // X :au mileu
        HITBOX_HEIGHT = 20; //(int) (sprite.getHeight() - 16); // temp
        HITBOX_YOFFSET = 0;
        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        //System.out.println("************** END initializeSprite " + findRegion + " / " + sprite);

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

            //batch.draw(sprite, sprite.getX(), sprite.getY(),0,0,sprite.getWidth(),sprite.getHeight(),1,1,(float) (Math.random()*360));
        }
    }

}
