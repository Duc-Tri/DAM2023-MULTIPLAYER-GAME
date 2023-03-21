package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListPlayerActivity extends AppCompatActivity {


    String[] playerID;
    int[] illustrations = {
            R.drawable.illu1,
            R.drawable.illu2,
            R.drawable.illu3,
            R.drawable.illu4,
            R.drawable.illu5};



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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(ListPlayerActivity.this,MultiJoueurActivity.class));
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