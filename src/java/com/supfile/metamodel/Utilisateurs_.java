/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.metamodel;


import com.supfile.entity.Utilisateurs;
import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author Benyi Jean marc
 */
@StaticMetamodel(Utilisateurs.class)
public class Utilisateurs_ {
    public static volatile SingularAttribute<Utilisateurs , Long> id ;
    public static volatile SingularAttribute<Utilisateurs , String > nom;
    public static volatile SingularAttribute<Utilisateurs , String> prenom;
    public static volatile SingularAttribute<Utilisateurs , String> login;
    public static volatile SingularAttribute<Utilisateurs , String> motDePasse;
    public static volatile SingularAttribute<Utilisateurs , String> email;
    public static volatile SingularAttribute<Utilisateurs , String> codePostal;
}
