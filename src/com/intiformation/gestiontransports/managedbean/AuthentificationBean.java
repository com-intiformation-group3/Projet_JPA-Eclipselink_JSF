package com.intiformation.gestiontransports.managedbean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.intiformation.gestiontransports.dao.UtilisateurDAOImpl;
import com.intiformation.gestiontransports.dao.IUtilisateurDAO;


@SuppressWarnings("serial")
@ManagedBean(name = "authenticationBean")
@SessionScoped
public class AuthentificationBean implements Serializable{
	
	//props
	private String login;
	private String password;
	
	//dao
	private IUtilisateurDAO utilisateurDAO;
	

	
	
	
	//ctor
	public AuthentificationBean() { //pour l'instanciation du managedbean
		utilisateurDAO = new UtilisateurDAOImpl();
	}

	
	
	
	
	//méthodes
	public String connecterUtilisateur() {		//permet de connecter l'utilisateur => création de la session
		
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//1. vérif si l'utilisateur existe dans la bdd
		if(utilisateurDAO.isUtilisateurExists(login, password)){	//s'il existe
			
			
			//création de la session pour l'utilisateur (création d'un id de session)
			HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(true);
			
			//sauvegarde du login et password dans la session
			session.setAttribute("user_login", login);
			session.setAttribute("user_password", password);
			
			//redirection (navigation) vers la page accueil.xhtml
			return "accueil.xhtml";
			
		}else {	//n'existe pas
			/**
			 *  envoi d'un msg vers la vue via la classe FacesMessage et le context
			 */
			//déf du message via FacesMessage
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,
													"Echec de connexion ",
													" - Identifiant ou Mot de passe invalide");
			
			//envoi du message vers la vue via le context de JSF
			/**
			 * addMessage(String, FacesMessage) :
			 * 		- String : soit l'id du composant. Message pour ce composant
			 * 		- null : message global pour l'ensemble de la page
			 */
			contextJSF.addMessage(null, message);
			
			//redirection (navigation) vers la page authentification.xhtml
			return "authentification.xhtml";
		}
	}
	
	
	public String deconnecterUtilisateur() {		//permet de deconnecter l'utilisateur => destruction de la session
		//récup du context de JSF
		FacesContext contextJSF = FacesContext.getCurrentInstance();
		
		//récup de la session de l'utilisateur
		HttpSession session = (HttpSession) contextJSF.getExternalContext().getSession(false);
		
		//déconnexion
		session.invalidate();
		
		//redirection vers la page authentification.xhtml
		return "authentification.xhtml";
	}
	
	
	
	
	
	//getters|setters
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
