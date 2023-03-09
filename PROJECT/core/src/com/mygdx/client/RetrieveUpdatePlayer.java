package com.mygdx.client;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveUpdatePlayer implements Runnable {
    Player player;
    float cpt;
    public RetrieveUpdatePlayer(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        long initialTime  = System.currentTimeMillis();
        long runningTime  = 100000000L;
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer  "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer  "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer  "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer  "   );
        while(System.currentTimeMillis() < initialTime+runningTime){
            System.out.println("while  RetrieveUpdatePlayer  "   );


            for(int i = 0 ; i < player.getGame().getMates().size(); i++){
                System.out.println("player.getGame().getMates().size()   " + player.getGame().getMates().size());
                System.out.println(" cpt   " +  cpt);
                requestServer( player.getGame().getMates().get(i));
//                System.out.println("mates.get(i).getSprite().getX()    "  + player.getGame().getMates().get(i).getSprite().getX());
//                System.out.println("mates.get(i).getX()    "  +player.getGame().getMates().get(i).getX() );
                if(player.getGame().getMates().get(i).getSprite().getX() != player.getGame().getMates().get(i).getX()){

                    System.out.println("mates.get(i).getSprite().getX()    "  + player.getGame().getMates().get(i).getSprite().getX());
                    System.out.println("mates.get(i).getX()    "  +player.getGame().getMates().get(i).getX() );
                }
                if(player.getGame().getMates().get(i).getSprite().getY() != player.getGame().getMates().get(i).getY()){

                    System.out.println("mates.get(i).getSprite().getY()    "  + player.getGame().getMates().get(i).getSprite().getY());
                    System.out.println("mates.get(i).getY()    "  + player.getGame().getMates().get(i).getY());
                }
            }
            System.out.println("while  RetrieveUpdatePlayer  END"   );
        }
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer END END "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer END "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer END "   );
        System.out.println("RUUUUUNNNNNNN  RetrieveUpdatePlayer  END"   );
    }
    public static void requestServer(Player player) {
        String GET_URL = player.getGame().getURLServer()+"RetrieveUpdatePlayer";
        String paramString = buildParam(player);

        GET_URL = GET_URL + paramString;
        System.out.println("GET_URL  " + GET_URL);
        String USER_AGENT = "Mozilla/5.0";
        URL url = null;
        try {
            url = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("responseCode  " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String responseString = String.valueOf(response);
                System.out.println("responseString  " + responseString);
                String [] tempString = responseString.split(";");
                updatePlayer(player, tempString);
            } else {
                System.out.println("GET request did not work.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
        }
    }

    private static void updatePlayer(Player player, String[] tempString) {
        System.out.println("updatePlayer ");
        if(tempString[0]!=null  && !tempString[0].isEmpty()){
            float tempX = Float.parseFloat(tempString[0]);
            float tempY = Float.parseFloat(tempString[1]);
            System.out.println("updatePlayer tempX " + tempX);
            System.out.println("updatePlayer tempY " + tempY);
//          player.setXFromRealX(tempX);
//          player.setYFromRealY(tempY);
            player.setRealX(tempX);
            player.setRealY(tempY);

            player.setFindRegion(tempString[2]);
        }
    }

    private static String buildParam(Player player) {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        return param;
    }
}
