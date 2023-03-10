package game.entity;

public class Player {

	private String x;
	private String y;
	private String boxWidth;
	private String boxHeight;
	private String uniqueID;
	private String serverUniqueID;
	private String spriteColorInt;
	private String findRegion;
	private String textureAtlasPath;
	private String scale;

	public Player(String uniqueID, String serverUniqueID) {
		super();
		this.uniqueID = uniqueID;

		this.serverUniqueID = serverUniqueID;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getBoxWidth() {
		return boxWidth;
	}

	public void setBoxWidth(String boxWidth) {
		this.boxWidth = boxWidth;
	}

	public String getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(String boxHeight) {
		this.boxHeight = boxHeight;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getServerUniqueID() {
		return serverUniqueID;
	}

	public void setServerUniqueID(String serverUniqueID) {
		this.serverUniqueID = serverUniqueID;
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

	public String getTextureAtlasPath() {
		return textureAtlasPath;
	}

	public void setTextureAtlasPath(String textureAtlasPath) {
		this.textureAtlasPath = textureAtlasPath;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

}
