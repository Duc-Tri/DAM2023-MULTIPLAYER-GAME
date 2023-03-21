package com.mygdx.client;

import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RemovePlayers {

    public static void requestServer(Player[] players) {
        String GET_URL = MainGame.URLServer + "RemovePlayers";//
        String paramString = buildParam(players);
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
//                String[] resp = String.valueOf(response).split(";");
//                player.setServerUniqueID(resp[0]);
//                player.setNumLobby(resp[1]);
                String[] resp = String.valueOf(response).split(";");
                if (resp.length > 1) {
                    String[] listId = resp[0].split(",");
                    for (int i = 0; i < listId.length; i++) {
                        players[i].setLobbyPlayerId(listId[i]);
                        players[i].setNumLobby(resp[100]);
                    }
                }

            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    private static String buildParam(Player[] players) {

        String param = "?";
        param = param + "&listPlayers=";
        for (int i = 0; i < players.length; i++) {
            if (i == 0) {
                param = param + players[i].getUniqueID();
            } else {
                param = param + ";" + players[i].getUniqueID();
            }
        }

//        param = param + "&y=" + player.getY();
//        param = param + "&boxWidth=" + player.getHitbox().getWidth();
//        param = param + "&boxHeight=" + player.getHitbox().getHeight();
//        param = param + "&uniqueID=" + player.getUniqueID();
//        param = param + "&spriteColorInt=" + player.getSpriteTint();
//        param = param + "&findRegion=" + player.getFindRegion();
//        param = param + "&textureAtlasPath=" + player.getTextureAtlasPath();
//        param = param + "&scale=" + 1;//+player.getScale();
//        param = param + "&numLobby=" + player.getNumLobby();

//        System.out.println("NewPlayer _____________________ buildParam : " + param);

        return param;
    }

}
