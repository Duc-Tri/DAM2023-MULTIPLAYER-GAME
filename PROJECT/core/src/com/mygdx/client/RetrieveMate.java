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
        while (System.currentTimeMillis() < initialTime + runningTime) {
            String[] tempMates = requestServer(player);
            Mates.createNewMates(tempMates);
        }
    }

    public static String[] requestServer(Player player) {
        String GET_URL = MainGame.URLServer + "RetrieveMate";//
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
                if (!String.valueOf(response).isEmpty()) {
                    String[] mates = String.valueOf(response).split(";");
                    return mates;
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
        return param;
    }

}
