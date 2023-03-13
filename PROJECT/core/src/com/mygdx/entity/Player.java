package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.GameScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactersAtlas;


public class Player extends LivingEntity {

    private static TextureAtlas allPlayersAtlas;

    public Player() {

        // TEXTURE DE TOUS LES OBJETS PLAYER ----------------------------------
        if (allPlayersAtlas == null) {
            System.out.println("initializeSprite .......... " + MainGame.PLAYERS_ATLAS);
            allPlayersAtlas = new TextureAtlas(Gdx.files.internal(MainGame.PLAYERS_ATLAS));
        }

        System.out.println("---------------------------- CONSTRUCTOR Player");
        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "player" + R + V + B;
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        // TODO : remove randomness
        RMXP_CHARACTER = (int) (Math.random() * RMXPCharactersAtlas.MAX_CHARACTERS) + "_";

        findRegion = RMXP_CHARACTER + "DOWN_0";

        initializeSprite();

        // to position everything well ----------
        setX(getX());
        setY(getY());
    }

    @Override
    public void initializeSprite() {

        // TEXTURE LOCAL DE LIVINGENTITY --------------------------------------
        if (textureAtlas == null) {
            textureAtlas = allPlayersAtlas;
        }

        textureRegion = textureAtlas.findRegion(findRegion);

        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        sprite = new Sprite(textureRegion);

        //sprite.scale(scale);
        //sprite.setColor(spriteTint);
    }

    private float getRealX() {
        float relativePlayerX = entityX - GameScreen.SCREEN_WIDTH / 2.0f + GameScreen.getCamera().position.x;
        return relativePlayerX;
    }

    private float getRealY() {
        float relativePlayerY = entityY - GameScreen.SCREEN_HEIGHT / 2.0f + GameScreen.getCamera().position.y;// + 10;
        return relativePlayerY;
    }

    public void setYFromRealY() {
        float temp = getRealY() - GameScreen.getCamera().position.y + GameScreen.SCREEN_HEIGHT / 2.0f;
        setY(temp);
    }

    public void setXFromRealX() {
        float temp = getRealX() - GameScreen.getCamera().position.x + GameScreen.SCREEN_WIDTH / 2.0f; // + 10;
        setX(temp);
    }

}
