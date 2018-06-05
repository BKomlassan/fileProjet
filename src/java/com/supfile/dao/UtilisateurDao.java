/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.dao;

import com.supfile.entity.Utilisateurs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Benyi Jean marc
 */
@Local
public interface UtilisateurDao {

 public Utilisateurs utilisateurs = new Utilisateurs();
 public Utilisateurs addUser (Utilisateurs utilisateurs);
 public void removeUtilisateur (Utilisateurs utilisateurs);
 public Utilisateurs updateUtilisateur(Utilisateurs utilisateurs);
 public Utilisateurs findUtilisateurById(long id);
 public Utilisateurs getUtilisateurByLogInAnPassword(String login, String pass);
 public Utilisateurs getUtilisateurByLogin(String login);
 public List<Utilisateurs> getAllUtilisateur();

}
