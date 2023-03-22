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

                String[] resp = String.valueOf(response).split(";");
                player.setServerUniqueID(resp[0]);
                player.setNumLobby(resp[1]);

                ///////////////////////////////////////////////////////////////////////////////////
                //player.setMaster(true);
                ///////////////////////////////////////////////////////////////////////////////////
                player.setMaster(resp[2].trim().equalsIgnoreCase("true"));

                System.out.println("NewPlayer ###### " +  resp[0]+"/"+resp[1]+"/"+resp[2]);
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    private static String buildParam(Player player) {

        String param = "?";
        param = param + "x=" + player.getX();
        param = param + "&y=" + player.getY();
        param = param + "&boxWidth=" + player.getHitbox().getWidth();
        param = param + "&boxHeight=" + player.getHitbox().getHeight();
        param = param + "&uniqueID=" + player.getUniqueID();
        param = param + "&spriteColorInt=" + player.getSpriteTint();
        param = param + "&findRegion=" + player.getFindRegion();
        param = param + "&scale=" + 1;//+player.getScale();
        param = param + "&numLobby=" + player.getNumLobby();

//        System.out.println("NewPlayer _____________________ buildParam : " + param);

        return param;
    }

}
