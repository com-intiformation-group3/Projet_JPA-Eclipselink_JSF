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
import com.intiformation.gestiontransports.test.M;




@SuppressWarnings("serial")
@ManagedBean(name = "gestionMarchandise")
@SessionScoped
public class GestionMarchandiseBean implements Serializable{

	//props
	private Marchandise marchandise;
	private long filtreCargaisonId = 0;
	
	List<Marchandise> liste_marchandises;
	
	IGenericDAO<Marchandise> marchandiseDAO;
	
	
	
	//ctors
	public GestionMarchandiseBean() { //ctor vide pour l'instanciation du managedbean
		marchandiseDAO = new GenericDAOImpl<Marchandise>(Marchandise.class);
	}
	
	
	
	

	//méthodes
	public void resetFiltre(){
		this.filtreCargaisonId = 0;
	}
	/**
	 * permet de récup la liste des marchandises dans la bdd via la dao.
	 * permet aussi d'alimenter la table dans accueil.xhtml pour affichage.
	 */
	public List<Marchandise> findAllMarchandisesBdd(){
		
		if(filtreCargaisonId > 0)
			liste_marchandises = marchandiseDAO.getAllByFKm(filtreCargaisonId);
		else
			liste_marchandises = marchandiseDAO.getAll();
		
		return liste_marchandises;
	}
	


/////////////////////////////////////////////////////////////////// SECTION  SUPPRESSION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public void supprimerMarchandise(ActionEvent event) {
		
		//1. récup du param passé dans le composant au click du lien 'supprimer'
		UIParameter cp = (UIParameter) event.getComponent().findComponent("deleteID");
		
		//2. récup de la valeur du param => l'id de la marchandise à supprimer
		Long marchandiseID = (long) cp.getValue();
		
		//3. suppression de la marchandise dans la bdd via la dao
		//3.1 récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		
		
		if(marchandiseDAO.supprimer(marchandiseID)) {  //suppression ok
		
			//envoi d'un message vers la vue via le context
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
														"Suppression réussie", 
														"La marchandise a été supprimée avec succès"));
		
		}else {  //suppression not ok
		
			//envoi d'un message vers la vue via le context
			contextJSF.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
														"Echec de la Suppression", 
														"La suppression de la marchandise a échouée"));
		}
	}
	
/////////////////////////////////////////////////////////////////// SECTION  MODIFICATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public void selectionnerMarchandise(ActionEvent event) {
		
		//1. récup du param passé dans le composant au click du lien 'modifier'
		UIParameter cp = (UIParameter) event.getComponent().findComponent("updateID");
		
		//2. récup de la valeur du param => l'id de la marchandise à modifier
		Long marchandiseID = (long) cp.getValue();
		
		//3. récup de la marchandise dans la bdd par l'id
		Marchandise marchandise = (Marchandise) marchandiseDAO.getById(marchandiseID);
		
		//4. affectation de la marchandise à modifier à la prop 'marchandise' du managedbean
		setMarchandise(marchandise);
	
	/**
	* dans la page modifier-marchandise.xhtml => on récupère la marchandise à modifier via 
	* la prop compte du MB (managedbean)
	*/
	}
	
	

	public void modifierMarchandise(ActionEvent event) {	//invoquée au click du bouton modifier de la page modifier-marchandise.xhtml
	
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		M.t(marchandise);
		M.main(null);
		
		if(marchandiseDAO.modifier(marchandise)) {  //modif ok
		
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
													"Modification réussie", 
													"La marchandise a été modifiée avec succès");
			contextJSF.addMessage(null,  message);
		
		}else {  //modif not ok
		
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
													"Echec de la Modification", 
													"La modification de la marchandise a échouée");
			contextJSF.addMessage(null,  message);
		}
	}
	
	
///////////////////////////////////////////////////////////////////////// SECTION  AJOUT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void initialiserMarchandise(ActionEvent event) {
	
		//instanciation d'un nouveau objet marchandise vide
		Marchandise marchandiseAdd = new Marchandise();
		
		//affectation de l'objet à la prop marchandise du MB
		setMarchandise(marchandiseAdd);
	
	}
	
	
	
	public void ajouterMarchandise(ActionEvent event) {
		
		//récup le context de JSF pour l'envoi de messages vers la vue
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//association (bricolée) de la marchandise à la cargaison
		M.t(marchandise);
		M.main(null);
		
		//ajout dans la bdd
		if (marchandiseDAO.ajouter(marchandise)) {	//ajout ok 
		
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
													"Ajout réussie", 
													"La marchandise a été ajoutée avec succès");
			contextJSF.addMessage(null,  message);
		
		} else {	//ajout not ok
		
			//envoi d'un message vers la vue via le context
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
													"Echec de l'ajout", 
													"L'ajout de la marchandise a échoué");
			contextJSF.addMessage(null,  message);
		
		}
	}
	
	

	
	
	
	
	
	//getters|setters
	public Marchandise getMarchandise() {
	return marchandise;
	}
	
	public void setMarchandise(Marchandise marchandise) {
	this.marchandise = marchandise;
	}

	
	public Long getFiltreCargaisonId() {
		return filtreCargaisonId;
	}

	public void setFiltreCargaisonId(Long filtreCargaisonId) {
		this.filtreCargaisonId = filtreCargaisonId;
	}

}
