package com.mygdx.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entity.LivingEntity;

public class LifeBar {
    private final static Texture green = new Texture("test/green.png");
    private final static Texture black = new Texture("test/black.png");
    private LivingEntity livingEntity;
    private Sprite sprite;
    private static final int WIDTH = 40;
    private static final int HEIGHT = 4;
    private float ratio;

    public LifeBar(LivingEntity e) {
        livingEntity = e;
        sprite = new Sprite(green);
    }

    public void setBarRatio(int maxLife) {
        ratio = (float) maxLife / WIDTH;
    }


    public void draw(SpriteBatch batch) {

        //System.out.println("draw lifeBar ===== " + livingEntity.getUniqueID() + " / " + livingEntity.getCurrentLife() + " / " + ratio);

        sprite.setColor(1, 0, 0, 1); // ?????

        float x = livingEntity.getFootX() - WIDTH / 2;
        float y = livingEntity.getY() + 1;

        // background
        batch.draw(black, x, y, WIDTH, HEIGHT);

        // foreground
        batch.draw(green, x, y, livingEntity.getCurrentLife() / ratio, HEIGHT);
    }
}
