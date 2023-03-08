package com.mygdx.client;

import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewPlayer {

    public static void requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "NewPlayer";//
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
                player.setServerUniqueID(String.valueOf(response));

                System.out.println(" NewPlayer/GET setServerUniqueID ---_" + String.valueOf(response) + "_--- " + player.getServerUniqueID());

            } else {
                System.out.println(responseCode + " NewPlayer/GET request did not work.");
            }

        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    private static String buildParam(Player player) {

        String param = "?";
        param = param + "x=" + player.getX();
        param = param + "&y=" + player.getY();
        param = param + "&boxWidth=" + player.getBox().getWidth();
        param = param + "&boxHeight=" + player.getBox().getHeight();
        param = param + "&uniqueID=" + player.getUniqueID();
        param = param + "&spriteColorInt=" + player.getSpriteTint();
        param = param + "&findRegion=" + player.getFindRegion();
        param = param + "&textureAtlasPath=" + player.getTextureAtlasPath();
        param = param + "&scale=" + 1;//+player.getScale();

        System.out.println("NewPlayer ///// buildParam : " + param);

        return param;
    }

}
