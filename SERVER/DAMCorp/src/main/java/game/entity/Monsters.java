package game.entity;

import java.util.ArrayList;

//#################################################################################################
// Gestionnaire de monstres, utilise un tableau de Mob
// Un par lobby
//#################################################################################################
public class Monsters {

	private int numLobby;
	private final static int MAX_MONSTERS = 50;
	// private Mob[] lobbyMobs = new Mob[MAX_MONSTERS];
	private ArrayList<Mob> lobbyMobs = new ArrayList<Mob>();

	private long lastUpdate;
	private String lastMobsStr; // dernier String reçu du client MASTER

	public Monsters(int numLobby) {
		super();
		this.numLobby = numLobby;
	}

	public int getNumLobby() {
		return numLobby;
	}

	public void setNumLobby(int numLobby) {
		this.numLobby = numLobby;
	}

	// ============================================================================================
	// => les monstres du lobby, agglomérés en String, à reconstituer côté client
	// ============================================================================================
	public String toString() {
		StringBuilder lobbyMonstersStr = new StringBuilder();

		for (Mob m : lobbyMobs) {
			if (m.getCurrentLife() > 0) {
				lobbyMonstersStr.append(m.getUniqueID() + ";");
				lobbyMonstersStr.append(m.getFootX() + ";");
				lobbyMonstersStr.append(m.getY() + ";");
				lobbyMonstersStr.append(m.getFindRegion() + ";");
				lobbyMonstersStr.append(m.getCurrentLife() + "!"); // pas * pas ~ pas # !!!
			}
		}

		return lobbyMonstersStr.toString();
	}

	// ============================================================================================
	// TODO : RELIRE CET ALGO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// ============================================================================================
	public void setMonstersStr(String mobsStr) {
		lastMobsStr = mobsStr;

		String[] tempMobs = mobsStr.split("!");

		// FLOOD !!!
		// System.out.println("createNewMobs >>>>> tempMobs{" + tempMobs.length + "}=" +
		// String.join("_@_", tempMobs)

		if (tempMobs != null && tempMobs.length != 0) {

			for (String strMob : tempMobs) {

				boolean found = false;

				if (strMob != null && !strMob.isEmpty()) {
					String[] strMobSplit = strMob.split(";");
					String oneMobId = strMobSplit[0];
					int life = Integer.parseInt(strMobSplit[4]);

					for (int i = lobbyMobs.size() - 1; i >= 0; i--) {
						Mob updateMob = lobbyMobs.get(i);
						if (oneMobId.equalsIgnoreCase(updateMob.getUniqueID())) {
							found = true;

							if (life > 0) {
								updateMob.setFootX(strMobSplit[1]);
								updateMob.setY(strMobSplit[2]);
								updateMob.setFindRegion(strMobSplit[3]);
								updateMob.setCurrentLife(life);

								// FLOOD !
								// System.out.println("createNewMobs >>>>> UPDATE MOB >>>>> " +
								// drawMob.toString());
							}

							// System.out.println("createNewMobs >>>>> FOUND " + oneMobId);

							break;
						}
					}

					if (!found && life > 0) {

						String n = strMobSplit[3].split("_")[0]; // TODO : ????????????????

						// System.out.println("createNewMobs >>>>> NOT FOUND " + n + " / " + oneMobId);

						Mob newMob = new Mob("??????????????????????????????");
						newMob.setUniqueID(oneMobId);
						newMob.setFootX(strMobSplit[1]);
						newMob.setY(strMobSplit[2]);
						newMob.setFindRegion(strMobSplit[3]);
						newMob.setCurrentLife(Integer.parseInt(strMobSplit[4]));

						lobbyMobs.add(newMob);

						System.out.println("createNewMobs >>>>> CREATE MOB >>>>> " + newMob.toString());
					}

					// System.out.println("createNewMobs::oneMob=" + strMob);
				}
			}
		}

	}

}
