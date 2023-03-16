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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    String pseudo, oldPseudo, userID;
    int img, state = 0, musicPos;
    FirebaseAndroid db;
    SharedPreferences prefs;
    SharedPreferences.Editor editPref;
    Intent itGameMode;
    View grayedOutView;

    public void initUi(){
        tiPseudo = findViewById(R.id.tiPseudo);
        volumeBtn = findViewById(R.id.volumeBtn);
        registerBtn = findViewById(R.id.registerBtn);
        connexionBtn = findViewById(R.id.connexionBtn);
        ivBackground = findViewById(R.id.ivBackground);

    }

    public void playMusic() {
        //Création de l'audio lancher
        audioLauncher = MediaPlayer.create(this, R.raw.connexion_theme);
        audioLauncher.setLooping(true);
        audioLauncher.start();
        volumeBtn.setImageResource(R.drawable.volume_on);

        //Création de l'audio manager
        audioPlayer = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (audioPlayer.getStreamMaxVolume(AudioManager.STREAM_MUSIC)*0.5f), 0);
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

        //Check s'il y a des données dans les préférences
        pseudo = prefs.getString("pseudo", null);
        if(pseudo != null) {
            //Change ce qui est écris sur le bouton d'enregistrement
            registerBtn.setText(R.string.change_account);
            state = 1;
            Log.i("PREFS_PSEUDO", pseudo);
            userID = prefs.getString("userID", null);
            if(userID != null){
                Log.i("PREFS_USERID", userID);
            } else {
                Log.i("PREFS_USERID_NULL", "User ID est nul");
            }
            img = prefs.getInt("img", 0);
            if (img != 0) {
                Log.i("PREFS_IMG", String.valueOf(img));
            } else {
                Log.i("PREFS_IMG_NULL", "Il n'y a pas encore d'icone de profil enregistrée");
            }

            //Définition du text du bouton connexionBtn
            String textConnexionButton = "Continuer avec " + pseudo;

            // Chercher l'index où commence le pseudo
            int startIndex = textConnexionButton.indexOf(pseudo);

            // Ajouter un ForegroundColorSpan rouge autour du pseudo
            SpannableString pseudoRed = new SpannableString(textConnexionButton);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
            pseudoRed.setSpan(colorSpan, startIndex, startIndex + pseudo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //Je set mes modifications sur le bouton
            connexionBtn.setText(pseudoRed);
            connexionBtn.setVisibility(View.VISIBLE);
        } else {
            Log.i("PREF_IS_EMPTY", "Le fichier de préférence est vide");
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

    private final View.OnClickListener onClickGreyBackgroundListener = v -> onClickGreyBackground();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Initialisation des vues
        initUi();

        //Check de la preférence
        checkPref();

        //TEST : Vidage du fichier de préférence
        /*editPref.remove("pseudo");
        editPref.remove("userID");
        editPref.remove("img");
        editPref.apply();*/

        //On lance la musique
        playMusic();

        //Lancement de l'animation du fond
        gestionAnimation();

        //Connexion base
        connectBase();

        //TEST : Vidage de la référence pseudo
        /*db.deleteAllPseudos();*/

        //Appel du setOnClick pour le fond gris
        grayedOutView = new View(this);
        grayedOutView.setOnClickListener(onClickGreyBackgroundListener);

        //Création de l'intent pour le changement d'activité
        itGameMode = new Intent(LoginPage.this, GameMode.class);

    }

    public void continuer (View v){
        //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité
        itGameMode.putExtra("pseudoToDisplay", pseudo);
        musicPos = audioLauncher.getCurrentPosition();
        itGameMode.putExtra("isMuted", isMuted);
        itGameMode.putExtra("musicPos", musicPos);
        Log.i("MUSIC_POS_ACT_LOG", String.valueOf(musicPos));
        startActivity(itGameMode);
    }

    public void register (View v){

        switch(state) {
            case 0:
                //Dans ce cas il est affiché "S'enregistrer"

                //Réccupération du pseudo
                pseudo = Objects.requireNonNull(tiPseudo.getText()).toString();
                pseudo = pseudo.trim();

                //Test si le pseudo est nul
                if (pseudo.isEmpty()) {
                    Toast.makeText(this, "Le pseudo ne peut pas être vide ! Mettez au moins une lettre !", Toast.LENGTH_SHORT).show();
                } else {
                    //Enregistre des préférences pour la prochaine connexion
                    editPref.putString("pseudo", pseudo);

                    //Ecriture en base
                    editPref.putString("userID", db.registerUser(pseudo));
                    editPref.apply();

                    //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité
                    itGameMode.putExtra("pseudoToDisplay", pseudo);

                    //On réccupère le moment de la musique pour la prochaine activité
                    musicPos = audioLauncher.getCurrentPosition();
                    itGameMode.putExtra("isMuted", isMuted);
                    itGameMode.putExtra("musicPos", musicPos);
                    startActivity(itGameMode);
                }

                break;

            case 1:
                //Dans celui-ci il est affiché "Créer un nouveau compte"

                //Réccupération de l'ancien pseudo
                oldPseudo = pseudo;
                oldPseudo = oldPseudo.trim();

                //Réccupération du nouveau pseudo
                pseudo = Objects.requireNonNull(tiPseudo.getText()).toString();
                pseudo = pseudo.trim();

                //Test si le pseudo est nul
                if (pseudo.isEmpty()) {
                    Toast.makeText(this, "Le pseudo ne peut pas être vide ! Mettez au moins une lettre !", Toast.LENGTH_SHORT).show();
                } else {
                    dialogAndGrayBackground();
                }

                break;
        }


    }

    public void onClickGreyBackground() {
        grayedOutView.setVisibility(View.GONE);
    }

    public void dialogAndGrayBackground() {

        // Création de l'alerte builder avec le contexte de l'activité
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Créez un layout XML pour votre Dialog personnalisé
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_validation_deleting_account, null);

        // Définissez le layout XML comme vue pour le Dialog
        builder.setView(dialogView);

        // Créez le Dialog
        final AlertDialog dialog = builder.create();

        // Obtenez la View racine de l'activité
        View rootView = getWindow().getDecorView().getRootView();

        // Créez un layout XML pour le fond semi-transparent

        grayedOutView.setBackgroundColor(Color.parseColor("#88000000"));
        grayedOutView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Ajoutez le layout semi-transparent à la View racine de l'activité
        ((ViewGroup) rootView).addView(grayedOutView);
        grayedOutView.setVisibility(View.GONE);

        Log.i("OLDPSEUDO", oldPseudo);
        Log.i("PSEUDO", pseudo);

        TextView tvOldAccount = dialogView.findViewById(R.id.tvOldAccount);
        TextView tvNewAccount = dialogView.findViewById(R.id.tvNewAccount);

        //Je fais le setting des boutons
        tvOldAccount.setText(String.valueOf(oldPseudo));
        tvNewAccount.setText(String.valueOf(pseudo));

        dialog.show();
        grayedOutView.setVisibility(View.VISIBLE);

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

    public void onOldPseudoClicked (View v) {
        //Enregistre des préférences pour la prochaine connexion
        editPref.putString("pseudo", oldPseudo);
        editPref.apply();

        //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité et l'état de la musique
        itGameMode.putExtra("pseudoToDisplay", oldPseudo);
        musicPos = audioLauncher.getCurrentPosition();
        itGameMode.putExtra("isMuted", isMuted);
        itGameMode.putExtra("musicPos", musicPos);
        startActivity(itGameMode);
    }

    public void onNewPseudoClicked (View v) {
        //Enregistre des préférences pour la prochaine connexion
        editPref.putString("pseudo", pseudo);
        editPref.apply();

        //Supprime l'ancien pseudo de la base de donnée
        db.suppressUnusedPseudo(prefs.getString("userID", null), oldPseudo);

        //Ecriture en base et mise a jour des préférences
        editPref.putString("userID", db.registerUser(pseudo));
        editPref.apply();

        //Changement d'activité en envoyant le pseudo pour l'afficher dans la prochaine activité et l'état de la musique
        itGameMode.putExtra("pseudoToDisplay", pseudo);
        musicPos = audioLauncher.getCurrentPosition();
        Log.i("MUSIC_POS_ACT_LOG", String.valueOf(musicPos));
        itGameMode.putExtra("isMuted", isMuted);
        itGameMode.putExtra("musicPos", musicPos);
        startActivity(itGameMode);
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