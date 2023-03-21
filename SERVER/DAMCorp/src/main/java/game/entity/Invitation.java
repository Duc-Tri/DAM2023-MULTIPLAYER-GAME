package game.entity;

public class Invitation {

	Player expediteur;
	Player destinataire;
	String lobby;
	String message;
	
	
	
	
	
	public Invitation(Player expediteur, Player destinataire, String lobby, String message) {
		super();
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.lobby = lobby;
		this.message = message;
	}
	public Player getExpediteur() {
		return expediteur;
	}
	public void setExpediteur(Player expediteur) {
		this.expediteur = expediteur;
	}
	public Player getDestinataire() {
		return destinataire;
	}
	public void setDestinataire(Player destinataire) {
		this.destinataire = destinataire;
	}
	public String getLobby() {
		return lobby;
	}
	public void setLobby(String lobby) {
		this.lobby = lobby;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static void sendInvitation(Invitation invitation) {
		// TODO Auto-generated method stub
		Invitation[] invExp = invitation.getExpediteur().getInvitations();
		Invitation[] invDest = invitation.getDestinataire().getInvitations();
		
		for(Invitation i : invExp) {
			if(i==null) {
				i = invitation;
			}
		}
		for(Invitation i : invDest) {
			if(i==null) {
				i = invitation;
			}
		}
	}

	
	
	
	
}
