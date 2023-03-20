package com.mygdx.weapon;

import com.badlogic.gdx.math.Rectangle;
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
    protected float SLASH_TIME = 200; // la durée active de l'arme pedant une attaque, en millis
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
    final static HashMap<String, Integer> WEAPON_ROTATIONS = new HashMap<String, Integer>() {
        {
            put("RIGHT", -90);
            put("LEFT", 90);
            put("DOWN", 180);
            put("UP", 0);
        }
    };

    // PAS STATIC => PROPRE À CHAQUE ARME !!!
    //---------------------------------------------------------------------------------------------
    protected HashMap<String, Rectangle> HITBOXES = new HashMap<>();
    protected HashMap<String, Integer> HITBOXES_XOFFSET = new HashMap<>();

    @Override
    public void animate(String dir) {
        //System.out.println("WEAPON:animate ===== " + dir);

        sprite.setRotation(WEAPON_ROTATIONS.get(dir));
        hitbox = HITBOXES.get(dir);
        HITBOX_XOFFSET = HITBOXES_XOFFSET.get(dir);
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
