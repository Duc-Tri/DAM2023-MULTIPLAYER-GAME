package com.mygdx.bagarre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameMode extends AppCompatActivity {
    TextView tvPseudo;
    ImageView ivPseudo;
    Spinner imageSp;

    ImageButton buttonSolo;

    List<ImageItem> imgList = new ArrayList<>();

    public void initUi() {
        tvPseudo = findViewById(R.id.tvPseudo);
        ivPseudo = findViewById(R.id.ivPseudo);
        imageSp = findViewById(R.id.imageSp);
        buttonSolo = findViewById(R.id.soloBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        initUi();

        SharedPreferences prefs = getSharedPreferences("pref_pseudo", this.MODE_PRIVATE);
        SharedPreferences.Editor editPref = prefs.edit();

        int idImg = prefs.getInt("img", 0);
        Log.i("IDIMG_PREF", String.valueOf(idImg));
        if(idImg != 0) {
            ivPseudo.setImageResource(idImg);
        }

        imgList.add(new ImageItem(R.drawable.profil1, "profil1"));
        imgList.add(new ImageItem(R.drawable.profil2, "profil2"));
        imgList.add(new ImageItem(R.drawable.profil3, "profil3"));
        imgList.add(new ImageItem(R.drawable.profil4, "profil4"));

        //Création d'un adaptater avec notre nouvelle classe ImageAdapter
        ImageAdapter adapter = new ImageAdapter(this, R.layout.spinner_item, imgList);
        imageSp.setAdapter(adapter);

        //Récupération du pseudo depuis la précédente application et l'afficher a la place de pseudo
        Intent itGameMode = getIntent();
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

        ImageView background = findViewById(R.id.background);
        background.setTranslationY(-130);

        buttonSolo.setOnClickListener(v -> {
            Intent itGame = new Intent(GameMode.this, AndroidLauncher.class);
            startActivity(itGame);
        });

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
}