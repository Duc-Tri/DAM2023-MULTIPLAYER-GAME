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
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mygdx.client.NewPlayer;

public class BeginningAnimation extends AppCompatActivity {

    ImageView light_left_guy, light_right_guy, left_guy, right_guy, titre_bagarre, touch_screen;
    Animation fadeInBackground, fadeInLight, fadeInCharaLight, characterFadeOut, titreFadeIn, scaleTitle, touchScreenFadeIn, touchScreenFadeOut;
    AnimationSet titleAnimSet, blinkTouchScreen;
    ConstraintLayout clBackground;
    FrameLayout flBackLight;
    long timeFadeInFirstBackground, timeFadeInLight, timeTitleApparition, timeAnimationDude;
    int tLeftGuy, tRightGuy;
    float coeffCharaLeft = 1.873f, coeffCharaRight = 1.781f;
    boolean clickable = false;

    public void initUI() {
        flBackLight = findViewById(R.id.flBackLight);
        clBackground = findViewById(R.id.clBackground);
        titre_bagarre = findViewById(R.id.titre_bagarre);
        touch_screen = findViewById(R.id.touch_screen);
        right_guy = findViewById(R.id.right_guy);
        left_guy = findViewById(R.id.left_guy);
        light_left_guy = findViewById(R.id.light_left_guy);
        light_right_guy = findViewById(R.id.light_right_guy);
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
        float screenDP = metrics.densityDpi;

        Log.i("SCREEN_DENSITY", String.valueOf(screenDP));
        Log.i("SCREEN_DIMENSION", screenWidth + "x" + screenHeight);

        initUI();

        //====================== Determination des timing de l'animation ======================

        //Temps de l'apparition du premier background
        timeFadeInFirstBackground = 4000;

        //Temps de l'apparition de la lumière
        timeFadeInLight = 3000;

        //Temps de l'animation du titre
        timeTitleApparition = 3000;

        //Temps de l'animation des personnages
        timeAnimationDude = 3000;

        //====================== Determination des timing de l'animation ======================

        //Sort les dudes de l'écran
        left_guy.setTranslationX(-screenWidth);
        right_guy.setTranslationX(screenWidth);

        //Animation du fond
        fadeInBackground = new AlphaAnimation(0, 1);
        fadeInBackground.setInterpolator(new DecelerateInterpolator());
        fadeInBackground.setDuration(timeFadeInFirstBackground);
        clBackground.setAnimation(fadeInBackground);

        fadeInLight = new AlphaAnimation(0, 1);
        fadeInLight.setInterpolator(new DecelerateInterpolator());
        fadeInLight.setDuration(timeFadeInLight);
        fadeInLight.setStartOffset(timeFadeInFirstBackground + timeAnimationDude);

        fadeInCharaLight = new AlphaAnimation(0, 1);
        fadeInCharaLight.setInterpolator(new DecelerateInterpolator());
        fadeInCharaLight.setDuration(timeFadeInLight);
        fadeInCharaLight.setStartOffset(timeFadeInFirstBackground + timeAnimationDude);

        //Animation des dude
        left_guy.animate()
                .translationX(0)
                .setDuration(3000)
                .setStartDelay(timeFadeInFirstBackground);
        right_guy.animate()
                .translationX(0)
                .setDuration(3000)
                .setStartDelay(timeFadeInFirstBackground);

        //Fade-in du titre
        titreFadeIn = new AlphaAnimation(0, 1);
        titreFadeIn.setInterpolator(new DecelerateInterpolator());
        //Scale du titre
        scaleTitle = new ScaleAnimation(
                0.1f, 1f,
                0.1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleTitle.setInterpolator(new DecelerateInterpolator());
        scaleTitle.setFillAfter(true);

        //Merge de l'animation pour le titre
        titleAnimSet = new AnimationSet(false);
        titleAnimSet.setDuration(timeTitleApparition);
        titleAnimSet.addAnimation(titreFadeIn);
        titleAnimSet.addAnimation(scaleTitle);

        //Animation du touch screen
        touchScreenFadeIn = new AlphaAnimation(0, 1);
        touchScreenFadeIn.setInterpolator(new DecelerateInterpolator());
        touchScreenFadeIn.setDuration(1000);
        touchScreenFadeIn.setFillAfter(true);

        touchScreenFadeOut = new AlphaAnimation(1, 0);
        touchScreenFadeOut.setInterpolator(new DecelerateInterpolator());
        touchScreenFadeOut.setDuration(500);
        touchScreenFadeOut.setFillAfter(true);

        fadeInBackground.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flBackLight.setAnimation(fadeInLight);
                light_left_guy.setAnimation(fadeInCharaLight);
                light_right_guy.setAnimation(fadeInCharaLight);
                light_right_guy.setVisibility(View.VISIBLE);
                light_left_guy.setVisibility(View.VISIBLE);
                flBackLight.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeInLight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                titre_bagarre.setAnimation(titleAnimSet);
                titre_bagarre.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        titleAnimSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                touch_screen.setAnimation(touchScreenFadeIn);
                touch_screen.setVisibility(View.VISIBLE);
                clickable = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        touchScreenFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                touch_screen.setAnimation(touchScreenFadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        touchScreenFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                touch_screen.setAnimation(touchScreenFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


//
//        //Fadeout des personnages
//        characterFadeOut = new AlphaAnimation(1, 0);
//        characterFadeOut.setInterpolator(new DecelerateInterpolator());
//        characterFadeOut.setDuration(timeFadeInLight/5);
//        characterFadeOut.setStartOffset((timeFadeInFirstBackground + timeFadeInLight) - timeFadeInLight/5);
//        characterFadeOut.setFillAfter(true);
//

//

//

//
//        //Blink du touch screen
//        touchScreenFadeIn = new AlphaAnimation(0, 1);
//        touchScreenFadeIn.setDuration(500);
//        touchScreenFadeIn.setInterpolator(new DecelerateInterpolator());
//        touchScreenFadeOut = new AlphaAnimation(1, 0);
//        touchScreenFadeOut.setDuration(500);
//        touchScreenFadeOut.setInterpolator(new DecelerateInterpolator());
//
//        blinkTouchScreen = new AnimationSet(false);
//        blinkTouchScreen.setStartOffset(timeFadeInFirstBackground + timeFadeInLight + timeTitleApparition);
//        blinkTouchScreen.addAnimation(touchScreenFadeIn);
//        blinkTouchScreen.addAnimation(touchScreenFadeOut);
//
//        //Set de l'animation des fonds
//        ivBackground1.setAnimation(fadeInBackground);
//        ivBackground2.setAnimation(fadeInBackground2);
//
//        //Set de l'animation du mec de gauche
//        left_guy.animate()
//                .translationX(tLeftGuy*3)
//                .setStartDelay(timeFadeInFirstBackground/2 - 500)
//                .setDuration(timeFadeInFirstBackground/2);
//        left_guy.setAnimation(characterFadeOut);
//
//        //Set de l'animation du mec de droite
//        right_guy.animate()
//                .translationX(- (tRightGuy*3))
//                .setStartDelay(timeFadeInFirstBackground/2 - 500)
//                .setDuration(timeFadeInFirstBackground/2);
//        right_guy.setAnimation(characterFadeOut);
//
//        //Set animation du titre
//        titre_bagarre.setAnimation(titleAnimSet);
//
//        //Set animation du touch screen
//        touch_screen.setAnimation(blinkTouchScreen);
//
//        titleAnimSet.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                clickable = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//
//        }
//

        }

    public void nextAct(View v) {
        if (clickable) {
            Intent itConnexion = new Intent(BeginningAnimation.this, LoginPage.class);
            startActivity(itConnexion);
        }
    }

    }