package game.entity;

public class Monster {

	private String x;
	private String y;
	private String uniqueID;
	private String lobbyPlayerId;
	private String spriteColorInt;
	private String findRegion;
	private String numLobby = "";
	private int life=999999; // bidouille magique ....

	private long lastUpdate;

	public Monster(String uniqueID) {
		super();
		this.uniqueID = uniqueID;
	}

	public String getFootX() {
		return x;
	}

	public void setFootX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getSpriteColorInt() {
		return spriteColorInt;
	}

	public void setSpriteColorInt(String spriteColorInt) {
		this.spriteColorInt = spriteColorInt;
	}

	public String getFindRegion() {
		return findRegion;
	}

	public void setFindRegion(String findRegion) {
		this.findRegion = findRegion;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getNumLobby() {
		return numLobby;
	}

	public void setNumLobby(String numLobby) {
		this.numLobby = numLobby;
	}

	public String getLobbyPlayerId() {
		return lobbyPlayerId;
	}

	public void setLobbyPlayerId(String lobbyPlayerId) {
		this.lobbyPlayerId = lobbyPlayerId;
	}

	public int getCurrentLife() {
		return life;
	}

	public void setCurrentLife(int newLife) {
		// au cas où des dommages ont été appliqués .................................
		if (newLife < life)
			life = newLife;
	}

	public String buildHttpResponse() {
		return uniqueID + ";" + getFootX() + ";" + getY() + ";" + findRegion + ";" + life + "!";
		// pas * pas ~ pas # !!!
	}

	public void applyDamage(int damage) {
		life -= damage;
	}

}