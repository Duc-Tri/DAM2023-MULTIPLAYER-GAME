package com.mygdx.bagarre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mygdx.client.NewPlayer;

public class BeginningAnimation extends AppCompatActivity {

    ImageView light_left_guy, light_right_guy, left_guy, right_guy, titre_bagarre, touch_screen;
    ImageButton volumeBtn;
    Animation fadeInBackground, fadeInLight, fadeInCharaLight, characterFadeOut, titreFadeIn, scaleTitle, touchScreenFadeIn, touchScreenFadeOut;
    AnimationSet titleAnimSet, blinkTouchScreen;
    ConstraintLayout clBackground;
    FrameLayout flBackLight;
    MediaPlayer audioLauncher;
    AudioManager audioPlayer;
    SharedPreferences prefs;
    SharedPreferences.Editor editPref;
    long timeFadeInFirstBackground, timeFadeInLight, timeTitleApparition, timeAnimationDude;
    boolean clickable = false, isMuted;
    int musicPos;

    public void initUI() {
        flBackLight = findViewById(R.id.flBackLight);
        clBackground = findViewById(R.id.clBackground);
        titre_bagarre = findViewById(R.id.titre_bagarre);
        touch_screen = findViewById(R.id.touch_screen);
        right_guy = findViewById(R.id.right_guy);
        left_guy = findViewById(R.id.left_guy);
        light_left_guy = findViewById(R.id.light_left_guy);
        light_right_guy = findViewById(R.id.light_right_guy);
        volumeBtn = findViewById(R.id.volumeBtn3);
    }

    public void playMusic() {
        //Création de l'audio lancher
        audioLauncher = MediaPlayer.create(this, R.raw.ken);
        audioLauncher.setLooping(true);
        audioLauncher.start();
        volumeBtn.setImageResource(R.drawable.volume_on);

        //Création de l'audio manager
        audioPlayer = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Sharedreferences et son editor
        prefs = getSharedPreferences("pref_pseudo", MODE_PRIVATE);
        editPref = prefs.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        playMusic();

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

    }

    public void toggleMute(View v){

        //Vérifie si la musique est mute et la mute si ce n'est pas le cas. La demute si c'est le cas.
        if(isMuted) {
            audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, (int) ((audioPlayer.getStreamMaxVolume(AudioManager.STREAM_MUSIC))*0.5f), 0);
            volumeBtn.setImageResource(R.drawable.volume_on);

            isMuted = false;
        } else {
            audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            volumeBtn.setImageResource(R.drawable.volume_off);

            isMuted = true;
        }
    }

    public void nextAct(View v) {
        if (clickable) {
            Intent itConnexion = new Intent(BeginningAnimation.this, LoginPage.class);
            musicPos = audioLauncher.getCurrentPosition();
            itConnexion.putExtra("isMuted", isMuted);
            itConnexion.putExtra("musicPos", musicPos);
            startActivity(itConnexion);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioLauncher.seekTo(musicPos);
        audioLauncher.setLooping(true);
        audioLauncher.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioLauncher.pause();
        musicPos = audioLauncher.getCurrentPosition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioLauncher.stop();
        audioLauncher.release();
    }
}