package com.intiformation.gestiontransports.entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("aérienne")
public class CargaisonAerienne extends Cargaison{

	//prop
	@Column(name = "poids_max")
	Double poids_max = 1500.;

	
	//ctors
	public CargaisonAerienne() {
	}

	public CargaisonAerienne(String reference, Double distance, String date_livraison, Double poids_max) {
		super( reference,  distance,  date_livraison);
//		this.reference = reference;
//		this.distance = distance;
//		this.date_livraison = date_livraison;
		poids_max = this.poids_max;
	}

	
	//getters|setters
	public Double getPoids_max() {
		return poids_max;
	}
	public void setPoids_max(Double poids_max) {
		this.poids_max = poids_max;
	}
	
}
