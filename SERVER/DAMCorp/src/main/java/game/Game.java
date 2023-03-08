package game;

import game.entity.Player;

public class Game {

//	PLayer[][] PoolList = new PLayer[100][10];
	final static int poolSize = 100; // juste pour test ...
	static int staticIdCpt;
	static Player[] players = new Player[poolSize];

	public boolean possibleToScribe() {
		// TODO Auto-generated method stub
		if (staticIdCpt < poolSize - 1) {
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
		players[serverId] = player;

	}

	public Player retrievePlayer(String parameter) {
		// TODO Auto-generated method stub
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);
//		System.out.println("parameter " + parameter);

		if (parameter != null && !parameter.isEmpty()) {
			int serverId = Integer.parseInt(parameter);

			return players[serverId];
		}
		return null;
	}

	public String retrieveMate(Player player) {
		// TODO Auto-generated method stub
		String tempString = "";
		int cpt = 0;
//		System.out.println("players.length " + players.length);
		for (int i = 0; i < players.length; i++) {
//			System.out.println("i " + i);
			if (player != null && !player.getServerUniqueID().equalsIgnoreCase(i + "") && (players[i] != null)) {
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
		// System.out.println("tempString " + tempString);
		return tempString;

	}

}
