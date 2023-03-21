package com.mygdx.client;

import com.mygdx.bagarre.MainGame;
import com.mygdx.entity.Mates;
import com.mygdx.entity.Mob;
import com.mygdx.entity.Monsters;
import com.mygdx.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RetrieveMonsters implements Runnable {
    private static final String GET_URL = MainGame.URLServer + "RetrieveMonsters";
    private static final String USER_AGENT = "Mozilla/5.0";
    private final static long RUNNING_TIME = 100000000L;
    private static Player player;

    private Monsters monsters;

    public RetrieveMonsters(Player p, Monsters mobs) {
        player = p;
        monsters = mobs;
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();
        int i = 0;
        while (System.currentTimeMillis() < initialTime + RUNNING_TIME) {

            String[] tempMobs = requestServer();

//            System.out.println("RetrieveMonsters:run ++++++++++++++++++++++ ");

            if (tempMobs != null && tempMobs.length > 0) {
                monsters.createNewMobs(tempMobs);
                monsters.removeOldMobs(tempMobs);
            } else {
                monsters.removeAllMobs(tempMobs);
            }

            try {
                Thread.sleep(100); ///////////////////
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static String[] requestServer() {
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

                String res = String.valueOf(response);
                if (res != null && !res.isEmpty()) {

//                    System.out.println("RetrieveMonsters:requestServer " + res);

                    String[] monsters = res.split("!");
                    return monsters;
                }

            } else {
                System.out.println("RetrieveMonsters:requestServer did not work.");
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }

    private static String buildParam() {
        String param = "?";
        param = param + "&serverUniqueID=" + player.getServerUniqueID();
        param = param + "&numLobby=" + player.getNumLobby();

        return param;
    }

}
