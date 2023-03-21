
package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.DebugOnScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactersAtlas;
import com.mygdx.map.Map;
import com.mygdx.pathfinding.Vector2int;
import com.mygdx.weapon.Sword;

//#################################################################################################
// PLayer < LivingEntity (Entity)
//=================================================================================================
// - possède un Sword (d'autres armes pourront être rajoutées ...)
//
// - en MULTIJOUEUR, peut être MASTER ou SLAVE
//
// - le MASTER gère l'IA des monstres
//#################################################################################################
public class Player extends LivingEntity {
    private final static int MAX_LIFE = 200;
    private final static String PLAYERS_ATLAS = "characters/RMXP_humans.atlas";
    private static TextureAtlas allPlayersAtlas;
    private String lobbyPlayerId;
    private String numLobby = "";
    private boolean isMaster;
    private static Map map; // même carte pour tout le monde !
    private Sword sword;
    private static final int NO_HURT_TIME = 1000; // temps durant lequel on est intouchable, en millis

    public Player() {
        // TEXTURE DE TOUS LES OBJETS PLAYER ----------------------------------
        if (allPlayersAtlas == null) {
            allPlayersAtlas = new TextureAtlas(Gdx.files.internal(PLAYERS_ATLAS));
        }

        currentLife = maxLife = MAX_LIFE;
        uniqueID = "player" + nextUniqueId();

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        // TODO : remove randomness
        RMXP_CHARACTER = (int) (Math.random() * RMXPCharactersAtlas.MAX_CHARACTERS) + "_";

        sword = new Sword(this);
        lastHurtTime = 0;
        initializeSprite();

        // to position correctly hitbox + sprite well -------------------------
        setX(getX());
        setY(getY());

        System.out.println("---------------------------- CONSTRUCTOR Player " + uniqueID);
    }

    public Player(Map m) {
        this();
        map = m;
        Vector2int startPoint = map.randomPointAtSpawnArea("START");
        setX(startPoint.x- sprite.getWidth()/2);
        setY(startPoint.y);
    }

    @Override
    public void initializeSprite() {
        if (entityAtlas == null) entityAtlas = allPlayersAtlas; // texture locale

        sprite = new Sprite(allPlayersAtlas.findRegion("0_DOWN_0"));
        //sprite.setColor(spriteTint);
        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);
        animate("DOWN"); // au départ, le perso regarde vers le bas
        lifeBar.setBarRatio(MAX_LIFE);
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
        if (isMaster) {
            System.out.println(uniqueID + " *** setMaster ========== " + isMaster +
                    " / " + MainGame.getInstance().getGameMode());

//            masterMobs = new Monsters(map, );
        } else {
//            masterMobs.reset();
        }
    }

    public void move(String dirKeyword, int deltaX, int deltaY) {
        animate(dirKeyword); // dans tous les cas, on anime

        if (MainGame.getInstance().getMap().checkObstacle(this, deltaX, deltaY))
            return; // OBSTACLE ! on ne bouge pas !

        if (deltaX != 0) setX(entityX + deltaX);

        if (deltaY != 0) setY(entityY + deltaY);
    }

    public void attack() {
        // Monsters.killRandom();
        // TODO: attaque avec une épée
        if (sword.attack()) {
            Mob mob = Monsters.getInstance().checkAttackHit(sword);
            DebugOnScreen.getInstance().setText(10, (mob == null ? "NULL" : mob.uniqueID));
        }
    }

    @Override
    public void animate(String string) {
        super.animate(string);
        sword.animate(string);
    }

    int frames = 0;

    public void drawAndUpdate(SpriteBatch batch) {
        super.drawAndUpdate(batch);
        sword.drawAndUpdate(batch);
        // TODO : clignote en rouge
        frames++;
        if (!canBeHurt())

            sprite.setColor((frames % 10 == 0 || frames % 4 == 0) ? Color.RED : Color.BLACK);
        else
            sprite.setColor(Color.WHITE);
    }

    public boolean canBeHurt() {
        return (System.currentTimeMillis() - lastHurtTime > NO_HURT_TIME);
    }
}