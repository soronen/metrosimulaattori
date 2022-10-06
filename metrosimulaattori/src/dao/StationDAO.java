package dao;

import entity.Simulaattori;
import entity.Station;
import jakarta.persistence.EntityManager;

public class StationDAO {

    public void lisaaAsema(Station s) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    public Station haeAsema(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Station s = em.find(Station.class, id);
        em.getTransaction().commit();
        return s;
    }

    public void poistaAsema(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Station s = em.find(Station.class, id);
        if (s!=null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }
}
