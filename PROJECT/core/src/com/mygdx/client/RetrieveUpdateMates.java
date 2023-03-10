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

public class RetrieveUpdateMates implements Runnable {
    Player player;
    float cpt;

    public RetrieveUpdateMates(Player player) {
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
        }
    }

    public static void requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "RetrieveUpdateMates";
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
                String[] tempStringArray = responseString.split("/");

//               System.out.println("requestServer " + player.getX() + " / " + player.getY());

                for(int i = 0 ; i < tempStringArray.length; i++ ){
                    String[] tempString = tempStringArray[i].split(";");
                    updatePlayer(player, tempString);
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
    }

    private static void updatePlayer(Player player, String[] tempString) {
        if (tempString[0] != null && !tempString[0].isEmpty()) {
//            System.out.println("tempString[0]  " + tempString[0]);
//            System.out.println("tempString[1]  " + tempString[1]);
//            System.out.println("tempString[2]  " + tempString[2]);
            float tempX = Float.parseFloat(tempString[0]);
            float tempY = Float.parseFloat(tempString[1]);
            player.setRealX(tempX);
            player.setRealY(tempY);

//            System.out.println(player.getServerUniqueID() +"   updatePlayers " + player.getX() + " / " + player.getY());
//
//            player.setX(tempX);
//            player.setY(tempY);
//            player.setFindRegion(tempString[2]);
        }
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        return param;
    }
}
