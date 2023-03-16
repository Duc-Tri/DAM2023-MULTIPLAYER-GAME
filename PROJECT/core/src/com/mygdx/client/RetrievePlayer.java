package com.mygdx.client;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrievePlayer {
    public static void requestServer(Player player) {
        String GET_URL = MainGame.URLServer+"RetrievePlayer";
        String paramString = buildParam(player);
        GET_URL = GET_URL + paramString;
        String USER_AGENT = "Mozilla/5.0";
        //System.out.println("URL RetrievePlayer : "+GET_URL);
        URL url = null;
        try {
            url = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String [] tempString = String.valueOf(response).split(";");
                //System.out.println(String.valueOf(response));
                updatePlayer(player, tempString);

            } else {
                System.out.println("GET request did not work.");
            }
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//            e.printStackTrace();
        } catch (IOException e) {
//            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    private  static void updatePlayer(Player player, String[] tempString) {
        if(tempString[0]!=null  && !tempString[0].isEmpty()){
            player.setHitbox(new Rectangle());
            player.setSprite(new Sprite());
            player.setX(Float.parseFloat(tempString[0]));
            player.setY(Float.parseFloat(tempString[1]));
            player.initializeSprite();
            player.getHitbox().setWidth(Float.parseFloat(tempString[2]));
            player.getHitbox().setHeight(Float.parseFloat(tempString[3]));
            player.setUniqueID(tempString[4]);
            player.setServerUniqueID(tempString[5]);
            player.setFindRegion(tempString[6]);
            //player.setTextureAtlasPath(tempString[7]);
            //player.setScale(Float.parseFloat(tempString[8]));

//            System.out.println("Num Lobby dans update player : " + player.getNumLobby());
//            System.out.println("Server Unique ID : "+player.getServerUniqueID());
//            System.out.println("player X : "+player.getX());
//            System.out.println("player Y : "+player.getY());
//            System.out.println("player Unique ID : "+player.getUniqueID());
        }
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        param = param + "&numLobby=" + player.getNumLobby();
      //  System.out.println("numLobby = "+ player.getNumLobby());
        return param;
    }
}
