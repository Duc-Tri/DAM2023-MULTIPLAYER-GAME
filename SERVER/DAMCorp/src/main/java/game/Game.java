package game;

import game.entity.Player;

public class Game {

	private final static int POOLSIZE = 10;
	static int staticIdCpt;
	private final static int LOBBYSIZE = 20;
	static int numLobby = 0;

	static Player[][] players = new Player[POOLSIZE][LOBBYSIZE];

	static String[] monsters = new String[POOLSIZE]; // les monstres sont agglomérés en string

	static int cptLobby = 0;
	private final static long TIMEOUT = 60000L; // 60000L = une minute

	public boolean possibleToScribe() {
		if (staticIdCpt * cptLobby < POOLSIZE * LOBBYSIZE) {
			return true;
		}
		return false;
	}

	public String getNextId() {
		if (staticIdCpt < LOBBYSIZE) {
			return "" + staticIdCpt++;
		}
		staticIdCpt = 0;
		return "" + staticIdCpt++;
	}

	public void addPlayer(Player player) {
		int serverId = Integer.parseInt(player.getServerUniqueID());

		if (cptLobby >= LOBBYSIZE) {
			cptLobby = 0;
			numLobby++;
		}

		player.setNumLobby("" + numLobby);
		player.setMaster(cptLobby == 0); // le premier player = master
		players[numLobby][cptLobby] = player;

		cptLobby++;
	}

	public void addPlayers(Player[] players) {
		numLobby++;
		addPlayers(players, numLobby);
		cptLobby = 0;
		numLobby++;
	}

	public void addPlayers(Player[] players, int numLobby) {
		for (int i = 0; i < players.length; i++) {
			addPlayer(players[i], numLobby);
		}
	}

	public void addPlayer(Player player, int numLobby) {
		if (numLobby < POOLSIZE) {
			for (int i = 0; i < players[numLobby].length; i++) {
				if (players[numLobby] == null) {
					players[numLobby][i] = player;
				}
			}
		}
	}

	public Player retrievePlayer(String parameterIDserv, String paramLobby) {
		if (parameterIDserv != null && !parameterIDserv.isEmpty() && paramLobby != null && !paramLobby.isEmpty()
				&& !paramLobby.equalsIgnoreCase("null")) {
			int serverId = Integer.parseInt(parameterIDserv);
			int nLobby = Integer.parseInt(paramLobby);
			return players[nLobby][serverId];
		}
		return null;
	}

	public String retrieveMate(Player player) {
		String tempString = "";
		int cpt = 0;

		if (player != null) {
			int nLobby = Integer.parseInt(player.getNumLobby());
			for (int i = 0; i < players[nLobby].length; i++) {
				if (player != null && (players[nLobby][i] != null)
						&& !player.getServerUniqueID().equalsIgnoreCase(players[nLobby][i].getServerUniqueID() + "")
						&& players[nLobby][i].getLastUpdate() > System.currentTimeMillis() - TIMEOUT) {
					if (cpt == 0) {
						tempString = "" + players[nLobby][i].getServerUniqueID();
					} else {
						tempString = tempString + ";" + players[nLobby][i].getServerUniqueID();
					}
					cpt++;
				} else {
					if (player != null && (players[nLobby][i] != null)
							&& !player.getServerUniqueID().equalsIgnoreCase(players[nLobby][i].getServerUniqueID() + "")
							&& !(players[nLobby][i].getLastUpdate() > System.currentTimeMillis() - TIMEOUT)) {
//						System.out.println("Temps dépassé, on ne retourne pas " + players[nLobby][i].getServerUniqueID());
					}
				}
			}
		}
		return tempString;
	}

	public String retrieveMonsters(Player player) {
		String tempString = "";

		if (player != null) {
			int nLobby = Integer.parseInt(player.getNumLobby());
			tempString = monsters[nLobby];

			// System.out.println(nLobby + " / " + tempString.length() + "
			// Game::retrieveMonsters === " + tempString);
		}

		return tempString;
	}

	public void setMonsters(int nLobby, String mobs) {
		monsters[nLobby] = mobs;

		// System.out.println(nLobby + " / " + mobs.length() + " Game::setMonsters === "
		// + mobs);
	}

	public int getId() {
		return staticIdCpt;
	}

	public void cancelGame(String serverUniqueID, String numLobby) {
		// TODO Auto-generated method stub
		int parseInt = -1;
		try {
			Integer.parseInt(numLobby);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(parseInt!=-1 && parseInt<LOBBYSIZE ) {
			for(Player p : players[parseInt]) {
				p = null;
			}
		}
		
	}
}
