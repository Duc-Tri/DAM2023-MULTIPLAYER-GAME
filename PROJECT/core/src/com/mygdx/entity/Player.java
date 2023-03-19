
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
    private String lobbyPlayerId;
    private String numLobby = "";
    private boolean isMaster;

    public Player() {

        // TEXTURE DE TOUS LES OBJETS PLAYER ----------------------------------
        if (allPlayersAtlas == null) {
            //System.out.println("initializeSprite .......... " + MainGame.PLAYERS_ATLAS);
            allPlayersAtlas = new TextureAtlas(Gdx.files.internal(MainGame.PLAYERS_ATLAS));
        }

        uniqueID = "player" + nextUniqueId();

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        // TODO : remove randomness
        RMXP_CHARACTER = (int) (Math.random() * RMXPCharactersAtlas.MAX_CHARACTERS) + "_";

        findRegion = RMXP_CHARACTER + "DOWN_0";

        initializeSprite();

        // to position everything well ----------
        setX(getX());
        setY(getY());

        System.out.println("---------------------------- CONSTRUCTOR Player " + uniqueID);
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

    public String getNumLobby() {
        return numLobby;
    }

    public void setNumLobby(String numLobby) {
        this.numLobby = numLobby;
    }

    public String getLobbyPlayerId() {
        return lobbyPlayerId;
    }

    public void setLobbyPlayerId(String lobbyPlayerId) {
        this.lobbyPlayerId = lobbyPlayerId;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
        if (isMaster)
            System.out.println(uniqueID + " *** setMaster ========== " + isMaster +
                    " / " + MainGame.getInstance().getGameMode());
    }
}