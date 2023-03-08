package com.mygdx.client;

import com.mygdx.entity.Mate;
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
        long initialTime  = System.currentTimeMillis();
        long runningTime  = 100000000L;
        while(System.currentTimeMillis() < initialTime+runningTime){
            String[] tempMates = requestServer(player);
            createMates(tempMates);
//        String[] tempMates = RetrieveMate.requestServer(player);
//        createMates(tempMates);

        }
    }
    public static String[] requestServer(Player player) {
        String GET_URL = player.getGame().getURLServer()+"RetrieveMate";//
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
                if(!String.valueOf(response).isEmpty()){
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


    private void createMates(String[] tempMates) {
        if(tempMates !=  null){
            for (int i0 = 0; i0 < tempMates.length; i0++) {
                boolean finded = false;
                for(int i1 = 0; i1 < player.getGame().getMates().size(); i1++){
                    if(tempMates[i0] != null && tempMates[i0].equalsIgnoreCase(player.getGame().getMates().get(i1).getServerUniqueID())   ){
                        finded = true;
                    }
                }
                if(!finded){
                    player.getGame().getMates().add(new Mate(player.getGame()));
                    player.getGame().getMates().get(player.getGame().getMates().size()-1).setServerUniqueID(tempMates[i0]);
                    RetrievePlayer.requestServer(player.getGame().getMates().get(player.getGame().getMates().size()-1));
                    player.getGame().getMates().get(player.getGame().getMates().size()-1).initializeSprite();
                }
            }
        }

    }

}
