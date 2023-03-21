package com.mygdx.weapon;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entity.Item;
import com.mygdx.entity.LivingEntity;
import com.mygdx.pathfinding.Vector2int;

import java.util.HashMap;

//#################################################################################################
// Weapon, arme avec laquelle le joueur attaque.
//=================================================================================================
//
//#################################################################################################
public abstract class Weapon extends Item {
    public static final boolean DEBUG_HITBOX = false;

    protected int damage;

    protected boolean activated; // l'arme est activée ou pas
    protected float ACTIVE_TIME = 200; // la durée active de l'arme pendant une attaque, en millis
    protected float COOLDOWN_TIME = 10; // délai avant de pouvoir réutiliser l'arme, en millis
    private long lastMillis;
    protected LivingEntity owner;

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
    // pas static => propre à chaque arme !!!
    protected HashMap<String, Rectangle> HITBOXES = new HashMap<>();
    protected HashMap<String, Vector2int> HITBOX_OFFSETS = new HashMap<>();

    protected HashMap<String, Integer> SPRITE_YOFFSETS = new HashMap<>();

    public Weapon(LivingEntity owner) {
        lastMillis = System.currentTimeMillis();
        activated = false;
        this.owner = owner;
    }

    public boolean attack() {
        // TODO: tenir compte du cooldown et du slashTime
        long currMillis = System.currentTimeMillis();

        if (!activated && currMillis - lastMillis > COOLDOWN_TIME) {
            activated = true;
            lastMillis = currMillis;
            // System.out.println(currMillis + " attack:SLASH ================= " + activated);
        }
        return activated;
    }

    @Override
    public void animate(String dir) {
        //System.out.println("WEAPON:animate ===== " + dir);

        sprite.setRotation(WEAPON_ROTATIONS.get(dir));
        SPRITE_YOFFSET = SPRITE_YOFFSETS.get(dir);

//        sprite.setX(hitbox.x);
//        sprite.setCenterY(hitbox.y + hitbox.height / 2);
        hitbox = HITBOXES.get(dir);
        HITBOX_XOFFSET = HITBOX_OFFSETS.get(dir).x;
        HITBOX_YOFFSET = HITBOX_OFFSETS.get(dir).y;
    }

    public void update() {
        setX(owner.getFootX());
        setY(owner.getY() + owner.getHalfHeight());

        // est-ce que l'arme a épuisé son temps d'activation ?
        if (activated) {
            long currMillis = System.currentTimeMillis();
            if (currMillis - lastMillis > ACTIVE_TIME) {
                activated = false;
                lastMillis = currMillis;
//            System.out.println(currMillis + " animate:SLASH ===== " + slashOn);
            }
        }
    }

    public boolean hitWith(LivingEntity ent) {
        if (!activated)
            return false;

        // TODO: check with hitboxes

        return true;
    }

    public void setX(float x) {
        hitbox.setX(x + HITBOX_XOFFSET);
        sprite.setX(x - sprite.getWidth() / 2);
    }

    public void setY(float y) {
        hitbox.setY(y + HITBOX_YOFFSET);
        sprite.setY(y + SPRITE_YOFFSET);
    }

    public boolean isActivated() {
        return activated;
    }

    public int getDamage() {
        return damage;
    }

}
