package com.intiformation.gestiontransports.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "marchandise")
@Table(name="marchandises")
public class Marchandise implements Serializable{

	//props
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto-incrementation
	@Column(name = "id_marchandise")
	private Long idMarchandise;
	@Column(name = "nom")
	private String nom;
	@Column(name = "poids")
	private Double poids;
	@Column(name = "volume")
	private Double volume;
	
	private Long fk;
	
	
	
	//association Marchandise et Cargaison
	/**
	 * nature de la relation : plusieurs marchandises pour une cargaison
	 * 							many     marchandises  to  one cargaison
	 */
	@ManyToOne
	@JoinColumn(name = "cargaison_ref", referencedColumnName = "reference", updatable=true) //gestion de la FK
	private Cargaison cargaison;



	//ctors
	public Marchandise() {
	}
	
	public Marchandise(String nom, Double poids, Double volume) {
		this.nom = nom;
		this.poids = poids;
		this.volume = volume;
	}
	

	
	//getters|setters
	public Long getIdMarchandise() {
		return idMarchandise;
	}
	public void setIdMarchandise(Long idMarchandise) {
		this.idMarchandise = idMarchandise;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public Double getPoids() {
		return poids;
	}
	public void setPoids(Double poids) {
		this.poids = poids;
	}

	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Cargaison getCargaison() {
		return cargaison;
	}
	public void setCargaison(Cargaison cargaison) {
		this.cargaison = cargaison;
	}

	public Long getFk() {
		return fk;
	}

	public void setFk(Long fk) {
		this.fk = fk;
	}
	
	
	
	
}
