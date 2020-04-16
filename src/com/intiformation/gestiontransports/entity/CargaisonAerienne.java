package com.intiformation.gestiontransports.entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("aérienne")
public class CargaisonAerienne extends Cargaison{


	//ctors
	public CargaisonAerienne() {
	}

	public CargaisonAerienne(Long reference, Double distance, String date_livraison, Double poids_max) {
		super( reference,  distance,  date_livraison);
//		this.reference = reference;
//		this.distance = distance;
//		this.date_livraison = date_livraison;
		this.poids_max = 1500.;
//		this.utilisateur_id = utilisateur.getIdUtilisateur();
	}

	
	
}
