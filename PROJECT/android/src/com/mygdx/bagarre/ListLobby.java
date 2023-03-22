package com.mygdx.bagarre;

import com.mygdx.client.RetrieveLobbies;

import java.net.MalformedURLException;

public class ListLobby {


    String[] listLobbies;


    public ListLobby(){
        try {
            listLobbies = RetrieveLobbies.requestServer();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }



    }


    public void show(){

        for(int i = 0 ;i< listLobbies.length;i++){
            System.out.println("Lobby libre : " + listLobbies[i]);
        }

    }

}
