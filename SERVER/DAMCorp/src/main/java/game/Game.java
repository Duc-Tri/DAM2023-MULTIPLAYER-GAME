package game;

import game.entity.Player;

public class Game {

	final static int poolSize = 100;
	static int staticIdCpt;
	final static int lobbySize = 3;
	static int numLobby = 0;
	static Player[][] players = new Player[poolSize][lobbySize];
	int cptLobby =0;
	private long timeout = 10000L;
	
	public boolean possibleToScribe() {
		if (staticIdCpt*cptLobby < poolSize*lobbySize) {
			return true;
		}
		return false;
	}

	public String getNextId() {
		if(staticIdCpt<lobbySize) {
			return "" + staticIdCpt++;	
		}
		staticIdCpt = 0;
		return "" +staticIdCpt++;
	}

	public void addPlayer(Player player) {
		int serverId = Integer.parseInt(player.getServerUniqueID());
		if( cptLobby < lobbySize ) {
			players[numLobby][cptLobby] = player;
			player.setNumLobby(""+numLobby);
			cptLobby ++;
		}else {
			cptLobby=0;
			numLobby ++;
			players[numLobby][cptLobby] = player;
			player.setNumLobby(""+numLobby);
			cptLobby++;
		}
	}
	
	public Player retrievePlayer(String parameterIDserv, String paramLobby) {
		if (parameterIDserv != null && !parameterIDserv.isEmpty() && !paramLobby.equalsIgnoreCase("null") && paramLobby != null && !paramLobby.isEmpty() && !paramLobby.equalsIgnoreCase("null")) {
			int serverId = Integer.parseInt(parameterIDserv);
			int nLobby = Integer.parseInt(paramLobby);
			return players[nLobby][serverId];
		}
		return null;
	}

	public String retrieveMate(Player player) {
		String tempString = "";
		int cpt = 0;
		if (player != null ) {
			int nLobby = Integer.parseInt(player.getNumLobby());
			for (int i = 0; i < players[nLobby].length; i++) {
				if (player != null 
						&& (players[nLobby][i] != null)
						&& !player.getServerUniqueID().equalsIgnoreCase(players[nLobby][i].getServerUniqueID() + "") 
						&& players[nLobby][i].getLastUpdate()>System.currentTimeMillis()-this.timeout  
						){
					if (cpt == 0) {
						tempString = "" + players[nLobby][i].getServerUniqueID();
					} else {
						tempString = tempString + ";" + players[nLobby][i].getServerUniqueID();
					}
					cpt++;
				}else {
					if (player != null 
							&& (players[nLobby][i] != null)
							&& !player.getServerUniqueID().equalsIgnoreCase(players[nLobby][i].getServerUniqueID() + "") 
							&& !(players[nLobby][i].getLastUpdate()>System.currentTimeMillis()-this.timeout)  
							){
						System.out.println("Temps dépassé, on ne retourne pas " + players[nLobby][i].getServerUniqueID());
					}
				}
			}
		}
		return tempString;
	}

	public int getId() {
		return staticIdCpt;
	}
}
