package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MultiJoueurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_joueur);

        setListenerForCreateGame();
        setListenerForJoinGame();
    }


    private void setListenerForCreateGame() {
        Button btnCreerPartie = findViewById(R.id.creerPartie);

        btnCreerPartie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiJoueurActivity.this,ListPlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListenerForJoinGame(){
        Button btnRejoindrePartie = findViewById(R.id.rejoindrePartie);

        btnRejoindrePartie.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultiJoueurActivity.this, ListLobbyActivity.class);
                startActivity(intent);
            }
        }));
    }


}