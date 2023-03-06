package com.mygdx.client;



import com.mygdx.bagarre.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import entitygestion.Mates;
import entitygestion.Player;

public class ClientRetrieveMates {

    public static void retrieveMate(Player player) {
        System.out.println("retrieveMate  ");
        String POST_URL = "http://192.168.0.33:10020/DAMCorp/RetrieveMate?";
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
        try {
//            POST_URL = addParamTo(POST_URL,"x",""+player.getX());
//            POST_URL = addParamTo(POST_URL,"y",""+player.getY());
//            POST_URL = addParamTo(POST_URL,"boxWidth",""+player.getBox().getWidth());
//            POST_URL = addParamTo(POST_URL,"boxHeight",""+player.getBox().getHeight());
//            POST_URL = addParamTo(POST_URL,"uniqueID",""+player.getUniqueID());
            POST_URL = addParamTo(POST_URL,"serverUniqueID",""+player.getServerUniqueID());
//            POST_URL = addParamTo(POST_URL,"spriteColorInt",""+player.getSpriteColorInt());
//            POST_URL = addParamTo(POST_URL,"findRegion",""+player.getFindRegion());
//            POST_URL = addParamTo(POST_URL,"textureAtlasPath",""+player.getTextureAtlasPath());
//            POST_URL = addParamTo(POST_URL,"scale",""+player.getScale());
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

            String[] responseArray;//= new String[0];
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                int cpt=0;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

               //     System.out.println("inputLine " + inputLine);
                }


                in.close();

                // print result
                responseArray = response.toString().split(";");
                createMates(responseArray, player);
//               return
//                System.out.println();
            } else {
                System.out.println("GET request did not work.");
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        return null;

    }

    private static void createMates(String[] sArr,Player player) {
//        for(int i =0 ; i < sArr.length; i++){
//            System.out.println("Mates sArr["+i+"] = "  + sArr[i]);
//        }

        System.out.println("createMates   ");
        player.createMates(sArr,player);
        player.createMissingMate(sArr,player );
//        return new Mates[0];
    }

    private static String addParamTo(String post_url, String var, String value) {
        post_url = post_url+"&"+var+"="+value;
        return post_url;

    }

}
