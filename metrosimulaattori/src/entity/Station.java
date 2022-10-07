package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Luo olion joka pitää sisällään metroaseman yleiset muuttujat,
 * jotka eivät ole riippuvaisia palvelupisteistä.
 *
 * @author Eetu Soronen
 * @version 1
 */
@Entity
public class Station {

    /**
     * prosenttiluku (0-100), joka määrää kuinka monta % asiakkaista
     * hyppää lipunmyynnin yli suoraan lipuntarakastukseen.
     */
    private int esiostettujenLippujenSuhde;
    /**
     * saapuvat asiakkaat luodaan tiettyin väliajoin normaalijakauman avulla, jolla on odotusarvo ja varianssi.
     */
    private int asiakkaidenSaapumisenOdotusarvo;
    /**
     * saapuvat asiakkaat luodaan tiettyin väliajoin normaalijakauman avulla, jolla on odotusarvo ja varianssi.
     */
    private int asiakkaidenSaapumisenVarianssi;
    /**
     * Asemassa olevat asiakkaat tällä hetkellä.
     */
    private int asemassaOlevatAsiakkaat;
    /**
     * Simulaattorin elinkaaren aikanana asemasta poistuneet asiakkaat.
     */
    private int asemastaPoistuneetAsiakkaat;
    /**
     * Aseman kapasiteetti, jos asemassa on tätä luku enemmän asiakkaita,
     * ei Entrance-palvelupiste päästä lisää läpi.
     */
    private int asemanKapasiteetti;
    /**
     * tietokannan primary key, jonka luomisesta huolehtii tietokanta (mariadb).
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    /**
     * Olion konstruktori, joka asettaa sen kaikille muuttujille arvot.
     * @param esiostettujenLippujenSuhde - katso  {@link #esiostettujenLippujenSuhde}
     * @param asiakkaidenSaapumisenOdotusarvo - katso {@link #asiakkaidenSaapumisenOdotusarvo}
     * @param asiakkaidenSaapumisenVarianssi - katso {@link #asiakkaidenSaapumisenVarianssi}
     * @param asemassaOlevatAsiakkaat - katso {@link #asemassaOlevatAsiakkaat}
     * @param asemastaPoistuneetAsiakkaat - katso {@link #asemastaPoistuneetAsiakkaat}
     * @param asemanKapasiteetti - katso {@link #asemanKapasiteetti}
     */
    public Station(int esiostettujenLippujenSuhde, int asiakkaidenSaapumisenOdotusarvo, int asiakkaidenSaapumisenVarianssi, int asemassaOlevatAsiakkaat, int asemastaPoistuneetAsiakkaat, int asemanKapasiteetti) {
        this.esiostettujenLippujenSuhde = esiostettujenLippujenSuhde;
        this.asiakkaidenSaapumisenOdotusarvo = asiakkaidenSaapumisenOdotusarvo;
        this.asiakkaidenSaapumisenVarianssi = asiakkaidenSaapumisenVarianssi;
        this.asemassaOlevatAsiakkaat = asemassaOlevatAsiakkaat;
        this.asemastaPoistuneetAsiakkaat = asemastaPoistuneetAsiakkaat;
        this.asemanKapasiteetti = asemanKapasiteetti;
    };

    /**
     * hibernaten vaatima tyhjä konstruktori
     */
    public Station() {}

    /**
     * getteri
     * @return {@link #asemanKapasiteetti}
     */
    public int getAsemanKapasiteetti() {
        return asemanKapasiteetti;
    }

    /**
     * setteri
     * @param asemanKapasiteetti - {@link #asemanKapasiteetti}
     */
    public void setAsemanKapasiteetti(int asemanKapasiteetti) {
        this.asemanKapasiteetti = asemanKapasiteetti;
    }

    /**
     * getteri
     * @return {@link #esiostettujenLippujenSuhde}
     */
    public int getEsiostettujenLippujenSuhde() {
        return esiostettujenLippujenSuhde;
    }

    /**
     * setteri
     * @param esiostettujenLippujenSuhde {@link #esiostettujenLippujenSuhde}
     */
    public void setEsiostettujenLippujenSuhde(int esiostettujenLippujenSuhde) {
        this.esiostettujenLippujenSuhde = esiostettujenLippujenSuhde;
    }

    /**
     * getteri
     * @return {@link #asiakkaidenSaapumisenOdotusarvo}
     */
    public int getAsiakkaidenSaapumisenOdotusarvo() {
        return asiakkaidenSaapumisenOdotusarvo;
    }

    /**
     * setteri
     * @param asiakkaidenSaapumisenOdotusarvo {@link #asiakkaidenSaapumisenOdotusarvo}
     */
    public void setAsiakkaidenSaapumisenOdotusarvo(int asiakkaidenSaapumisenOdotusarvo) {
        this.asiakkaidenSaapumisenOdotusarvo = asiakkaidenSaapumisenOdotusarvo;
    }


    /**
     * getteri
     * @return {@link #asiakkaidenSaapumisenVarianssi}
     */
    public int getAsiakkaidenSaapumisenVarianssi() {
        return asiakkaidenSaapumisenVarianssi;
    }

    /**
     * setteri
     * @param asiakkaidenSaapumisenVarianssi {@link #asiakkaidenSaapumisenVarianssi}
     */
    public void setAsiakkaidenSaapumisenVarianssi(int asiakkaidenSaapumisenVarianssi) {
        this.asiakkaidenSaapumisenVarianssi = asiakkaidenSaapumisenVarianssi;
    }

    /**
     * tyrannosaurus rex🦖
     * @return {@link #asemassaOlevatAsiakkaat}
     */
    public int getAsemassaOlevatAsiakkaat() {
        return asemassaOlevatAsiakkaat;
    }

    /**
     * setteri
     * @param asemassaOlevatAsiakkaat {@link #asemassaOlevatAsiakkaat}
     */
    public void setAsemassaOlevatAsiakkaat(int asemassaOlevatAsiakkaat) {
        this.asemassaOlevatAsiakkaat = asemassaOlevatAsiakkaat;
    }

    /**
     * getteri
     * @return {@link #asemastaPoistuneetAsiakkaat}
     */
    public int getAsemastaPoistuneetAsiakkaat() {
        return asemastaPoistuneetAsiakkaat;
    }

    /**
     * setteri
     * @param asemastaPoistuneetAsiakkaat {@link #asemastaPoistuneetAsiakkaat}
     */
    public void setAsemastaPoistuneetAsiakkaat(int asemastaPoistuneetAsiakkaat) {
        this.asemastaPoistuneetAsiakkaat = asemastaPoistuneetAsiakkaat;
    }

    /**
     * getteri
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

}
