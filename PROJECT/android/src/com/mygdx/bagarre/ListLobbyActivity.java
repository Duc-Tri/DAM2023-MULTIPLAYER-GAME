package com.mygdx.bagarre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;

public class ListLobbyActivity extends AppCompatActivity {

    String[] retrieveLobbyList;

    String[] numLobby;
    int[] numLobbyTemp;

    String[] listLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby_view);

        numLobbyTemp = getResources().getIntArray(R.array.numLobby);
        numLobby = new String[numLobbyTemp.length];
        for(int i = 0; i < numLobbyTemp.length; i++) {
            numLobby[i] = Integer.toString(numLobbyTemp[i]);
            System.out.println("lobby libre : " + numLobby[i]);
        }



        new HttpTask(new HttpTask.OnHttpResultListener() {
            @Override
            public void onHttpResult(String[] result) {

                listLobby = result;

//                System.out.println("Taille du tableau : " + listLobby.length);
//
//                for(int i = 0; i < listLobby.length; i++) {
//                    System.out.println("lobby libre : " + listLobby[i]);
//                }

                initRecyclerView(listLobby);
            }
        }).execute();

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
	
    private void initRecyclerView(String[] ListLobby){

        if (ListLobby == null) {
            ListLobby = numLobby;
        }

        retrieveLobbyList = ListLobby;

        RecyclerView rvListLobby = findViewById(R.id.rvListLobby);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvListLobby.setLayoutManager(layoutManager);

        ListLobbyAdapter adapter = new ListLobbyAdapter(this, retrieveLobbyList);

        rvListLobby.setAdapter(adapter);
    }


}