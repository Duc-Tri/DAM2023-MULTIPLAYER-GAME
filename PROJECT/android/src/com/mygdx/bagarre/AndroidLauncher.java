package com.mygdx.bagarre;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainGame.setConfig("android");

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(MainGame.getInstance(), config);
    }
}
