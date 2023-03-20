package com.mygdx.weapon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.Item;
import com.mygdx.entity.LivingEntity;

//#################################################################################################
// Weapon, arme avec lequel le joueur attaque.
//=================================================================================================
//
//#################################################################################################
public abstract class Weapon extends Item {

    protected int damage;
    protected float slashTime; // le temps de présence dans le jeu, en milliseconds
    protected float scoolDownTime; // délai de refroidissement, avant de pouvoir réutiliser l'arme
    protected boolean slashOn; // l'arme est lancée ou pas

    public Weapon() {

    }

    public void attack(float deltaTime) {
        // TODO: tenir compte du cooldown et du slashTime
        slashOn = true;
    }

    public boolean hitWith(LivingEntity ent) {
        if (!slashOn) return false;

        return true;
    }


}
