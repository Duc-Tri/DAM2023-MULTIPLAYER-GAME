package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.graphics.RMXPCharactersAtlas;
import com.mygdx.graphics.RMXPMonstersAtlas;

//=============================================================================
// Test des atlas générés (players, monsters, ...)
//=============================================================================
public class TestAtlas extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private final static String playersAtlasPath = "characters/RMXP_humans.atlas";
    private final static String monstersAtlasPath = "characters/RMXP_monsters.atlas";
    private TextureAtlas atlasPlayers, atlasMonsters;

    int SCREEN_WIDTH, SCREEN_HEIGHT;
    boolean switchAtlas;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.graphics.setWindowedMode(1800, 1000);
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        atlasMonsters = new TextureAtlas(Gdx.files.internal(monstersAtlasPath));
        atlasPlayers = new TextureAtlas(Gdx.files.internal(playersAtlasPath));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        batch.begin();

        if (switchAtlas) {
            renderMonsters();
        } else {
            renderPlayers();
        }

        batch.end();
    }


    public void renderMonsters() {

        int posX = 0;
        int posY = 0;

        for (int nchar = 0; nchar < RMXPMonstersAtlas.MAX_MONSTERS; nchar++) {

            for (int dir = 0; dir < RMXPMonstersAtlas.DIRS.length; dir++) {

                for (int frame = 0; frame < RMXPMonstersAtlas.ANIM_FRAMES; frame++) {

                    String region = nchar + "_" + RMXPMonstersAtlas.DIRS[dir] + "_" + frame;
                    TextureAtlas.AtlasRegion textureRegion = atlasMonsters.findRegion(region);

                    //batch.draw(textureRegion, (nchar + dir + frame) * 16, (nchar + dir + frame) * 24);
                    batch.draw(textureRegion, posX, posY);

                    posX += textureRegion.getRegionWidth();
                    if (posX + 16 > SCREEN_WIDTH) {
                        posX = 0;
                        posY += textureRegion.getRegionHeight();
                    }
                }

            }

        }
    }

    public void renderPlayers() {
        int posX = 0;
        int posY = 0;

        for (int nchar = 0; nchar < RMXPCharactersAtlas.MAX_CHARACTERS; nchar++) {

            for (int dir = 0; dir < RMXPCharactersAtlas.DIRS.length; dir++) {

                for (int frame = 0; frame < RMXPCharactersAtlas.ANIM_FRAMES; frame++) {

                    if (frame == 0 || frame == 2)
                        continue; // on saute les poses "iddles" pour en afficher moins ...

                    String region = nchar + "_" + RMXPCharactersAtlas.DIRS[dir] + "_" + frame;
                    TextureAtlas.AtlasRegion textureRegion = atlasPlayers.findRegion(region);

                    batch.draw(textureRegion, posX, posY);

                    posX += RMXPCharactersAtlas.FRAME_WIDTH;
                    if (posX + RMXPCharactersAtlas.FRAME_WIDTH > SCREEN_WIDTH) {
                        posX = 0;
                        posY += RMXPCharactersAtlas.FRAME_HEIGHT;
                    }
                }

            }

        }
    }

    private void switchAtlas() {
        switchAtlas = !switchAtlas;

        System.out.println("switchAtlas " + switchAtlas);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switchAtlas();
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switchAtlas();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}