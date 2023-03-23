package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

public class ListPlayerActivity extends AppCompatActivity implements FirebaseAndroid.OnListReadyListener {


    FirebaseAndroid db = new FirebaseAndroid();
    String[] playerID;
    int[] illustrations = {
            R.drawable.illu1,
            R.drawable.illu2,
            R.drawable.illu3,
            R.drawable.illu4,
            R.drawable.illu5};

    private List<String> listPseudoCompleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_liste_player);
        playerID = getResources().getStringArray((R.array.playerID));
        db.getListRecycler(this);
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

    private void initRecyclerView(List<String> listPseudoCompleted){
        /*private void initRecyclerView(){*/
        //On relie avec le design et on initialise le layout manager du RecyclerView

        RecyclerView rvListPlayer = findViewById(R.id.rvListPlayer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rvListPlayer.setLayoutManager(layoutManager);

        //Création de l'adapter
        ListPlayerAdapter adapter = new ListPlayerAdapter(this,illustrations,listPseudoCompleted);
        /*ListPlayerAdapter adapter = new ListPlayerAdapter(this,illustrations,playerID);*/

        //Association avec le RecyclerView
        rvListPlayer.setAdapter(adapter);



    }

    @Override
    public void onListReady(List<String> listPseudo) {
        listPseudoCompleted = listPseudo;
        if(listPseudo.size() > 0) {
            initRecyclerView(listPseudoCompleted);
        }
    }
}