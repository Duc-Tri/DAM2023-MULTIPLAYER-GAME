package com.mygdx.client;

import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdatePlayer {
    public static void requestServer(Player player) {
        String GET_URL = player.getGame().getURLServer()+"UpdatePlayer";
        String paramString  = buildParam(player);
        GET_URL = GET_URL+paramString;
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
        try {
            url = new URL(GET_URL);
            HttpURLConnection con =  (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent",USER_AGENT);
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request did not work.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildParam(Player player) {
        float realX = player.getRealX();
        float realY = player.getRealY();
        String param = "?";
        param = param + "x="+realX;
        param = param + "&y="+realY;
        param = param + "&serverUniqueID="+player.getServerUniqueID();
        param = param + "&findRegion="+player.getFindRegion();

        return param;
    }

}