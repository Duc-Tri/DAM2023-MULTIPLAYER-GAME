package com.mygdx.client;



import com.mygdx.bagarre.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import entitygestion.Player;

public class ClientRetrievePlayer {

    public static Player retrievePlayer(Player player) {

        String POST_URL = "http://192.168.0.33:10020/DAMCorp/RetrievePlayer?";
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
        try {
            System.out.println("player.getServerUniqueID()    " + player.getServerUniqueID());
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
               return  createPlayer(responseArray,player.getGame());
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


        return null;

    }

    private static Player createPlayer(String[] sArr, Game game) {
//        for(int i =0 ; i < sArr.length; i++){
//            System.out.println("sArr["+i+"] = "  + sArr[i]);
//        }

        Player player = new Player(game);
        player.createBox();
        player.createSprite();

        player.setX(Float.parseFloat(sArr[0]));
        player.setY(Float.parseFloat(sArr[1]));

        player.getBox().setWidth(Float.parseFloat(sArr[2]));
        player.getBox().setHeight(Float.parseFloat(sArr[3]));
        player.setUniqueID(sArr[4]);
        player.setServerUniqueID(sArr[5]);
        System.out.println("player.setServerUniqueID(sArr[5]); " + sArr[5]);
        player.setSpriteColorInt(Integer.parseInt(sArr[6]));
        player.setSpriteColorInt(player.getSpriteColorInt());
        player.setFindRegion(sArr[7]);
        player.setTextureAtlasPath(sArr[8]);
        player.setScale(Float.parseFloat(sArr[9]));




//     Player player = new Player();
//        System.out.println();
//        System.out.println(player.getServerUniqueID());
//        System.out.println(player.getX());
//        System.out.println(player.getY());
//        System.out.println(player.getBox().getWidth());
//        System.out.println(player.getBox().getHeight());
//        System.out.println(player.getUniqueID());
//        System.out.println(player.getSpriteColorInt());
//        System.out.println(player.getFindRegion());
//        System.out.println(player.getTextureAtlasPath());
//        System.out.println(player.getScale());
        return player;
    }

    private static String addParamTo(String post_url, String var, String value) {
        post_url = post_url+"&"+var+"="+value;
        return post_url;

    }

}
