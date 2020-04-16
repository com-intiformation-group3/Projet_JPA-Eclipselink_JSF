package com.intiformation.gestiontransports.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.intiformation.gestiontransports.dao.GenericDAOImpl;
import com.intiformation.gestiontransports.dao.IGenericDAO;
import com.intiformation.gestiontransports.entity.Cargaison;
import com.intiformation.gestiontransports.entity.Marchandise;
import com.intiformation.gestiontransports.test.C;



/**
 * managedbean pour la gestion des cargaisons
 *
 */
@SuppressWarnings("serial")
@ManagedBean(name = "gestionCargaison")
@SessionScoped
public class GestionCargaisonBean implements Serializable{

	//props
	private Cargaison cargaison;
	private Marchandise marchandise; //pour volume total, poids total & co�t total
	private long filtreUtilisateurId = 0;
	
	List<Cargaison> liste_cargaisons;
	List<Marchandise> liste_marchandises; //pour volume total & poids total
	
	IGenericDAO<Cargaison> cargaisonDAO;
	IGenericDAO<Marchandise> marchandiseDAO; //pour volume total, poids total & co�t total
	
	
	//ctors
	public GestionCargaisonBean() { //ctor vide pour l'instanciation du managedbean
		
		cargaisonDAO = new GenericDAOImpl<Cargaison>(Cargaison.class);  //on en profite pour l'instancier � l'appel
		marchandiseDAO = new GenericDAOImpl<Marchandise>(Marchandise.class); //pour volume total, poids total & co�t total
	}
	
	
	
	
	//m�thodes
	public void resetFiltre(){
		this.filtreUtilisateurId = 0;
	}
	/**
	 * permet de r�cup la liste des cargaisons dans la bdd via la dao.
	 * permet aussi d'alimenter la table dans accueil.xhtml pour affichage.
	 */
	public List<Cargaison> findAllCargaisonsBdd(){
		
		if(filtreUtilisateurId > 0) {
			
			liste_cargaisons = cargaisonDAO.getAllByFKc(filtreUtilisateurId);
			determinerVolumePoidsCout(liste_cargaisons);
			
		}else {
			
			liste_cargaisons = cargaisonDAO.getAll();
			determinerVolumePoidsCout(liste_cargaisons);

		}
		return liste_cargaisons;
	}

	
///////////////////////////////////////////////////////////////// SECTION:  Volume Total | Poids Total | Co�t Total \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void determinerVolumePoidsCout(List<Cargaison> liste_cargaisons) {
		
		Double volume_total = 0.;
		Double poids_total = 0.;
		Double distance = 0.;
		for (Cargaison c : liste_cargaisons) {
			
			//r�cup de la liste des marchandises associ�es � la cargaison
	//		liste_marchandises = marchandiseDAO.getAllByFKs("cargaison_ref", cargaison.getReference());
			List<Marchandise> liste_m = marchandiseDAO.getAllByFKm(c.getReference());
			
			//D�terminer le volume & le poids total des marchandises de cette liste
			volume_total = 0.;
			poids_total = 0.;
			for (Marchandise m : liste_m) {
				volume_total = volume_total+ m.getVolume();
				poids_total = poids_total + m.getPoids();
			}
		
			//Set du volume & poids total des marchandises dans la variable volume_total & poids_total de la cargaison
			c.setVolume_total(volume_total);
			c.setPoids_total(poids_total);
			
			
			//D�terminer le co�t total de la cargaison
			distance = c.getDistance();
			
			if(c.getTemperature()== null)
				c.setTemperature(0.0);
			if(c.getTemperature() == 0.0) {	//le cas d'une cargaison a�rienne
		
				if(volume_total < 80000)
					c.setCout_total(10*distance*poids_total);
				else
					c.setCout_total(12*distance*poids_total);
			
			}else {	//le cas d'une cargaison routi�re
		
				if(volume_total < 380000)
					c.setCout_total(4*distance*poids_total);
				else
					c.setCout_total(6*distance*poids_total);
		
			}
		}
		
	}	
/////////////////////////////////////////////////////////////////// SECTION  SUPPRESSION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/**
	 * invoqu�e au click sur le lien 'supprimer' de accueil.xhtml <br/>
	 * au click, l'�v�nement javax.faces.event.ActionEvent se d�clenche <br/>
	 * l'�v�nement encapsule toutes les infos concernant le composant <br/>
	 * permet de supprimer un client dans la bdd via la dao <br/>
	 */
	public void supprimerCargaison(ActionEvent event) {
		//1. r�cup du param pass� dans le composant au click du lien 'supprimer'
		UIParameter cp = (UIParameter) event.getComponent().findComponent("deleteID");
		
		//2. r�cup de la valeur du param => l'id du client � supprimer
		Long cargaisonID = (long) cp.getValue();
		
		//3. suppression de la cargaison dans la bdd via la dao
		//3.1 r�cup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		

		
		if(cargaisonDAO.supprimer(cargaisonID)) {  //suppression ok
			
			//envoi d'un message vers la vue via le context
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					 									"Suppression r�ussie", 
					 									"La cargaison a �t� supprim� avec succ�s"));
			
		}else {  //suppression not ok
			
			//envoi d'un message vers la vue via le context
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
														"Echec de la Suppression", 
													 	"La suppression de la cargaison a �chou�e"));
			
		}
	}

