package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class ListLobbyActivity extends AppCompatActivity {

    int[] numLobby;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby_view);

        numLobby = getResources().getIntArray(R.array.numLobby);

        initRecyclerView();

        setListenerForRetour();
        setListenerForJoinLobby();
    }

    private void setListenerForJoinLobby() {
        Button btnJoinLobby = findViewById(R.id.btnJoinLobbyGame);
        btnJoinLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ListLobbyActivity.this,AndroidLauncher.class);
                startActivity(intent);
            }
        });
    }


    private void setListenerForRetour() {
        Button btnRetour = findViewById(R.id.btnRetourLobby);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListLobbyActivity.this,MultiJoueurActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initRecyclerView(){

        RecyclerView rvListLobby = findViewById(R.id.rvListLobby);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvListLobby.setLayoutManager(layoutManager);

        ListLobbyAdapter adapter = new ListLobbyAdapter(this,numLobby);

        rvListLobby.setAdapter(adapter);
    }


}