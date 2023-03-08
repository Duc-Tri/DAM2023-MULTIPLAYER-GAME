package com.mygdx.client;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveUpdatePlayer {
    public static void requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "RetrieveUpdatePlayer";
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
                String[] tempString = String.valueOf(response).split(";");
                updatePlayer(player, tempString);
            } else {
                System.out.println(responseCode + " RetrieveUpdatePlayer/GET request did not work.");
            }
        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    private static void updatePlayer(Player player, String[] tempString) {
        if (tempString[0] != null && !tempString[0].isEmpty()) {
            float tempX = Float.parseFloat(tempString[0]);
            float tempY = Float.parseFloat(tempString[1]);
            player.setX(tempX);
            player.setY(tempY);
            player.setFindRegion(tempString[2]);
        }
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        return param;
    }
}
