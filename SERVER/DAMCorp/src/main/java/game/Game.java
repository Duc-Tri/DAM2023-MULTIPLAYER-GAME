package game;

import game.entity.Player;

public class Game {

//	PLayer[][] PoolList = new PLayer[100][10];
	final static int poolSize = 100; // juste pour test ...
	static int staticIdCpt;
	final static int lobbySize = 8;
	static int numLobby = 0;
	static Player[][] players = new Player[poolSize][lobbySize];
	int cptLobby =0;
	
	
	public boolean possibleToScribe() {
		// TODO Auto-generated method stub
		if (staticIdCpt < poolSize*lobbySize) {
			return true;
		}
		//
		return false;
		
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return "" + staticIdCpt++;
	}

	public void addPlayer(Player player) {
		// TODO Auto-generated method stub

		System.out.println("Game ========== addPlayer ■ " + player.getServerUniqueID());

		int serverId = Integer.parseInt(player.getServerUniqueID());
		
		
		if( cptLobby == lobbySize-1 ) {
			
			cptLobby =0;
			numLobby ++;
			player.setNumLobby(""+numLobby);
			players[numLobby][cptLobby] = player;
			
		}else {

			player.setNumLobby(""+numLobby);
			cptLobby ++;
			players[numLobby][cptLobby] = player;
			
		}
		System.out.println("player.numLobby : "+ player.getNumLobby());
	}
	
/*** A mettre en commentaire apres ***/
//	public Player retrievePlayer(String parameterIDserv) {
//		// TODO Auto-generated method stub
////		System.out.println("parameter " + parameter);
////		System.out.println("parameter " + parameter);
////		System.out.println("parameter " + parameter);
////		System.out.println("parameter " + parameter);
//
//		if (parameterIDserv != null && !parameterIDserv.isEmpty() && !parameterIDserv.equalsIgnoreCase("null")) {
//			int serverId = Integer.parseInt(parameterIDserv);
//
//			return players[serverId];
//		}
//		return null;
//	}
	
	public Player retrievePlayer(String parameterIDserv, String paramLobby) {
		// TODO Auto-generated method stub
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);

		if (parameterIDserv != null && !parameterIDserv.isEmpty() && !paramLobby.equalsIgnoreCase("null") && paramLobby != null && !paramLobby.isEmpty() && !paramLobby.equalsIgnoreCase("null")) {
			int serverId = Integer.parseInt(parameterIDserv);
			int nLobby = Integer.parseInt(paramLobby);
			
			return players[nLobby][serverId];
		}
		return null;
	}

	public String retrieveMate(Player player) {
		// TODO Auto-generated method stub
		String tempString = "";
		int cpt = 0;
		if (player != null ) {
			int nLobby = Integer.parseInt(player.getNumLobby());
			System.out.println("Je suis dans RetrieveMate dans le Lobby n°"+nLobby);
			
			
	//		System.out.println("players.length " + players.length);
			for (int i = 0; i < players[nLobby].length; i++) {
	//			System.out.println("i " + i);
				if (player != null 
						//&& !player.getServerUniqueID().equalsIgnoreCase(i + "") 
						&& (players[nLobby][i] != null) 
						&& players[nLobby][i].getLastUpdate()>System.currentTimeMillis()-100000L ) {
	//				System.out.println("Fine i " + i);
					if (cpt == 0) {
						tempString = "" + i;
					} else {
						tempString = tempString + ";" + i;
					}
					cpt++;
	//				System.out.println("tempString  " + tempString);
				}
	
			}
		}
		// System.out.println("tempString " + tempString);
		return tempString;

	}

}
