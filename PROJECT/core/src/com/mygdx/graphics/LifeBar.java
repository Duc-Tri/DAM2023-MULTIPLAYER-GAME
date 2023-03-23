package com.mygdx.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.entity.LivingEntity;

import java.util.Currency;

import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;

public class LifeBar {
    private final static TextureAtlas colorsAtlas = new TextureAtlas("misc/colors.atlas");
    private final static TextureRegion blackRegion = colorsAtlas.findRegion("BLACK");
    private TextureRegion colorRegion;
    private LivingEntity owner;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 4;
    private float ratio;
    private int maxLife;

    public LifeBar(LivingEntity e) {
        owner = e;
        colorRegion = colorsAtlas.findRegion("GREY");
    }

    public void setBarRatio(int maxLife) {
        this.maxLife = maxLife;
        ratio = (float) maxLife / WIDTH;
//        System.out.println("LifeBar +++++++++++++++++ " + owner.getUniqueID() +
//                " #" + owner.getCurrentLife() + " / " + owner.getMaxLife());
    }

    public void draw(SpriteBatch batch) {
        float x = owner.getFootX() - WIDTH / 2;
        float y = owner.getY() + owner.getSprite().getHeight() + 1;
        int life = owner.getCurrentLife();

        //System.out.println("draw lifeBar ===== " + livingEntity.getUniqueID() + " / " + livingEntity.getCurrentLife() + " / " + ratio);

        if (life < maxLife / 3f)
            colorRegion = colorsAtlas.findRegion("RED");
        else if (life < 2f * maxLife / 3f)
            colorRegion = colorsAtlas.findRegion("ORANGE");
        else
            colorRegion = colorsAtlas.findRegion("GREEN");

        // BACKGROUND ---------------------------
        batch.draw(blackRegion, x, y, WIDTH, HEIGHT);

        // FOREGROUND ---------------------------
        if (life > 0)
            batch.draw(colorRegion, x, y, life / ratio, HEIGHT);
    }
}
