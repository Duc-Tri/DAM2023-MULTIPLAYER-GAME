package com.mygdx.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.DebugOnScreen;
import com.mygdx.entity.Item;
import com.mygdx.entity.LivingEntity;

import java.util.HashMap;

//#################################################################################################
// Weapon, arme avec lequel le joueur attaque.
//=================================================================================================
//
//#################################################################################################
public abstract class Weapon extends Item {
    public static final boolean DEBUG_HITBOX = true;

    protected int damage;
    protected float SLASH_TIME = 200; // le temps d'apparition dans le jeu, en milliseconds
    protected float COOLDOWN_TIME = 10; // délai avant de pouvoir réutiliser l'arme, en millis
    protected boolean slashOn; // l'arme est lancée ou pas
    private long lastMillis;
    protected int HITBOX_WIDTH = 16;
    protected int HITBOX_HEIGHT = 16;

    protected LivingEntity owner;

    public Weapon(LivingEntity owner) {
        lastMillis = System.currentTimeMillis();
        slashOn = false;
        this.owner = owner;
    }

    public void attack() {
        // TODO: tenir compte du cooldown et du slashTime
        long currMillis = System.currentTimeMillis();

        if (!slashOn && currMillis - lastMillis > COOLDOWN_TIME) {
            slashOn = true;
            lastMillis = currMillis;
            System.out.println(currMillis + " attack:SLASH ===== " + slashOn);
        }

    }

    final static HashMap<String, Integer> WEAPON_ROTATION = new HashMap<String, Integer>() {
        {
            put("RIGHT", -90);
            put("LEFT", 90);
            put("DOWN", 180);
            put("UP", 0);
        }
    };

    final static int HITBOX_W = 50;
    final static int HITBOX_H = 20;

    final static HashMap<String, Rectangle> HITBOX_ROTATION = new HashMap<String, Rectangle>() {
        {
            put("UP", new Rectangle(-800, 0, HITBOX_H, HITBOX_W));
            put("DOWN", new Rectangle(800, 0, -HITBOX_H, -HITBOX_W));
            put("RIGHT", new Rectangle(0, 100, HITBOX_W, -HITBOX_H));
            put("LEFT", new Rectangle(0, 500, -HITBOX_W, -HITBOX_H));

        }
    };

    @Override
    public void animate(String dir) {
        System.out.println("WEAPON:animate ===== " + dir);

//        sprite.setRotation(0);

        sprite.setRotation(WEAPON_ROTATION.get(dir));
        hitbox = HITBOX_ROTATION.get(dir);
//        HITBOX_XOFFSET = (int) HITBOX_ROTATION.get(dir).x;
        System.out.println(HITBOX_XOFFSET + " / " + HITBOX_YOFFSET);
    }

    public void update() {
        setX(owner.getFootX());
        setY(owner.getY() + owner.getSprite().getHeight() / 2);
        //sprite.setRegionHeight(4);

        long currMillis = System.currentTimeMillis();
        if (currMillis - lastMillis > SLASH_TIME) {
            slashOn = false;
            lastMillis = currMillis;
//            System.out.println(currMillis + " animate:SLASH ===== " + slashOn);
        }
    }

    public boolean hitWith(LivingEntity ent) {
        if (!slashOn)
            return false;

        // TODO: check with hitboxes

        return true;
    }


    @Override
    public void setX(float x) {
        hitbox.setX(x + HITBOX_XOFFSET);
        sprite.setX(x);
    }

    @Override
    public void setY(float y) {
        hitbox.setY(y); // + HITBOX_YOFFSET);
        sprite.setY(y);
    }
}
