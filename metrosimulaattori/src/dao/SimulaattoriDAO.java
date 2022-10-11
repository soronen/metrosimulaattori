package dao;

import datasource.MySqlJpaConn;
import entity.Simulaattori;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * sisältää metodit, joilla käsitellään ja välitetään dataa tietokannan ja Simulaattori-olioiden välillä.
 * @author Eetu Soronen
 * @version 1
 */
public class SimulaattoriDAO implements ISimulaattoriDAO {

    /**
     * Lisää Simulator-olion tietokantaan
     * @param s Simulaattori
     */
    @Override
    public void lisaaSimulaattori(Simulaattori s) {
        EntityManager em = MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    /**
     * Hakee simulaattorin tietokannasta sen id:n perustella.
     * @param id
     * @return Simulaattori-olio
     */
    @Override
    public Simulaattori haeSimulaattori(int id) {
        EntityManager em = MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        em.getTransaction().commit();
        return s;
    }

    /**
     * Poistaa Simulaattori-olion tietokannasta sen id:n perusteella.
     * @param id
     */
    @Override
    public void poistaSimulaattori(int id) {
        EntityManager em = MySqlJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        if (s!=null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }

    /**
     * Palauttaa kaikki tietokannan Simulator-oliot (jotka taas sisältävät kaikki Station ja ServicePoint -oliot..)
     * @return List<Simulaattori> olio, joka sisältää kaikki tietokannan simulaattorit
     */
    @Override
    public List<Simulaattori> listaaSimulaattorit() {
        EntityManager em = MySqlJpaConn.getInstance();
        String jpqlQuery = "SELECT s FROM Simulaattori s";
        Query q = em.createQuery(jpqlQuery);
        List<Simulaattori> resultList = q.getResultList();
        return resultList;
    }
}
