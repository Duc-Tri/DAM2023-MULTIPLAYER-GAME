package com.mygdx.client;

import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveMate implements Runnable {
    Player player;

    public RetrieveMate(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();
        long runningTime = 100000000L;
        int i=0;
        while (System.currentTimeMillis() < initialTime + runningTime) {
            String[] tempMates = requestServer(player);
            //System.out.println("Avant le if des threads : tempsMates legnth : "+tempMates.length);
            if(tempMates != null && tempMates.length > 0) {
                //System.out.println("Saluuut Je suis dans le run de retrieveMates et voici les joueur " + tempMates[i]);
                Mates.createNewMates(tempMates);
               // Mates.removeOldMates(tempMates);

            }
        }
    }

    public static String[] requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "RetrieveMate";//
        String paramString = buildParam(player);
        //System.out.println("Je commence une request Server =========================================== ");
        GET_URL = GET_URL + paramString;
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
       // System.out.println("Voici la Requete : "+ GET_URL);
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
                System.out.println("Je suis apres la while : " + response);
                in.close();
                if (!String.valueOf(response).isEmpty()) {
                    System.out.println("Je suis avant le parse : " + response);
                    String[] mates = String.valueOf(response).split(";");
                    System.out.println("Je suis apres le parse : " + mates[0]);
                    return mates;
//                    return null;
                }

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
        return null;
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        param = param + "&numLobby=" + player.getNumLobby();
        return param;
    }

}
