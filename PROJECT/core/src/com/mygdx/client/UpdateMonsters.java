package com.mygdx.client;

import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Mob;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UpdateMonsters implements Runnable {
    private static Player player;

    private static Monsters simulationMobs;
    private final static String GET_URL = MainGame.URLServer + "UpdateMonsters";
    private final static String USER_AGENT = "Mozilla/5.0";
    private final static long RUNNING_TIME = 100000000L;

    public UpdateMonsters(Player p, Monsters mobs) {
        player = p;
        simulationMobs = mobs;
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < initialTime + RUNNING_TIME) {
            try {
                Thread.sleep(100); ///////////////////
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            requestServer();
            //System.out.println("UpdateMonsters:run +++++++++++++++++++++++++++++++++++++");
        }
    }

    public static void requestServer() {

        String paramString = buildParam();

        URL url = null;

        try {
            url = new URL(GET_URL + paramString);
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

                System.out.println("UpdateMonsters:requestServer " + response);

            } else {
                System.out.println("UpdateMonsters:requestServer did not work.");
            }
        } catch (MalformedURLException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }

    }

    private static String buildParam() {

        ///////////////////////////////////////////////////////////////////////////////// BUG SERVER
        StringBuilder sbMonsters = new StringBuilder();
        sbMonsters.append("?");
        sbMonsters.append("&serverUniqueID=" + player.getServerUniqueID());
        sbMonsters.append("&numLobby=" + player.getNumLobby());
        sbMonsters.append("&monsters=");

        int len0 = sbMonsters.length();

        sbMonsters.append(simulationMobs.buildMonstersHttpParam());

        // FLOOD !!!
        System.out.println((sbMonsters.length() - len0) + " ########## UpdateMonsters:buildParam " + sbMonsters);

        return sbMonsters.toString();
    }

}
