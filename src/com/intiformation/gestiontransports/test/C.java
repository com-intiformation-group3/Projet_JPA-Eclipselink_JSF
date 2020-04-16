package com.intiformation.gestiontransports.test;

import com.intiformation.gestiontransports.dao.GenericDAOImpl;
import com.intiformation.gestiontransports.dao.IGenericDAO;
import com.intiformation.gestiontransports.dao.UtilisateurDAOImpl;
import com.intiformation.gestiontransports.entity.Cargaison;
import com.intiformation.gestiontransports.entity.Marchandise;
import com.intiformation.gestiontransports.entity.Utilisateur;

public class C {

	private static Utilisateur u;
	private static Cargaison cargaison;
	
	public static void t(Cargaison c) {
		cargaison = c;
		//association de la cargaison à l'utilisateur
		IGenericDAO<Utilisateur> utilisateurDAO = new GenericDAOImpl<Utilisateur>(Utilisateur.class);
		u = (Utilisateur) utilisateurDAO.getById(cargaison.getFk());
	}
	
	
	public static Utilisateur getU() {
		return u;
	}
	public static void setU(Utilisateur u) {
		C.u = u;
	}

	public Cargaison cargaison() {
		return cargaison;
	}
	public void setCargaison(Cargaison c) {
		C.cargaison = c;
	}

	public static void main(String[] args) {

		//association de la cargaison à l'utilisateur
		cargaison.setUtilisateur(u);

	}
	

}
