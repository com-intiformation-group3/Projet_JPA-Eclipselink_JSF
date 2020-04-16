package com.intiformation.gestiontransports.entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("routière")
public class CargaisonRoutiere extends Cargaison{

	
	//ctors
	public CargaisonRoutiere() {
	}

	public CargaisonRoutiere(Long reference, Double distance, String date_livraison, Double temperature) {
		super( reference,  distance,  date_livraison);
//		this.reference = reference;
//		this.distance = distance;
//		this.date_livraison = date_livraison;
		this.temperature = 11.;
//		this.utilisateur_id = utilisateur.getIdUtilisateur();
	}

	

	
}
