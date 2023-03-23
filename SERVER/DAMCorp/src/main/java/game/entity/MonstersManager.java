package game.entity;

import java.util.ArrayList;

//#################################################################################################
// Gestionnaire de monstres, utilise un tableau de Mob
// Un par lobby
//#################################################################################################
public class MonstersManager {

	private int numLobby;
	private final static int MAX_MONSTERS = 50;
	// private Mob[] lobbyMobs = new Mob[MAX_MONSTERS];
	private ArrayList<Monster> lobbyMobs = new ArrayList<Monster>();

	private long lastUpdate;
	private String lastMobsStr; // dernier String reçu du client MASTER
	private String lastAttackedMobsStr;

	public MonstersManager(int numLobby) {
		super();
		this.numLobby = numLobby;
		System.out.println(numLobby + "@ MonstersManager CONSTRUCTOR !");
	}

	public int getNumLobby() {
		return numLobby;
	}

	public void setNumLobby(int numLobby) {
		this.numLobby = numLobby;
	}

	// ============================================================================================
	// TODO : REFACTO CET ALGO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// ============================================================================================
	public void setMonstersStr(String mobsStr) {
		lastMobsStr = mobsStr;

		recreateMonsters(lastMobsStr.split("!"));
	}

	// ============================================================================================
	//
	// ============================================================================================
	public void setAttackedMonsters(String attackedStr) {
		lastAttackedMobsStr = attackedStr;

		applyDamagesOnMonsters(attackedStr.split("!"));
	}

	// ============================================================================================
	// => les monstres du lobby, agglomérés en String, à reconstituer côté client
	// ============================================================================================
	private void applyDamagesOnMonsters(String[] attackedMobs) {

		ArrayList<Monster> tempMobs = new ArrayList<Monster>(); // EN LOCAL !!!

		if (attackedMobs != null && attackedMobs.length != 0) {

			for (String strAttacked : attackedMobs) {

				boolean found = false;

				if (strAttacked != null && !strAttacked.isEmpty()) {
					String[] attackedSplit = strAttacked.split(";");

					String oneMobId = attackedSplit[0];

					int damage = Integer.parseInt(attackedSplit[1]);

					System.out.println(numLobby + "@ applyDamagesOnMonsters @@@@@@@@@@@@@@@ " + oneMobId + "/" + damage
							+ " => #" + lobbyMobs.size());

					// UPDATE MOBS LIFE -------------------------------------------------
					for (int i = lobbyMobs.size() - 1; i >= 0; i--) {

						Monster lobbyMob = lobbyMobs.get(i);

						if (oneMobId.equalsIgnoreCase(lobbyMob.getUniqueID())) {
							found = true;

							lobbyMob.applyDamage(damage);

							// FLOOD !
							// System.out.println("recreateMonsters >>>>> UPDATE MOB >>>>> " +
							// updateMob.toString());

							System.out.println(numLobby + "/" + lobbyMobs.size()
									+ "@ applyDamagesOnMonsters @@@@@@@@@@@@@@@ UPDATE " + lobbyMob.getUniqueID() + "="
									+ lobbyMob.getCurrentLife());

						} else {
							// NON EXISTING MOB ???
							System.err.println(numLobby + "/" + lobbyMobs.size()
									+ " applyDamagesOnMonsters @@@@@@@@@@@@@@@ DONT EXIST " + oneMobId + "<>" + damage);
						}

						// System.out.println("createNewMobs >>>>> FOUND " + oneMobId);
					}
				}

			}

		}

		// System.out.println(numLobby + "@ applyDamagesOnMonsters @@@@@@@@@@@@@@@
		// lobbyMobs.size=" + lobbyMobs.size());
	}

	// ============================================================================================
	// => les monstres du lobby, agglomérés en String, à reconstituer côté client
	// ============================================================================================
	private void recreateMonsters(String[] mobsStr) {

		ArrayList<Monster> tempMobs = new ArrayList<Monster>(); // EN LOCAL !!!

		if (mobsStr != null && mobsStr.length != 0) {

			for (String strMob : mobsStr) {

				boolean found = false;

				if (strMob != null && !strMob.isEmpty()) {
					String[] strMobSplit = strMob.split(";");

					String oneMobId = strMobSplit[0];

					int life = Integer.parseInt(strMobSplit[4]);

					// System.out.println(numLobby + "@ recreateMonsters ### " + oneMobId + " => #"
					// + lobbyMobs.size());

					// UPDATE OR REMOVE DEAD MOBS -------------------------------------------------
					for (int i = lobbyMobs.size() - 1; i >= 0; i--) {

						Monster updateMob = lobbyMobs.get(i);

						if (oneMobId.equalsIgnoreCase(updateMob.getUniqueID())) {
							found = true;

							if (life != 999999) {
								updateMob.setFootX(strMobSplit[1]);
								updateMob.setY(strMobSplit[2]);
								updateMob.setFindRegion(strMobSplit[3]);
								updateMob.setCurrentLife(life);

								// FLOOD !
								// System.out.println("recreateMonsters >>>>> UPDATE MOB >>>>> " +
								// updateMob.toString());

								tempMobs.add(updateMob);

								// System.out.println(numLobby + "@ recreateMonsters ### UPDATE " +
								// updateMob.getUniqueID());

							} else {
								lobbyMobs.remove(i); // dead mob
							}

							// System.out.println("createNewMobs >>>>> FOUND " + oneMobId);

							break;
						}
					}

					// CREATE A NEW MOB -----------------------------------------------------------
					if (!found && life > 0) {

						// System.out.println("createNewMobs >>>>> NOT FOUND " + n + " / " + oneMobId);

						Monster newMob = new Monster(oneMobId);
						newMob.setFootX(strMobSplit[1]);
						newMob.setY(strMobSplit[2]);
						newMob.setFindRegion(strMobSplit[3]);
						newMob.setCurrentLife(Integer.parseInt(strMobSplit[4]));

						tempMobs.add(newMob);

						// System.out.println(numLobby + "@ recreateMonsters ### NEW " +
						// newMob.getUniqueID() + "/"
						// + newMob.getCurrentLife() + " => " + tempMobs.size());

						// lobbyMobs.add(newMob);

						// System.out.println("recreateMonsters >>>>> NEW MOB >>>>> " +
						// newMob.buildHttpResponse());
					}

				}

			}
		}

		lobbyMobs.clear();
		lobbyMobs = tempMobs;
		// System.out.println(numLobby + "@ recreateMonsters ### lobbyMobs.size=" +
		// lobbyMobs.size());
	}

	// ============================================================================================
	//
	// ============================================================================================
	public String buildMonstersHttpResponse() {
		StringBuilder sbMonsters = new StringBuilder();
		for (int i = lobbyMobs.size() - 1; i >= 0; i--) {
			Monster mob = lobbyMobs.get(i);

			// FLOOD
			// System.out.println(numLobby + "@ buildMonstersHttpResponse ----------
			// mob.life=" + mob.getCurrentLife());

			if (mob.getCurrentLife() != 999999) {
				sbMonsters.append(mob.buildHttpResponse());
			} else {
				lobbyMobs.remove(i);
			}
		}

		return sbMonsters.toString();
	}

}
