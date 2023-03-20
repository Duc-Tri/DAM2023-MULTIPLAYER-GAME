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
    protected float SLASH_TIME = 400; // la durée active de l'arme pedant une attaque, en millis
    protected float COOLDOWN_TIME = 10; // délai avant de pouvoir réutiliser l'arme, en millis
    protected boolean slashOn; // l'arme est lancée ou pas
    private long lastMillis;

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

    // STATIC => POUR TOUTES LE ARMES !!!
    //---------------------------------------------------------------------------------------------
    final static HashMap<String, Integer> WEAPON_ROTATION = new HashMap<String, Integer>() {
        {
            put("RIGHT", -90);
            put("LEFT", 90);
            put("DOWN", 180);
            put("UP", 0);
        }
    };

    // PAS STATIC => PROPRE À CHAQUE ARME !!!
    //---------------------------------------------------------------------------------------------
    final HashMap<String, Rectangle> HITBOX_ROTATION = new HashMap<String, Rectangle>() {
        {
            put("UP", new Rectangle(0, 0, HITBOX_HEIGHT, HITBOX_WIDTH));
            put("DOWN", new Rectangle(0, 0, -HITBOX_HEIGHT, -HITBOX_WIDTH));
            put("RIGHT", new Rectangle(0, 0, HITBOX_WIDTH, -HITBOX_HEIGHT));
            put("LEFT", new Rectangle(0, 0, -HITBOX_WIDTH, -HITBOX_HEIGHT));
        }
    };

    @Override
    public void animate(String dir) {
        System.out.println("WEAPON:animate ===== " + dir);

//        sprite.setRotation(0);

        sprite.setRotation(WEAPON_ROTATION.get(dir));
        hitbox = HITBOX_ROTATION.get(dir);

        if (dir.equalsIgnoreCase("UP"))
            HITBOX_XOFFSET = -HITBOX_H / 2;
        else if (dir.equalsIgnoreCase("DOWN"))
            HITBOX_XOFFSET = HITBOX_H / 2;
        else
            HITBOX_XOFFSET = 0;

        //(int) HITBOX_ROTATION.get(dir).x; // pointeur sur un objet ...
        System.out.println(HITBOX_XOFFSET + " / " + HITBOX_YOFFSET);
    }

    public void update() {
        setX(owner.getFootX());
        setY(owner.getY() + owner.getSprite().getHeight() / 2);
        //sprite.setRegionHeight(4);

        long currMillis = System.currentTimeMillis();
        if (slashOn && currMillis - lastMillis > SLASH_TIME) {
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
