
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.controller;

import com.supfile.entity.Utilisateurs;
import com.supfile.services.utilisateurService;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Benyi Jean marc
 */
@ManagedBean(name = "utilisateurController")
@SessionScoped
public class UtilisateurController implements Serializable {

    @EJB
    private utilisateurService utilisateurService;
    private String login;
    private String password;
    private String messageInformation;
    private Utilisateurs user = new Utilisateurs();
    private Utilisateurs userFb = new Utilisateurs();
    private Utilisateurs utilisateurConnecte = new Utilisateurs();
    private int nbUtilisateurs;

    public String enreg() throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        try {
            utilisateurService.addUser(user);
            createDossierByLogin(user.getLogin());
            return "login?faces-redirect=true";
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }

    }

    public String login() {

        try {
            if (utilisateurService.getUtilisateurByLoginAndPassword(this.user.getLogin().trim(), this.user.getMotDePasse().trim()) != null) {
               // this.setMessageInformation("Utilisateur trouvé en base de données");
                setUtilisateurConnecte(utilisateurService.getUtilisateurByLoginAndPassword(this.user.getLogin(), this.user.getMotDePasse()));
                return "library?faces-redirect=true";
            } else {
                this.setMessageInformation("Login or password is incorrect ! try again");
                return "login";
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return "login";
        }
    }
    
        public String loginFb() {
               // this.setMessageInformation("Utilisateur trouvé en base de données");            
                setUtilisateurConnecte(this.userFb);
                return "home?faces-redirect=true";
    }

    public String login_facebook() {

        return "facebook";

    }

    public String login_gmail() {

        return "Gamil";
    }

    public String update() {
        try {
            if (password != "") {
                utilisateurConnecte.setMotDePasse(password);
            }
            utilisateurService.updateUtilisateur(utilisateurConnecte);
            return "profil";
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return "profil";
        }
    }

    public void deconnexion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
    }

    public void createDossierByLogin(String login) {
        Path path = Paths.get("C:\\" + login);
        try {
            Files.createDirectories(path);
        } catch (Exception e) {

        }
    }

    public Utilisateurs getUser() {
        return user;
    }

    public void setUser(Utilisateurs user) {
        this.user = user;
    }

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

    public String getMessageInformation() {
        return messageInformation;
    }

    public void setMessageInformation(String messageInformation) {
        this.messageInformation = messageInformation;
    }

    public Utilisateurs getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateurs utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public int getNbUtilisateurs() {
        return utilisateurService.getAllUtilisateurs().size();
    }

    public void setNbUtilisateurs(int nbUtilisateurs) {
        this.nbUtilisateurs = nbUtilisateurs;
    }

    public Utilisateurs getUserFb() {
        return userFb;
    }

    public void setUserFb(Utilisateurs userFb) {
        this.userFb = userFb;
    }
    
    
    
    
}
