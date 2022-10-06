package entity;

import application.simu.model.Palvelupiste;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.security.Provider;

@Entity
public class Simulaattori {

    private int simunKesto;

    @ManyToOne
    private ServicePoint entrance;
    @ManyToOne
    private ServicePoint ticketsales;
    @ManyToOne
    private ServicePoint ticketcheck;
    @ManyToOne
    private ServicePoint metro;

    @OneToOne
    private Station asema;

    public Simulaattori() {};


    @Id
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
