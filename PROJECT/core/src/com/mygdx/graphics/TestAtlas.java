package com.mygdx.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class TestAtlas extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;

    TextureAtlas textureAtlas;
    private String playersAtlasPath = "characters/RMXP_humans.atlas";
    private String monstersAtlasPath = "characters/RMXP_monsters.atlas";

    int SCREEN_WIDTH, SCREEN_HEIGHT;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1800, 1000);
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal(monstersAtlasPath));
    }

    @Override
    public void render() {
        renderMonsters();
    }


    public void renderMonsters() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        batch.begin();

        int posX = 0;
        int posY = 0;

        for (int nchar = 0; nchar < RMXPMonstersAtlas.MAX_MONSTERS; nchar++) {

            for (int dir = 0; dir < RMXPMonstersAtlas.DIRS.length; dir++) {

                for (int frame = 0; frame < RMXPMonstersAtlas.ANIM_FRAMES; frame++) {

//                    if (frame == 0 || frame == 2)                        continue; // on saute les iddles pour en afficher moins ...

                    String region = nchar + "_" + RMXPMonstersAtlas.DIRS[dir] + "_" + frame;
                    TextureAtlas.AtlasRegion textureRegion = textureAtlas.findRegion(region);

                    System.out.println(region);

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

        batch.end();
    }

    public void renderPlayers() {
        ScreenUtils.clear(0.4f, 0.4f, 0.4f, 1);

        batch.begin();

        int posX = 0;
        int posY = 0;

        for (int nchar = 0; nchar < RMXPCharactersAtlas.MAX_CHARACTERS; nchar++) {

            for (int dir = 0; dir < RMXPCharactersAtlas.DIRS.length; dir++) {

                for (int frame = 0; frame < RMXPCharactersAtlas.ANIM_FRAMES; frame++) {

                    if (frame == 0 || frame == 2)
                        continue; // on saute les iddles pour en afficher moins ...

                    String region = nchar + "_" + RMXPCharactersAtlas.DIRS[dir] + "_" + frame;
                    TextureAtlas.AtlasRegion textureRegion = textureAtlas.findRegion(region);

                    // frame==0 : IDDLE
                    //((frame == 0) ? "IDDLE" : (frame - 1)));

                    //batch.draw(textureRegion, (nchar + dir + frame) * 16, (nchar + dir + frame) * 24);
                    batch.draw(textureRegion, posX, posY);

                    posX += RMXPCharactersAtlas.FRAME_WIDTH;
                    if (posX > SCREEN_WIDTH) {
                        posX = 0;
                        posY += RMXPCharactersAtlas.FRAME_HEIGHT;
                    }
                }

            }

        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
}