package dao;

import application.simu.model.Palvelupiste;
import entity.ServicePoint;
import entity.Simulaattori;
import jakarta.persistence.EntityManager;

/**
 * sisältää metodit, joilla käsitellään ja välitetään dataa tietokannan ja ServicePoint-olioiden välillä.
 * @author Eetu Soronen
 * @version 1
 */
public class ServicePointDAO {

    /**
     * Lisää Station-olion tietokantaan
     * @param p Palvelupiste-olio
     */
    public void lisaaPalvelupiste(Palvelupiste p) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    /**
     * Hakee Station-olion tietokannasta sen id:n perusteella
     * @param id
     * @return
     */
    public ServicePoint haePalvelupiste(int id) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        ServicePoint p = em.find(ServicePoint.class, id);
        em.getTransaction().commit();
        return p;
    }

    /**
     * poistaa Station-olion tietokannasta sen id:n perusteella
     * @param id
     */
    public void poistaPalvelupiste(int id) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        ServicePoint p = em.find(ServicePoint.class, id);
        if (p!=null) {
            em.remove(p);
        }
        em.getTransaction().commit();
    }
}
