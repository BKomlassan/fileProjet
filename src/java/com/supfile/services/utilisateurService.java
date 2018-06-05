/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.services;

import com.supfile.dao.UtilisateurDao;
import com.supfile.entity.Utilisateurs;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Benyi Jean marc
 */

@Stateful
public class utilisateurService {
   
@EJB
private UtilisateurDao utilisateurDao;

public Utilisateurs addUser(Utilisateurs utilisateur) {
    return this.utilisateurDao.addUser(utilisateur);
}

public Utilisateurs getUtilisateurByLoginAndPassword(String login , String password) { 
    return this.utilisateurDao.getUtilisateurByLogInAnPassword(login, password);
}

public Utilisateurs getUtilisateurByLogin(String login){
    return this.utilisateurDao.getUtilisateurByLogin(login);
}

public Utilisateurs updateUtilisateur(Utilisateurs utilisateurs) {
    return  this.utilisateurDao.updateUtilisateur(utilisateurs);
}

public  List<Utilisateurs> getAllUtilisateurs() { 
    return  this.utilisateurDao.getAllUtilisateur();
}

public Utilisateurs findUtilisateurById(long id) { 
    return this.utilisateurDao.findUtilisateurById(id);
}



}
