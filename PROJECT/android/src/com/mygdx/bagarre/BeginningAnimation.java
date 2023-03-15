package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class BeginningAnimation extends AppCompatActivity {

    ImageView blackbackgroundscenic, ivBlackground1, ivBlackground2, left_guy, right_guy, titre_bagarre, touch_screen;

    public void initUI() {
        blackbackgroundscenic = findViewById(R.id.blackbackgroundscenic);
        ivBlackground1 = findViewById(R.id.ivBackground1);
        ivBlackground2 = findViewById(R.id.ivBackground2);
        titre_bagarre = findViewById(R.id.titre_bagarre);
        touch_screen = findViewById(R.id.touch_screen);
        right_guy = findViewById(R.id.right_guy);
        left_guy = findViewById(R.id.left_guy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning_animation);

        initUI();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = metrics.widthPixels;

        left_guy.setTranslationX(- (screenWidth/2));
        right_guy.setTranslationX(screenWidth/2);
        left_guy
    }
}