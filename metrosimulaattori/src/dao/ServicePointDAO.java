package dao;

import application.simu.model.Palvelupiste;
import entity.ServicePoint;
import entity.Simulaattori;
import jakarta.persistence.EntityManager;

public class ServicePointDAO {

    public void lisaaPalvelupiste(Palvelupiste p) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public ServicePoint haePalvelupiste(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        ServicePoint p = em.find(ServicePoint.class, id);
        em.getTransaction().commit();
        return p;
    }

    public void poistaPalvelupiste(int id) {
        EntityManager em = datasource.MariaDbJpaConn.getInstance();
        em.getTransaction().begin();
        ServicePoint p = em.find(ServicePoint.class, id);
        if (p!=null) {
            em.remove(p);
        }
        em.getTransaction().commit();
    }

}
