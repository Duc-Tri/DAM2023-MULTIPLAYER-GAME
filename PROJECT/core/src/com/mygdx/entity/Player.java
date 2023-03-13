package com.mygdx.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.GameScreen;
import com.mygdx.bagarre.MainGame;
import com.mygdx.graphics.RMXPCharactersAtlasGenerator;


public class Player extends LivingEntity {



    public Player() {

        final int R = 10 + (int) (Math.random() * 90);
        final int V = 10 + (int) (Math.random() * 90);
        final int B = 10 + (int) (Math.random() * 90);
        uniqueID = "player" + R + V + B;
        spriteTint = new Color((float) R / 100, (float) V / 100, (float) B / 100, 1);

        RMXP_CHARACTER = (int) (Math.random() * RMXPCharactersAtlasGenerator.MAX_CHARACTERS) + "_";
        findRegion = RMXP_CHARACTER + "DOWN_0";

        initializeSprite();

        // to position everything well ----------
        setX(getX());
        setY(getY());
    }

    public void initializeSprite() {
        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal(MainGame.playersAtlasPath));
        }
        textureRegion = textureAtlas.findRegion(findRegion);

        hitbox = new Rectangle(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);

        sprite = new Sprite(textureRegion);
        //sprite.scale(scale);
        //sprite.setColor(spriteTint);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setX(float playerX) {
        this.entityX = playerX;
        hitbox.setX(playerX + HITBOX_XOFFSET);
        sprite.setX(playerX);
    }

    public void setY(float playerY) {
        this.entityY = playerY;
        hitbox.setY(playerY + HITBOX_YOFFSET);
        sprite.setY(playerY);
    }

    public float getX() {
        return entityX;
    }

    public float getY() {
        return entityY;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Color getSpriteTint() {
        return spriteTint;
    }

    public void setSpriteTint(Color spriteTint) {
        this.spriteTint = spriteTint;
    }

    public String getFindRegion() {
        return findRegion;
    }

    public void setFindRegion(String findRegion) {
        this.findRegion = findRegion;
        textureRegion = textureAtlas.findRegion(this.findRegion);
        getSprite().setRegion(textureRegion);
    }

    public String getTextureAtlasPath() {
        return MainGame.playersAtlasPath;
    }

    public String getServerUniqueID() {
        return serverUniqueID;
    }

    public void setServerUniqueID(String serverUniqueID) {
        this.serverUniqueID = serverUniqueID;
    }

    public void drawAndUpdate(SpriteBatch batch) {
        if (batch == null || sprite == null || sprite.getTexture() == null) return;

        sprite.draw(batch);
    }

    public int getCompteurUp() {
        return compteurUp;
    }

    public void setCompteurUp(int compteurUp) {
        this.compteurUp = compteurUp;
    }

    public int getCompteurDown() {
        return compteurDown;
    }

    public void setCompteurDown(int compteurDown) {
        this.compteurDown = compteurDown;
    }

    public int getCompteurLeft() {
        return compteurLeft;
    }

    public void setCompteurLeft(int compteurLeft) {
        this.compteurLeft = compteurLeft;
    }

    public int getCompteurRight() {
        return compteurRight;
    }

    public void setCompteurRight(int compteurRight) {
        this.compteurRight = compteurRight;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
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

    private MainGame getGame() {
        return mainGame;
    }

    private void setGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    private void debug(ShapeRenderer renderer) {
        float relativeHitboxX = hitbox.x + GameScreen.getCamera().position.x; // - GameScreen.SCREEN_WIDTH / 2.0f
        float relativeHitboxY = hitbox.y + GameScreen.getCamera().position.y; //- GameScreen.SCREEN_HEIGHT / 2.0f

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.MAGENTA);
        renderer.rect(relativeHitboxX, relativeHitboxY, hitbox.width, hitbox.height);
        renderer.end();
    }
}
