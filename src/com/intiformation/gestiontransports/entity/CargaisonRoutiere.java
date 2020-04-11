package com.intiformation.gestiontransports.entity;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("routière")
public class CargaisonRoutiere extends Cargaison{

	//prop
	@Column(name = "temperature")
	Double temperature;

	
	//ctors
	public CargaisonRoutiere() {
	}

	public CargaisonRoutiere(String reference, Double distance, String date_livraison, Double temperature) {
		super( reference,  distance,  date_livraison);
//		this.reference = reference;
//		this.distance = distance;
//		this.date_livraison = date_livraison;
		this.temperature = temperature;
	}

	
	//getters|setters
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
}
