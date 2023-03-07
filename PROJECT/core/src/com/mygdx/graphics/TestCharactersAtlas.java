package com.mygdx.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class TestCharactersAtlas extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;

    TextureAtlas textureAtlas;
    private String textureAtlasPath = "characters/RMXP_humans.atlas";

    int SCREEN_WIDTH, SCREEN_HEIGHT;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1800, 1000 );
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal(textureAtlasPath));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        batch.begin();

        int posX = 0;
        int posY = 0;

        for (int nchar = 0; nchar < RMXPCharactesAtlasGenerator.MAX_CHARACTERS; nchar++) {

            for (int dir = 0; dir < RMXPCharactesAtlasGenerator.DIRS.length; dir++) {

                for (int frame = 0; frame < RMXPCharactesAtlasGenerator.ANIM_FRAMES + 1; frame++) {

                    if(frame!=2) // on saute la frame 2
                    {
                        TextureAtlas.AtlasRegion textureRegion = textureAtlas.findRegion(nchar + "_" +

                                RMXPCharactesAtlasGenerator.DIRS[dir] + "_" +

                                // frame==0 : IDDLE
                                ((frame == 0) ? "IDDLE" : (frame - 1)));

                        //batch.draw(textureRegion, (nchar + dir + frame) * 16, (nchar + dir + frame) * 24);
                        batch.draw(textureRegion, posX, posY);

                        posX += RMXPCharactesAtlasGenerator.FRAME_WIDTH;
                        if (posX > SCREEN_WIDTH) {
                            posX = 0;
                            posY += RMXPCharactesAtlasGenerator.FRAME_HEIGHT;
                        }
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