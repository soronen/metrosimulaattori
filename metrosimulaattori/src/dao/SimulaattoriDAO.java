package dao;

import entity.Simulaattori;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

/**
 * sisältää metodit, joilla käsitellään ja välitetään dataa tietokannan ja Simulaattori-olioiden välillä.
 * @author Eetu Soronen
 * @version 1
 */
public class SimulaattoriDAO {

    /**
     * Lisää Simulator-olion tietokantaan
     * @param s Simulaattori
     */
    public static void lisaaSimulaattori(Simulaattori s) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    /**
     * Hakee simulaattorin tietokannasta sen id:n perustella.
     * @param id
     * @return Simulaattori-olio
     */
    public Simulaattori haeSimulaattori(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        em.getTransaction().commit();
        return s;
    }

    /**
     * Poistaa Simulaattori-olion tietokannasta sen id:n perusteella.
     * @param id
     */
    public void poistaSimulaattori(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        if (s!=null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }

    // testaamaton!
    /**
     * Palauttaa kaikki tietokannan Simulator-oliot (jotka taas sisältävät kaikki Station ja ServicePoint -oliot..)
     * @return List<Simulaattori> olio, joka sisältää kaikki tietokannan simulaattorit
     */
    public List<Simulaattori> listaaSimulaattorit() {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        String jpqlQuery = "SELECT s FROM Simulaattori s";
        Query q = em.createQuery(jpqlQuery);
        List<Simulaattori> resultList = q.getResultList();
        return resultList;
    }

}
