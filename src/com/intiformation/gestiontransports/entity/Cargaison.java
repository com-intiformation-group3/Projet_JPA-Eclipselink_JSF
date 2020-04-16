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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity(name="cargaison")
@Table(name = "cargaisons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //stratégie : groupement dans une seule table
@DiscriminatorColumn(name = "type_cargaison", discriminatorType = DiscriminatorType.STRING)
public class Cargaison implements Serializable{
	
	//props
	@Id
	@Column(name = "reference")
	protected Long reference;
	@Column(name = "distance")
	protected Double distance;
	@Column(name = "date_livraison")
	protected String date_livraison;
	@Column(name = "temperature")
	Double temperature;
	@Column(name = "poids_max")
	Double poids_max;
	
	
	private Long fk;
	
	//props à ne pas mettre dans les ctors
	@Column(name = "volume_total")
	Double volume_total;
	@Column(name = "poids_total")
	Double poids_total;
	@Column(name = "cout_total")
	Double cout_total;
	
	
	
	/*solution pour ne pas avoir la table de jointure */
	@OneToMany(mappedBy = "cargaison", cascade = CascadeType.ALL)
	private List<Marchandise> marchandises;
	
	
	//association Cargaison et Utilisateur
	/**
	 * nature de la relation : plusieurs cargaisons pour une utilisateur
	 * 							many     cargaisons  to  one utilisateur
	 */
	@ManyToOne
	@JoinColumn(name = "utilisateur_id", referencedColumnName = "id_utilisateur", updatable=true) //gestion de la FK
	protected Utilisateur utilisateur;
	
//	protected Long utilisateurId;
	
	//ctors
	public Cargaison() {
	}
	
	public Cargaison(Long reference, Double distance, String date_livraison) {
		this.reference = reference;
		this.distance = distance;
		this.date_livraison = date_livraison;
	}

	//getters|setters
	public Long getReference() {
		return reference;
	}
	public void setReference(Long reference) {
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
	
	//////////////////////////////////////
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
	//////////////////////////////////////
	public Double getPoids_max() {
		return poids_max;
	}
	public void setPoids_max(Double poids_max) {
		this.poids_max = poids_max;
	}
	
	
	
	//pour volume total, poids total & cout total :
	public Double getVolume_total() {
		return volume_total;
	}
	public void setVolume_total(Double volume_total) {
		this.volume_total = volume_total;
	}

	public Double getPoids_total() {
		return poids_total;
	}
	public void setPoids_total(Double poids_total) {
		this.poids_total = poids_total;
	}

	public Double getCout_total() {
		return cout_total;
	}
	public void setCout_total(Double cout_total) {
		this.cout_total = cout_total;
	}

	
	public Long getFk() {
		return fk;
	}
	public void setFk(Long fk) {
		this.fk = fk;
	}
	
	
}
