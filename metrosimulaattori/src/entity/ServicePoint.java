package entity;

import application.simu.model.TapahtumanTyyppi;
import jakarta.persistence.*;

/**
 * Luo olion palvelupisteen ylläpidetyistä arvoista, jonka hibernate tallentaa tietokantaan.
 *
 * @author Eetu Soronen
 * @version 1
 */
@Entity
public class ServicePoint {

    /**
     * Palvelupisteen tyyppi (saapuminen, myynti, tarkastus, metro).
     * Enum-arvo, joka luetaan string-muodossa
     */
    @Enumerated(EnumType.STRING)
    private TapahtumanTyyppi palvelupiste;

    /**
     * Palvelupisteen läpi menneet asiakkaat.
     */
    private int palvellutAsiakkaat;
    /**
     * Asiakkaat jotka ovat palvelupisteen jonossa
     */
    private int jonossaOlevatAsiakkaat;

    /**
     * Keskimääräinen aika, joka kuluu yhden asiakkaan käsittelyssä.
     */
    private double palvelunKeskiaika;
    /**
     * Keskimääräinen aika, jonka 1 asiakas viettää jonossa palvelupisteeseen.
     */
    private double jononKeskikesto;
    /**
     * Palvelupisteen käsittelyaika noudattaa normaalijakaumaa jolla on odotusarvo ja varianssi.
     */
    private int palvelupisteenOdotusarvo;
    /**
     * Palvelupisteen käsittelyaika noudattaa normaalijakaumaa jolla on odotusarvo ja varianssi.
     */
    private int palvelupisteenVarianssi;

    /**
     * Määrää, kuinka monta asiakasta mahtuu metro-tapahtumaan kerralla (eli voi poistua asemasta).
     */
    private int metronKapasiteetti;

    /**
     * tietokannan primary key, jonka luomisesta huolehtii tietokanta (mariadb).
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    /**
     *  Luo ServicePoint-olion ja asettaa sen kaikki muuttujat.
     *
     * @param palvelupiste katso muuttuja {@link #palvelupiste}
     * @param palvellutAsiakkaat katso muuttuja {@link #palvellutAsiakkaat}
     * @param jonossaOlevatAsiakkaat katso muuttuja {@link #jonossaOlevatAsiakkaat}
     * @param palvelunKeskiaika katso muuttuja {@link #palvelunKeskiaika}
     * @param jononKeskikesto katso muuttuja {@link #jononKeskikesto}
     * @param palvelupisteenOdotusarvo katso muuttuja {@link #palvelupisteenOdotusarvo}
     * @param palvelupisteenVarianssi katso muuttuja {@link #palvelupisteenVarianssi}
     * @param metronKapasiteetti katso muuttuja {@link #metronKapasiteetti}
     */
    public ServicePoint(TapahtumanTyyppi palvelupiste, int palvellutAsiakkaat, int jonossaOlevatAsiakkaat,
                        double palvelunKeskiaika, double jononKeskikesto, int palvelupisteenOdotusarvo,
                        int palvelupisteenVarianssi, int metronKapasiteetti) {

        this.palvelupiste = palvelupiste;
        this.palvellutAsiakkaat = palvellutAsiakkaat;
        this.jonossaOlevatAsiakkaat = jonossaOlevatAsiakkaat;
        this.palvelunKeskiaika = palvelunKeskiaika;
        this.jononKeskikesto = jononKeskikesto;
        this.palvelupisteenOdotusarvo = palvelupisteenOdotusarvo;
        this.palvelupisteenVarianssi = palvelupisteenVarianssi;
        this.metronKapasiteetti = metronKapasiteetti;
    }

    /**
     * hibernaten/jakartan vaatima tyhjä konstruktori.
     */
    public ServicePoint() {};


    /**
     * getteri
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * getteri
     * @return {@link #palvelupiste}
     */
    public TapahtumanTyyppi getPalvelupiste() {
        return palvelupiste;
    }

    /**
     * setteri
     * @param palvelupiste {@link #palvelupiste}
     */
    public void setPalvelupiste(TapahtumanTyyppi palvelupiste) {
        this.palvelupiste = palvelupiste;
    }

    /**
     * getteri
     * @return {link #palvellutAsiakkaat}
     */
    public int getPalvellutAsiakkaat() {
        return palvellutAsiakkaat;
    }

    /**
     * setteri
     * @param palvellutAsiakkaat {@link #palvellutAsiakkaat}
     */
    public void setPalvellutAsiakkaat(int palvellutAsiakkaat) {
        this.palvellutAsiakkaat = palvellutAsiakkaat;
    }

    /**
     * getteri
     * @return {@link #jonossaOlevatAsiakkaat}
     */
    public int getJonossaOlevatAsiakkaat() {
        return jonossaOlevatAsiakkaat;
    }

    /**
     * setteri
     * @param jonossaOlevatAsiakkaat {@link #jonossaOlevatAsiakkaat}
     */
    public void setJonossaOlevatAsiakkaat(int jonossaOlevatAsiakkaat) {
        this.jonossaOlevatAsiakkaat = jonossaOlevatAsiakkaat;
    }


    /**
     * getteri
     * @return {@link #palvelunKeskiaika}
     */
    public double getPalvelunKeskiaika() {
        return palvelunKeskiaika;
    }

    /**
     * setteri
     * @param palvelunKeskiaika {@link #palvelunKeskiaika}
     */
    public void setPalvelunKeskiaika(double palvelunKeskiaika) {
        this.palvelunKeskiaika = palvelunKeskiaika;
    }

    /**
     * getteri
     * @return {@link #jononKeskikesto}
     */
    public double getJononKeskikesto() {
        return jononKeskikesto;
    }

    /**
     * setteri
     * @param jononKeskikesto {@link #jononKeskikesto}
     */
    public void setJononKeskikesto(double jononKeskikesto) {
        this.jononKeskikesto = jononKeskikesto;
    }

    /**
     * getteri
     * @return {@link #palvelupisteenOdotusarvo}
     */
    public int getPalvelupisteenOdotusarvo() {
        return palvelupisteenOdotusarvo;
    }

    /**
     * setteri
     * @param palvelupisteenOdotusarvo {@link #palvelupisteenOdotusarvo}
     */
    public void setPalvelupisteenOdotusarvo(int palvelupisteenOdotusarvo) {
        this.palvelupisteenOdotusarvo = palvelupisteenOdotusarvo;
    }

    /**
     * getteri
     * @return {@link #palvelupisteenVarianssi}
     */
    public int getPalvelupisteenVarianssi() {
        return palvelupisteenVarianssi;
    }

    /**
     * setteri
     * @param palvelupisteenVarianssi {@link #palvelupisteenVarianssi}
     */
    public void setPalvelupisteenVarianssi(int palvelupisteenVarianssi) {
        this.palvelupisteenVarianssi = palvelupisteenVarianssi;
    }

    /**
     * getteri
     * @return {@link #metronKapasiteetti}
     */
    public int getMetronKapasiteetti() {
        return metronKapasiteetti;
    }

    /**
     * setteri
     * @param metronKapasiteetti {@link #metronKapasiteetti}
     */
    public void setMetronKapasiteetti(int metronKapasiteetti) {
        this.metronKapasiteetti = metronKapasiteetti;
    }
}
