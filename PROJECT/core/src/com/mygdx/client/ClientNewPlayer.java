package com.mygdx.client;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import entitygestion.Player;

public class ClientNewPlayer {

    public static void newPlayer(Player player) {

        String POST_URL = "http://192.168.0.33:10020/DAMCorp/NewPlayer?";
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
        try {
            POST_URL = addParamTo(POST_URL,"x",""+player.getRealX());
            POST_URL = addParamTo(POST_URL,"y",""+player.getRealY());
            POST_URL = addParamTo(POST_URL,"boxWidth",""+player.getBox().getWidth());
            POST_URL = addParamTo(POST_URL,"boxHeight",""+player.getBox().getHeight());
            POST_URL = addParamTo(POST_URL,"uniqueID",""+player.getUniqueID());
            POST_URL = addParamTo(POST_URL,"serverUniqueID",""+player.getServerUniqueID());
            POST_URL = addParamTo(POST_URL,"spriteColorInt",""+player.getSpriteColorInt());
            POST_URL = addParamTo(POST_URL,"findRegion",""+player.getFindRegion());
            POST_URL = addParamTo(POST_URL,"textureAtlasPath",""+player.getTextureAtlasPath());
            POST_URL = addParamTo(POST_URL,"scale",""+player.getScale());
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);
//            System.out.println("POST_URL " + POST_URL);

            url = new URL(POST_URL);
            HttpURLConnection con =  (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent",USER_AGENT);


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    player.setServerUniqueID(inputLine);
                }
                in.close();

                // print result
//                System.out.println(response.toString());
            } else {
                System.out.println("GET request did not work.");
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

    private static String addParamTo(String post_url, String var, String value) {
        post_url = post_url+"&"+var+"="+value;
        return post_url;

    }

}
