package com.mygdx.client;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveUpdatePlayer implements Runnable {
    Player player;
    float cpt;

    public RetrieveUpdatePlayer(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();
        long runningTime = 100000000L;
        while (System.currentTimeMillis() < initialTime + runningTime) {

//            System.out.println("RetrieveUpdatePlayer : run " + Mates.getMates().size());

            for (int i = 0; i < Mates.getMates().size(); i++) {
                requestServer(Mates.getMate(i));
            }

            try {
                Thread.sleep(100); ///////////////////
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "RetrieveUpdatePlayer";
//        System.out.println("GET_URL " + GET_URL);
        String paramString = buildParam(player);

        GET_URL = GET_URL + paramString;
        String USER_AGENT = "Mozilla/5.0";
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
                String responseString = String.valueOf(response);
                String[] tempString = responseString.split(";");

//                System.out.println("requestServer " + player.getX() + " / " + player.getY());

                updatePlayer(player, tempString);
            } else {
                System.out.println("GET request did not work.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    private static void updatePlayer(Player player, String[] tempString) {
        if (tempString[0] != null && !tempString[0].isEmpty()) {
            float tempX = Float.parseFloat(tempString[0]);
            float tempY = Float.parseFloat(tempString[1]);

//            System.out.println("updatePlayer " + player.getX() + " / " + player.getY());

            player.setX(tempX);
            player.setY(tempY);
            player.setFindRegion(tempString[2]);
        }
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        param = param + "&numLobby=" + player.getNumLobby();
        return param;
    }
}
