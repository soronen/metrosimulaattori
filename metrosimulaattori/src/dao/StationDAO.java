package dao;

import entity.Simulaattori;
import entity.Station;
import jakarta.persistence.EntityManager;

/**
 * sisältää metodit, joilla käsitellään ja välitetään dataa tietokannan ja Station-olioiden välillä.
 * @author Eetu Soronen
 * @version 1
 */
public class StationDAO {

    /**
     * Lisää Station-olion tietokantaan
     * @param s
     */
    public void lisaaAsema(Station s) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    /**
     * Hakee Station-olion tietokannasta sen id:n perusteella.
     * @param id
     * @return
     */
    public Station haeAsema(int id) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        Station s = em.find(Station.class, id);
        em.getTransaction().commit();
        return s;
    }

    /**
     * Poistaa Station-olion tietokannasta sen id:n perusteella.
     * @param id
     */
    public void poistaAsema(int id) {
        EntityManager em = datasource.MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        Station s = em.find(Station.class, id);
        if (s!=null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }
}
