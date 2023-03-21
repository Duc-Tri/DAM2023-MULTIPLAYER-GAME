package game.entity;

public class Monsters {

	private final static int MAX_MONSTERS = 100;
	private String numLobby = "";
	private Mob[] mobs = new Mob[MAX_MONSTERS];

	private long lastUpdate;

	public Monsters(String numLobby) {
		super();
	}


	public String getNumLobby() {
		return numLobby;
	}

	public void setNumLobby(String numLobby) {
		this.numLobby = numLobby;
	}

}
