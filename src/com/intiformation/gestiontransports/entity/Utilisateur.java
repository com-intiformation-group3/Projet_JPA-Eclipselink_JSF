package com.intiformation.gestiontransports.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="utilisateur")
@Table(name="utilisateurs")
@NamedQueries({@NamedQuery(name="Utilisateur.isExists", 
                           query="SELECT COUNT(u.idUtilisateur) FROM utilisateur u WHERE u.email = ?1 AND u.motDePasse = ?2")})
public class Utilisateur {

	//props
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_utilisateur")
	private Long idUtilisateur;

	@Column(name="nom")
	private String nom;

	@Column(name="email")
	private String email;
	
	@Column(name="mot_de_passe")
	private String motDePasse;


	
	/*solution pour ne pas avoir la table de jointure */
	@OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
	private List<Cargaison> cargaisons;	
	
	
	
	//ctors
	public Utilisateur() {
	}
	public Utilisateur(Long idUtilisateur, String nom, String email, String motDePasse) {
		this.idUtilisateur = idUtilisateur;
		this.nom = nom;
		this.email = email;
		this.motDePasse = motDePasse;
	}
	public Utilisateur(String nom, String email, String motDePasse) {
		super();
		this.nom = nom;
		this.email = email;
		this.motDePasse = motDePasse;
	}

	
	//getters|setters
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	@Override
	public String toString() {
		return "[idUtilisateur=" + idUtilisateur + ", nom=" + nom + ", email=" + email + ", motDePasse=" + motDePasse + "]";
	}	
	
}
