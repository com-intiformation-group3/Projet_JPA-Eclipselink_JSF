package com.intiformation.gestiontransports.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cargaisons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //stratégie : groupement dans une seule table
@DiscriminatorColumn(name = "type_cargaison", discriminatorType = DiscriminatorType.STRING)
public class Cargaison implements Serializable{
	
	//props
	@Id
	@Column(name = "reference")
	protected String reference;
	@Column(name = "distance")
	protected Double distance;
	@Column(name = "date_livraison")
	protected String date_livraison;
	
	/*solution pour ne pas avoir la table de jointure */
	@OneToMany(mappedBy = "cargaison", cascade = CascadeType.ALL)
	private List<Marchandise> marchandises;
	
	
	//association Cargaison et Utilisateur
	/**
	 * nature de la relation : plusieurs cargaisons pour une utilisateur
	 * 							many     cargaisons  to  one utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "utilisateur_id", referencedColumnName = "id_utilisateur") //gestion de la FK
	private Utilisateur utilisateur;
	
	
	
	//ctors
	public Cargaison() {
	}
	
	public Cargaison(String reference, Double distance, String date_livraison) {
		this.reference = reference;
		this.distance = distance;
		this.date_livraison = date_livraison;
	}

	//getters|setters
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getDate_livraison() {
		return date_livraison;
	}
	public void setDate_livraison(String date_livraison) {
		this.date_livraison = date_livraison;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
	
}
