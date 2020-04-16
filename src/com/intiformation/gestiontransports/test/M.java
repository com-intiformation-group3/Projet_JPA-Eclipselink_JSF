package com.intiformation.gestiontransports.test;

import com.intiformation.gestiontransports.dao.GenericDAOImpl;
import com.intiformation.gestiontransports.dao.IGenericDAO;
import com.intiformation.gestiontransports.entity.Cargaison;
import com.intiformation.gestiontransports.entity.Marchandise;

public class M {

	private static Cargaison c;
	private static Marchandise marchandise;
	
	public static void t(Marchandise m) {
		marchandise = m;
		//association de la marchandise à la cargaison
		IGenericDAO<Cargaison> cargaisonDAO = new GenericDAOImpl<Cargaison>(Cargaison.class);
		c = (Cargaison) cargaisonDAO.getById(marchandise.getFk());
//		marchandise.setCargaison(c);
	}
	
	
	public static Cargaison getC() {
		return c;
	}
	public static void setC(Cargaison c) {
		M.c = c;
	}

	public Marchandise getMarchandise() {
		return marchandise;
	}
	public void setMarchandise(Marchandise m) {
		M.marchandise = m;
	}

	public static void main(String[] args) {

		//association de la marchandise à la cargaison
		marchandise.setCargaison(c);

	}
	

}
