package com.mygdx.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.entity.LivingEntity;

public class LifeBar {
    private final static TextureAtlas colorsAtlas = new TextureAtlas("misc/colors.atlas");
    private final static TextureRegion blackRegion = colorsAtlas.findRegion("BLACK");
    private TextureRegion colorRegion;
    private LivingEntity livingEntity;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 4;
    private float ratio;
    private int maxLife;

    public LifeBar(LivingEntity e) {
        livingEntity = e;
        colorRegion = colorsAtlas.findRegion("GREY");
    }

    public void setBarRatio(int maxLife) {
        this.maxLife = maxLife;
        ratio = (float) maxLife / WIDTH;
    }

    public void draw(SpriteBatch batch) {
        float x = livingEntity.getFootX() - WIDTH / 2;
        float y = livingEntity.getY() + 1;
        int life = livingEntity.getCurrentLife();

        //System.out.println("draw lifeBar ===== " + livingEntity.getUniqueID() + " / " + livingEntity.getCurrentLife() + " / " + ratio);

        if (life < maxLife / 3f)
            colorRegion = colorsAtlas.findRegion("RED");
        else if (life < 2f * maxLife / 3f)
            colorRegion = colorsAtlas.findRegion("ORANGE");
        else
            colorRegion = colorsAtlas.findRegion("GREEN");

        // background
        batch.draw(blackRegion, x, y, WIDTH, HEIGHT);

        // foreground
        batch.draw(colorRegion, x, y, life / ratio, HEIGHT);
    }
}
