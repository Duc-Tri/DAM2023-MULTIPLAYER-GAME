package com.mygdx.bagarre;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class BeginningAnimation extends AppCompatActivity {

    ImageView blackbackgroundscenic, ivBackground1, ivBackground2, left_guy, right_guy, titre_bagarre, touch_screen;
    Animation fadeInBackground, fadeInBackground2, characterFadeOut, titreFadeIn, scaleTitle, touchScreenFadeIn, touchScreenFadeOut;
    AnimationSet titleAnimSet, blinkTouchScreen;
    long timeFadeInFirstBackground, timeFadeInLight, timeTitleApparition, timeTouchScreen;
    boolean clickable = false;

    public void initUI() {
        blackbackgroundscenic = findViewById(R.id.blackbackgroundscenic);
        ivBackground1 = findViewById(R.id.ivBackground1);
        ivBackground2 = findViewById(R.id.ivBackground2);
        titre_bagarre = findViewById(R.id.titre_bagarre);
        touch_screen = findViewById(R.id.touch_screen);
        right_guy = findViewById(R.id.right_guy);
        left_guy = findViewById(R.id.left_guy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning_animation);

        //Récupération de la taille de l'écran
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = metrics.widthPixels;
        float screenHeight = metrics.heightPixels;
        Log.i("SCREEN_DIMENSION", screenWidth + "x" + screenHeight);

        initUI();

        //====================== Determination des timing de l'animation ======================

        //Temps de l'apparition du premier background
        timeFadeInFirstBackground = 4000;

        //Temps de l'apparition de la lumière
        timeFadeInLight = 3000;

        //Temps de l'animation du titre
        timeTitleApparition = 3000;

        //====================== Determination des timing de l'animation ======================

        //Instantie les des gars hors de l'écran
        left_guy.setTranslationX(- (screenWidth/2));
        right_guy.setTranslationX(screenWidth/2);

        //Fade-in du premier background
        fadeInBackground = new AlphaAnimation(0, 1);
        fadeInBackground.setInterpolator(new DecelerateInterpolator());
        fadeInBackground.setDuration(timeFadeInFirstBackground);

        //Fade-in du second background
        fadeInBackground2 = new AlphaAnimation(0, 1);
        fadeInBackground2.setInterpolator(new DecelerateInterpolator());
        fadeInBackground2.setDuration(timeFadeInLight);
        fadeInBackground2.setStartOffset(timeFadeInFirstBackground);

        //Fadeout des personnages
        characterFadeOut = new AlphaAnimation(1, 0);
        characterFadeOut.setInterpolator(new DecelerateInterpolator());
        characterFadeOut.setDuration(timeFadeInLight/5);
        characterFadeOut.setStartOffset((timeFadeInFirstBackground + timeFadeInLight) - timeFadeInLight/5);
        characterFadeOut.setFillAfter(true);

        //Fade-in du titre
        titreFadeIn = new AlphaAnimation(0,1);
        titreFadeIn.setInterpolator(new DecelerateInterpolator());

        //Scale du titre
        scaleTitle = new ScaleAnimation(
                0.1f,1f,
                0.1f,1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleTitle.setInterpolator(new DecelerateInterpolator());
        scaleTitle.setFillAfter(true);

        //Merge de l'animation pour le titre
        titleAnimSet = new AnimationSet(false);
        titleAnimSet.setStartOffset(timeFadeInFirstBackground + timeFadeInLight);
        titleAnimSet.setDuration(timeTitleApparition);
        titleAnimSet.addAnimation(titreFadeIn);
        titleAnimSet.addAnimation(scaleTitle);

        //Blink du touch screen
        touchScreenFadeIn = new AlphaAnimation(0, 1);
        touchScreenFadeIn.setDuration(500);
        touchScreenFadeIn.setInterpolator(new DecelerateInterpolator());
        touchScreenFadeOut = new AlphaAnimation(1, 0);
        touchScreenFadeOut.setDuration(500);
        touchScreenFadeOut.setInterpolator(new DecelerateInterpolator());

        blinkTouchScreen = new AnimationSet(false);
        blinkTouchScreen.setStartOffset(timeFadeInFirstBackground + timeFadeInLight + timeTitleApparition);
        blinkTouchScreen.addAnimation(touchScreenFadeIn);
        blinkTouchScreen.addAnimation(touchScreenFadeOut);

        //Set de l'animation des fonds
        ivBackground1.setAnimation(fadeInBackground);
        ivBackground2.setAnimation(fadeInBackground2);

        //Set de l'animation du mec de gauche
        left_guy.animate()
                .translationX(screenWidth/17)
                .setStartDelay(timeFadeInFirstBackground/2 - 500)
                .setDuration(timeFadeInFirstBackground/2);
        left_guy.setAnimation(characterFadeOut);

        //Set de l'animation du mec de droite
        right_guy.animate()
                .translationX((- (screenWidth)/17))
                .setStartDelay(timeFadeInFirstBackground/2 - 500)
                .setDuration(timeFadeInFirstBackground/2);
        right_guy.setAnimation(characterFadeOut);

        //Set animation du titre
        titre_bagarre.setAnimation(titleAnimSet);

        //Set animation du touch screen
        touch_screen.setAnimation(blinkTouchScreen);

        titleAnimSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clickable = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        }

        public void nextAct(View v) {
            if (clickable) {
                Intent itConnexion = new Intent(BeginningAnimation.this, LoginPage.class);
                startActivity(itConnexion);
            }
        }

    }