package entity;

import application.simu.model.Palvelupiste;
import jakarta.persistence.*;

import java.security.Provider;

@Entity
public class Simulaattori {

    private int simunKesto;

    @ManyToOne(cascade=CascadeType.ALL)
    private ServicePoint entrance;
    @ManyToOne(cascade=CascadeType.ALL)
    private ServicePoint ticketsales;
    @ManyToOne(cascade=CascadeType.ALL)
    private ServicePoint ticketcheck;
    @ManyToOne(cascade=CascadeType.ALL)
    private ServicePoint metro;

    @OneToOne(cascade=CascadeType.ALL)
    private Station asema;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    public Simulaattori(int simunKesto, ServicePoint entrance, ServicePoint ticketsales, ServicePoint ticketcheck, ServicePoint metro, Station asema) {
        this.simunKesto = simunKesto;
        this.entrance = entrance;
        this.ticketsales = ticketsales;
        this.ticketcheck = ticketcheck;
        this.metro = metro;
        this.asema = asema;
    }

    public Simulaattori() {};


    public int getId() {
        return id;
    }

    public int getSimunKesto() {
        return simunKesto;
    }

    public void setSimunKesto(int simunKesto) {
        this.simunKesto = simunKesto;
    }

    public ServicePoint getEntrance() {
        return entrance;
    }

    public void setEntrance(ServicePoint entrance) {
        this.entrance = entrance;
    }

    public ServicePoint getTicketsales() {
        return ticketsales;
    }

    public void setTicketsales(ServicePoint ticketsales) {
        this.ticketsales = ticketsales;
    }

    public ServicePoint getTicketcheck() {
        return ticketcheck;
    }

    public void setTicketcheck(ServicePoint ticketcheck) {
        this.ticketcheck = ticketcheck;
    }

    public ServicePoint getMetro() {
        return metro;
    }

    public void setMetro(ServicePoint metro) {
        this.metro = metro;
    }

    public Station getAsema() {
        return asema;
    }

    public void setAsema(Station asema) {
        this.asema = asema;
    }
}
