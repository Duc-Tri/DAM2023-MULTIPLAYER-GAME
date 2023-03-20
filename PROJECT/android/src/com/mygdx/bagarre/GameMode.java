package com.mygdx.bagarre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameMode extends AppCompatActivity {
    TextView tvPseudo;
    ImageView ivPseudo, background;
    Spinner imageSp;
    String userID;
    Button buttonSolo, buttonOnLine;
    List<ImageItem> imgList = new ArrayList<>();
    MediaPlayer audioLauncher;
    Intent itGameMode;
    ImageButton volumeBtn2;
    boolean isMuted = false;
    int musicPos;

    @SuppressLint("WrongViewCast")
    public void initUi() {
        tvPseudo = findViewById(R.id.tvPseudo);
        ivPseudo = findViewById(R.id.ivPseudo);
        imageSp = findViewById(R.id.imageSp);
        buttonSolo = findViewById(R.id.soloBtn);
        buttonOnLine = findViewById(R.id.soloOnLine);
        background = findViewById(R.id.background);
        volumeBtn2 = findViewById(R.id.volumeBtn2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        initUi();
        itGameMode = getIntent();

        SharedPreferences prefs = getSharedPreferences("pref_pseudo", this.MODE_PRIVATE);
        SharedPreferences.Editor editPref = prefs.edit();

        audioLauncher = MediaPlayer.create(this, R.raw.connexion_theme);
        musicPos = itGameMode.getIntExtra("musicPos", 0);
        Log.i("MUSIC_POS_ACT_GM", String.valueOf(musicPos));
        audioLauncher.seekTo(musicPos);
        audioLauncher.setLooping(true);
        audioLauncher.start();

        //Création de l'audio manager
        AudioManager audioPlayer = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (audioPlayer.getStreamMaxVolume(AudioManager.STREAM_MUSIC)*0.5f), 0);

        Log.i("IS_MUTED", String.valueOf(itGameMode.getBooleanExtra("isMuted", false)));
        isMuted = itGameMode.getBooleanExtra("isMuted", false);
        if (isMuted) {
            volumeBtn2.setImageResource(R.drawable.volume_off);
            audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        } else {
            volumeBtn2.setImageResource(R.drawable.volume_on);
        }

        volumeBtn2.setOnClickListener(v -> {
            //Vérifie si la musique est mute et la mute si ce n'est pas le cas. La demute si c'est le cas.
            if(isMuted) {
                audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, (int) ((audioPlayer.getStreamMaxVolume(AudioManager.STREAM_MUSIC))*0.5f), 0);
                volumeBtn2.setImageResource(R.drawable.volume_on);

                isMuted = false;
            } else {
                audioPlayer.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                volumeBtn2.setImageResource(R.drawable.volume_off);

                isMuted = true;
            }
        });



        userID = prefs.getString("userID", null);
        if(userID != null){
            Log.i("PREFS_USERID", userID);
        } else {
            Log.i("PREFS_USERID_NULL", "User ID est nul");
        }

        int idImg = prefs.getInt("img", 0);
        Log.i("PREFS_IMG", String.valueOf(idImg));
        if(idImg != 0) {
            ivPseudo.setImageResource(idImg);
            Log.i("PREFS_IMG_NULL", "Il n'y a pas encore d'icone de profil enregistrée");
        }

        imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
        imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
        imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
        imgList.add(new ImageItem(R.drawable.profil4, "profil4"));

        //Création d'un adaptater avec notre nouvelle classe ImageAdapter
//        ImageAdapter adapter = new ImageAdapter(this, R.layout.spinner_item, imgList);
//        imageSp.setAdapter(adapter);

        //Récupération du pseudo depuis la précédente application et l'afficher a la place de pseudo

        if (itGameMode != null) {
            if (itGameMode.hasExtra("pseudoToDisplay")) {
                tvPseudo.setText(itGameMode.getStringExtra("pseudoToDisplay"));
            }
        }

        ivPseudo.setOnClickListener(v -> imageSp.setVisibility(View.VISIBLE));

        imageSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int resourceId = getResourceIdForSelectedImage(position);
                Log.i("RESOURCE_ID", String.valueOf(position));
                ivPseudo.setImageResource(resourceId);
                editPref.putInt("img", resourceId);
                editPref.apply();
                imageSp.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageSp.setVisibility(View.INVISIBLE);
            }
        });

        buttonSolo.setOnClickListener(v -> {
            Intent itGame = new Intent(GameMode.this, AndroidLauncher.class);
            startActivity(itGame);
        });


        buttonOnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameMode.this,MultiJoueurActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // Méthode pour récupérer la ressource ID de l'image sélectionnée
    private int getResourceIdForSelectedImage(int position) {
        switch (position) {
            case 0:
                return R.drawable.profil1;
            case 1:
                return R.drawable.profil2;
            case 2:
                return R.drawable.profil3;
            case 3:
                return R.drawable.profil4;
            default:
                return -1;
        }
    }

    private List<ImageItem> getList (int choice) {

        switch(choice) {
            case 2131230883:
                imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
                imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
                imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
                imgList.add(new ImageItem(R.drawable.profil4, "profil4"));
                break;
            case 2131230884:
                imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
                imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
                imgList.add(new ImageItem(R.drawable.profil4, "profil4"));
                imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
                break;
            case 2131230885:
                imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
                imgList.add(new ImageItem(R.drawable.profil4, "profil4"));
                imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
                imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
                break;
            case 2131230886:
                imgList.add(new ImageItem(R.drawable.profil4, "profil4"));
                imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
                imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
                imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
                break;
        }

        return imgList;
    }

    @Override
    protected void onPause() {
        super.onPause();
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