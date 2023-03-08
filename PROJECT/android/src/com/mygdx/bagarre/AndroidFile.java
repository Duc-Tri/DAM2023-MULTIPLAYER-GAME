package com.mygdx.bagarre;

import android.content.res.AssetManager;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;

public class AndroidFile extends FileHandle {
    private AssetManager assetManager;

    public AndroidFile(AssetManager assetManager, String path, Files.FileType type) {
        super(path, type);
        this.assetManager = assetManager;
    }

    @Override
    public InputStream read() {
        try {
            return assetManager.open(file.getPath());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public byte[] readBytes() {
        try {
            InputStream is = assetManager.open(file.getPath());
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return file.getPath();
    }
}