/////////////////////////////////////////////////////////////////// SECTION  MODIFICATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	/**
	 * invoqu�e au click sur le lien 'modifier' de accueil.xhtml <br/>
	 * au click, l'�v�nement javax.faces.event.ActionEvent se d�clenche <br/>
	 * l'�v�nement encapsule toutes les infos concernant le composant <br/>
	 * permet de r�cup�rer les infos du client � modifier � partir de la bdd, 
	 * et lier ces infos � la page modifier-client.xhtml <br/>
	 */
	public void selectionnerCargaison(ActionEvent event) {
		//1. r�cup du param pass� dans le composant au click du lien 'modifier'
		UIParameter cp = (UIParameter) event.getComponent().findComponent("updateID");
		
		//2. r�cup de la valeur du param => l'id de la cargaison � modifier
		Long cargaisonID = (long) cp.getValue();
		
		//3. r�cup de la cargaison dans la bdd par l'id
		Cargaison cargaison = cargaisonDAO.getById(cargaisonID);
		
		//4. affectation de la cargaison � modifier � la prop 'cargaison' du managedbean
		setCargaison(cargaison);
		
		/**
		 * dans la page modifier-cargaison.xhtml => on r�cup�re la cargaison � modifier via 
		 * la prop cargaison du MB (managedbean)
		 */
	}
	

	/**
	 * invoqu�e au click du bouton modifier de la page modifier-cargaison.xhtml <br/>
	 * permet de modifier le cargaison dans la bdd
	 */
	public void modifierCargaison(ActionEvent event) {
		
		/**
		 * la prop cargaison du MB encapsule les infos de la cargaison � modifier dans la bdd
		 */
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		C.t(cargaison);
		C.main(null);
		
		if(cargaisonDAO.modifier(cargaison)) {  //modif ok
			
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
														 "Modification r�ussie", 
														 "La cargaison a �t� modifi� avec succ�s");
			contextJSF.addMessage(null,  message);
		
		}else {  //modif not ok

			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
														 "Echec de la Modification", 
														 "La modification de la cargaison a �chou�e");
			contextJSF.addMessage(null,  message);
			
		}
		
	}
	
	
///////////////////////////////////////////////////////////////////////// SECTION  AJOUT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	
	/**
	 * permet d'instancier un objet de type cargaison � vide pour pouvoir r�cup les valeurs 
	 * saisies dans le formulaire d'ajout de la page ajouter-cargaison.xhtml <br/>
	 * cela permet d'�viter un nullPointerException <br/>
	 */
	public void initialiserCargaison(ActionEvent event) {
		
		//instanciation d'un nouveau objet cargaison vide
		Cargaison cargaisonAdd = new Cargaison();
		
		//affectation de l'objet � la prop cargaison du MB
		setCargaison(cargaisonAdd);
	
	}
	
	
	/**
	 * invoqu�e au click du bouton Ajouter de la page ajouter_cargaison.xhtml <br/>
	 * permet d'ajouter une nouvelle cargaison dans la bdd <br/>
	 * @param event
	 */
	public void ajouterCargaison(ActionEvent event) {
		//r�cup le context de JSF pour l'envoi de messages vers la vue
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		C.t(cargaison);
		C.main(null);
		
		//ajout dans la bdd
		if (cargaisonDAO.ajouter(cargaison)) {	//ajout ok 
			
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
														 "Ajout r�ussie", 
														 "La cargaison a �t� ajout� avec succ�s");
			contextJSF.addMessage(null,  message);
			
		} else {	//ajout not ok

			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
					 								"Echec de l'ajout", 
					 								"L'ajout de la cargaison a �chou�e");
			contextJSF.addMessage(null,  message);
			
		}
	}
	
	

	

	
	
	
	
	
	
	
	//getters|setters
	public Cargaison getCargaison() {
		return cargaison;
	}

	public void setCargaison(Cargaison cargaison) {
		this.cargaison = cargaison;
	}

	public Long getFiltreUtilisateurId() {
		return filtreUtilisateurId;
	}
	public void setFiltreUtilisateurId(Long filtreUtilisateurId) {
		this.filtreUtilisateurId = filtreUtilisateurId;
	}


	//pour volume total & poids total :
	public Marchandise getMarchandise() {
		return marchandise;
	}
	public void setMarchandise(Marchandise marchandise) {
		this.marchandise = marchandise;
	}
	
}
