package entity;

import jakarta.persistence.*;


/**
 * Luo olion joka pitää sisällään koko simulaattorin, palvelupisteineen ja asemineen.
 * Tämä olio taas tallennetaan tietokantaan.
 *
 * @author Eetu Soronen
 * @version 1
 */
@Entity
public class Simulaattori {

    /**
     * aika, joka asetettiin simun kestoksi (todellinen ajoaika voi vaihdella hieman ja on double-arov)
     */
    private int simunKesto;

    /**
     * saapumista vastaava palvelupiste - katso {@link ServicePoint ServicePoint.class}
     */
    @OneToOne(cascade = CascadeType.ALL)
    private ServicePoint entrance;

    /**
     * lipunmyynnistä vastaava palvelupiste - katso {@link ServicePoint ServicePoint.class}
     */
    @OneToOne(cascade = CascadeType.ALL)
    private ServicePoint ticketsales;

    /**
     * lipuntarkastuksesta vastaava palvelupiste - katso {@link ServicePoint ServicePoint.class}
     */
    @OneToOne(cascade = CascadeType.ALL)
    private ServicePoint ticketcheck;

    /**
     * metrosta vastaava palvelupiste - katso {@link ServicePoint ServicePoint.class}
     */
    @OneToOne(cascade = CascadeType.ALL)
    private ServicePoint metro;

    /**
     * Metroasema - katso {@link Station Station.class}
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Station asema;

    /**
     * Simulaattori-taulukon primary key, jonka luomisesta huolehtii tietokanta (mariadb).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Luokan konstruktori, joka asettaa kaikkien muuttujien arvot.
     *
     * @param simunKesto  - katso {@link #simunKesto}
     * @param entrance    - katso {@link #entrance}
     * @param ticketsales - katso {@link #ticketsales}
     * @param ticketcheck - katso {@link #ticketcheck}
     * @param metro       - katso {@link #metro}
     * @param asema       - katso {@link #asema}
     */
    public Simulaattori(int simunKesto, ServicePoint entrance, ServicePoint ticketsales, ServicePoint ticketcheck, ServicePoint metro, Station asema) {
        this.simunKesto = simunKesto;
        this.entrance = entrance;
        this.ticketsales = ticketsales;
        this.ticketcheck = ticketcheck;
        this.metro = metro;
        this.asema = asema;
    }

    /**
     * hibernaten vaatima tyhjä konstruktori.
     */
    public Simulaattori() {
    }


    /**
     * getteri
     *
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * getteri
     *
     * @return {@link #simunKesto}
     */
    public int getSimunKesto() {
        return simunKesto;
    }

    /**
     * setteri
     *
     * @param simunKesto {@link #simunKesto}
     */
    public void setSimunKesto(int simunKesto) {
        this.simunKesto = simunKesto;
    }

    /**
     * getteri
     *
     * @return {@link #entrance}
     */
    public ServicePoint getEntrance() {
        return entrance;
    }

    /**
     * setteri
     *
     * @param entrance {@link #entrance}
     */
    public void setEntrance(ServicePoint entrance) {
        this.entrance = entrance;
    }

    /**
     * getteri
     *
     * @return {@link #ticketsales}
     */
    public ServicePoint getTicketsales() {
        return ticketsales;
    }

    /**
     * setteri
     *
     * @param ticketsales {@link #ticketsales}
     */
    public void setTicketsales(ServicePoint ticketsales) {
        this.ticketsales = ticketsales;
    }

    /**
     * getteri
     *
     * @return {@link #ticketcheck}
     */
    public ServicePoint getTicketcheck() {
        return ticketcheck;
    }

    /**
     * setteri
     *
     * @param ticketcheck {@link #ticketcheck}
     */
    public void setTicketcheck(ServicePoint ticketcheck) {
        this.ticketcheck = ticketcheck;
    }

    /**
     * getteri
     *
     * @return {@link #metro}
     */
    public ServicePoint getMetro() {
        return metro;
    }

    /**
     * setteri
     *
     * @param metro {@link #metro}
     */
    public void setMetro(ServicePoint metro) {
        this.metro = metro;
    }

    /**
     * getteri
     * @return {@link #asema}
     */
    public Station getAsema() {
        return asema;
    }

    /**
     * setteri
     * @param asema {@link #asema}
     */
    public void setAsema(Station asema) {
        this.asema = asema;
    }
}
