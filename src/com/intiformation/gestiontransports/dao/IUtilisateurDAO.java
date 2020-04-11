package com.intiformation.gestiontransports.dao;

public interface IUtilisateurDAO {

	/*======= méthodes propres à l'utilisateur ==========*/
	public boolean isUtilisateurExists(String pEmail, String pMdp);	
	
}
