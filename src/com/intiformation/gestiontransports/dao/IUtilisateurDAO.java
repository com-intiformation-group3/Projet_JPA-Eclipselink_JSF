package com.intiformation.gestiontransports.dao;

public interface IUtilisateurDAO {

	/*======= m�thodes propres � l'utilisateur ==========*/
	public boolean isUtilisateurExists(String pEmail, String pMdp);	
	
}
