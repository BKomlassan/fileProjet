/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.dao.jpa;

import com.supfile.dao.UtilisateurDao;
import com.supfile.entity.Utilisateurs;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Benyi Jean marc
 */
@Stateful
public class JpaUtilisateurDao implements  UtilisateurDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Utilisateurs addUser(Utilisateurs utilisateurs) {
        try
                {
                    em.persist(utilisateurs);

                }
        catch (Exception e)
                {
                    System.err.println(e.getStackTrace());
                }
        return utilisateurs;
    }

    @Override
    public void removeUtilisateur(Utilisateurs utilisateurs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utilisateurs updateUtilisateur(Utilisateurs utilisateurs) {

        return em.merge(utilisateurs);
    }

    @Override
    public Utilisateurs findUtilisateurById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utilisateurs getUtilisateurByLogInAnPassword(String login, String pass) {
                try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Utilisateurs> query = cb.createQuery(Utilisateurs.class);

            Root<Utilisateurs> user = query.from(Utilisateurs.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.equal(user.get("login"), login)
            );
            predicates.add(
                    cb.equal(user.get("motDePasse"), pass)
            );

            query.where(predicates.toArray(new Predicate[predicates.size()]));

            if (em.createQuery(query).getSingleResult() != null) {
                return em.createQuery(query).getSingleResult();
            } else {
                return null;
            }

        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            return null;
        }
    }
    
   @Override
    public Utilisateurs getUtilisateurByLogin(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public List<Utilisateurs> getAllUtilisateur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
