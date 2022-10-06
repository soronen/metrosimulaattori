package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {

    private int esiostettujenLippujenSuhde;
    private int asiakkaidenSaapumisenOdotusarvo;
    private int asiakkaidenSaapumisenVarianssi;

    private int asemassaOlevatAsiakkaat;
    private int asemastaPoistuneetAsiakkaat;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    public Station(int esiostettujenLippujenSuhde, int asiakkaidenSaapumisenOdotusarvo, int asiakkaidenSaapumisenVarianssi, int asemassaOlevatAsiakkaat, int asemastaPoistuneetAsiakkaat, int asemanKapasiteetti) {
        this.esiostettujenLippujenSuhde = esiostettujenLippujenSuhde;
        this.asiakkaidenSaapumisenOdotusarvo = asiakkaidenSaapumisenOdotusarvo;
        this.asiakkaidenSaapumisenVarianssi = asiakkaidenSaapumisenVarianssi;
        this.asemassaOlevatAsiakkaat = asemassaOlevatAsiakkaat;
        this.asemastaPoistuneetAsiakkaat = asemastaPoistuneetAsiakkaat;
        this.asemanKapasiteetti = asemanKapasiteetti;
    }

    public Station() {};

    private int asemanKapasiteetti;

    public int getAsemanKapasiteetti() {
        return asemanKapasiteetti;
    }

    public void setAsemanKapasiteetti(int asemanKapasiteetti) {
        this.asemanKapasiteetti = asemanKapasiteetti;
    }

    public int getEsiostettujenLippujenSuhde() {
        return esiostettujenLippujenSuhde;
    }

    public void setEsiostettujenLippujenSuhde(int esiostettujenLippujenSuhde) {
        this.esiostettujenLippujenSuhde = esiostettujenLippujenSuhde;
    }

    public int getAsiakkaidenSaapumisenOdotusarvo() {
        return asiakkaidenSaapumisenOdotusarvo;
    }

    public void setAsiakkaidenSaapumisenOdotusarvo(int asiakkaidenSaapumisenOdotusarvo) {
        this.asiakkaidenSaapumisenOdotusarvo = asiakkaidenSaapumisenOdotusarvo;
    }

    public int getAsiakkaidenSaapumisenVarianssi() {
        return asiakkaidenSaapumisenVarianssi;
    }

    public void setAsiakkaidenSaapumisenVarianssi(int asiakkaidenSaapumisenVarianssi) {
        this.asiakkaidenSaapumisenVarianssi = asiakkaidenSaapumisenVarianssi;
    }

    public int getAsemassaOlevatAsiakkaat() {
        return asemassaOlevatAsiakkaat;
    }

    public void setAsemassaOlevatAsiakkaat(int asemassaOlevatAsiakkaat) {
        this.asemassaOlevatAsiakkaat = asemassaOlevatAsiakkaat;
    }

    public int getAsemastaPoistuneetAsiakkaat() {
        return asemastaPoistuneetAsiakkaat;
    }

    public void setAsemastaPoistuneetAsiakkaat(int asemastaPoistuneetAsiakkaat) {
        this.asemastaPoistuneetAsiakkaat = asemastaPoistuneetAsiakkaat;
    }

    public int getId() {
        return id;
    }

}
