package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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