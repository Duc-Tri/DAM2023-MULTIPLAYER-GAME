package com.mygdx.bagarre;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {
    AudioManager audioPlayer;
    MediaPlayer audioLauncher;
    private Button registerBtn, connexionBtn;
    private ImageButton volumeBtn;
    private TextInputEditText tiPseudo;
    private boolean isMuted = false;
    private ImageView ivBackground;
    String pseudo;
    FirebaseAndroid db;
    SharedPreferences prefs;
    SharedPreferences.Editor editPref;
    Intent itGameMode;

    public void initUi(){
        registerBtn = findViewById(R.id.registerBtn);
        tiPseudo = findViewById(R.id.tiPseudo);
        connexionBtn = findViewById(R.id.connexionBtn);
        ivBackground = findViewById(R.id.ivBackground);
        volumeBtn = findViewById(R.id.volumeBtn);
    }

    public void playMusic() {
        //Création de l'audio lancher
        audioLauncher = MediaPlayer.create(this, R.raw.connexion_theme);
        audioLauncher.setLooping(true);
        audioLauncher.start();
        volumeBtn.setImageResource(R.drawable.volume_on);

        //Création de l'audio manager
        audioPlayer = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, (int) ((audioPlayer.getStreamMaxVolume(AudioManager.STREAM_MUSIC))*0.5f), 0);
    }

    public void connectBase() {
        //Initialisation de Firebase
        db = new FirebaseAndroid("https://test-e782f-default-rtdb.europe-west1.firebasedatabase.app", "json/keyTest782f.json", this);

        //Test de connexion
        if(db.connect()) {
            Log.i("CONNEXION_ENABLED", "La connexion a étée établie");
        }
    }

    public void checkPref() {

        //Sharedreferences et son editor
        prefs = getSharedPreferences("pref_pseudo", MODE_PRIVATE);
        editPref = prefs.edit();

        //S'il y a besoin de test sans les préférences
        /*editPref.remove("pseudo");
        editPref.apply();*/

        //Check s'il y a des données dans les préférences
        pseudo = prefs.getString("pseudo", null);
        if(pseudo != null) {
            //Change ce qui est écris sur le bouton d'enregistrement
            registerBtn.setText(R.string.change_account);

            //Affiche le bouton Continuer avec
            String textConnexionButton = "Continuer avec " + pseudo;
            SpannableString pseudoRed = new SpannableString(textConnexionButton);

            // Chercher l'index où commence le pseudo
            int startIndex = textConnexionButton.indexOf(pseudo);

            // Ajouter un ForegroundColorSpan rouge autour du pseudo
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
            pseudoRed.setSpan(colorSpan, startIndex, startIndex + pseudo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //Je set mes modifications sur le bouton
            connexionBtn.setText(pseudoRed);
            connexionBtn.setVisibility(View.VISIBLE);
        }
    }

    public void gestionAnimation() {
        // Obtenez la taille de l'écran en pixels
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float screenWidth = metrics.widthPixels;
        Log.i("SCREEN_WIDTH", String.valueOf(screenWidth));

        //Gestion de l'animation
        ivBackground.setTranslationY(-10);
        ivBackground.setTranslationX((float) (screenWidth*1.3));
        ivBackground.animate()
                .translationX((float) (-screenWidth*1.3))
                .setDuration(100000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ivBackground.setTranslationX((float) (screenWidth*1.3));
                        ivBackground.animate()
                                .translationX((float) (-screenWidth*1.3))
                                .setDuration(100000)
                                .start();
                    }
                })
                .start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Initialisation des vues
        initUi();

        //On lance la musique
        playMusic();

        //Lancement de l'animation du fond
        gestionAnimation();

        //Connexion base
        connectBase();

        //Check de la preférence
        checkPref();

        //Création de l'intent pour le changement d'activité
        itGameMode = new Intent(LoginPage.this, GameMode.class);

    }

    public void continuer (View v){
        //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité
        itGameMode.putExtra("pseudoToDisplay", pseudo);
        startActivity(itGameMode);
    }

    public void register (View v){
        //Réccupération du pseudo
        pseudo = Objects.requireNonNull(tiPseudo.getText()).toString();

        //Enregistre des préférences pour la prochaine connexion
        editPref.putString("pseudo", pseudo);
        editPref.apply();

        //Ecriture en base
        db.registerUser(pseudo);

        //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité
        itGameMode.putExtra("pseudoToDisplay", pseudo);
        startActivity(itGameMode);
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

    @Override
    protected void onPause() {
        super.onPause();

        // Mettre en pause la lecture de la musique
        audioLauncher.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Arrêter et libérer les ressources de l'objet MediaPlayer
        audioLauncher.stop();
        audioLauncher.release();
    }
}
