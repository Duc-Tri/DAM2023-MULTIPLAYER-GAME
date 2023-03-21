package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class ListPlayerActivity extends AppCompatActivity {


    String[] playerID;
    int[] illustrations = {
            R.drawable.illu1_head,
            R.drawable.illu2_head,
            R.drawable.illu3_head,
            R.drawable.illu4_head,
            R.drawable.illu5_head};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_player);



        playerID = getResources().getStringArray((R.array.playerID));

        initRecyclerView();

        setListenerForReturn();
        setListenerForStartGame();
    }

    private void setListenerForReturn() {
        Button btnRetour = findViewById(R.id.btnRetourLobby);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPlayerActivity.this,MultiJoueurActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setListenerForStartGame() {
        Button btnStart = findViewById(R.id.btnLancezGame);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPlayerActivity.this,AndroidLauncher.class);
                startActivity(intent);
            }
        });
    }


    private void initRecyclerView(){
        //On relie avec le design et on initialise le layout manager du RecyclerView

        RecyclerView rvListPlayer = findViewById(R.id.rvListPlayer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvListPlayer.setLayoutManager(layoutManager);

        //Création de l'adapter
        ListPlayerAdapter adapter = new ListPlayerAdapter(this,illustrations,playerID);

        //Association avec le RecyclerView
        rvListPlayer.setAdapter(adapter);



    }
}