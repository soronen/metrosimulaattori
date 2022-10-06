package dao;

import entity.Simulaattori;
import jakarta.persistence.EntityManager;

public class SimulaattoriDAO {

    public static void lisaaSimulaattori(Simulaattori s) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }

    public Simulaattori haeSimulaattori(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        em.getTransaction().commit();
        return s;
    }

    public void poistaSimulaattori(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        Simulaattori s = em.find(Simulaattori.class, id);
        if (s!=null) {
            em.remove(s);
        }
        em.getTransaction().commit();
    }


}
