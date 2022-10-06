package entity;

import application.simu.model.TapahtumanTyyppi;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ServicePoint {

    private TapahtumanTyyppi palvelupiste;

    private int palvellutAsiakkaat;
    private int jonossaOlevatAsiakkaat;
    private double palvelunKeskiaika;
    private double jononKeskikesto;

    private int palvelupisteenOdotusarvo;
    private int palvelupisteenVarianssi;

    private int metronKapasiteetti;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

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

    public ServicePoint() {};


    public int getId() {
        return id;
    }

    public TapahtumanTyyppi getPalvelupiste() {
        return palvelupiste;
    }

    public void setPalvelupiste(TapahtumanTyyppi palvelupiste) {
        this.palvelupiste = palvelupiste;
    }

    public int getPalvellutAsiakkaat() {
        return palvellutAsiakkaat;
    }

    public void setPalvellutAsiakkaat(int palvellutAsiakkaat) {
        this.palvellutAsiakkaat = palvellutAsiakkaat;
    }

    public int getJonossaOlevatAsiakkaat() {
        return jonossaOlevatAsiakkaat;
    }

    public void setJonossaOlevatAsiakkaat(int jonossaOlevatAsiakkaat) {
        this.jonossaOlevatAsiakkaat = jonossaOlevatAsiakkaat;
    }

    public double getPalvelunKeskiaika() {
        return palvelunKeskiaika;
    }

    public void setPalvelunKeskiaika(double palvelunKeskiaika) {
        this.palvelunKeskiaika = palvelunKeskiaika;
    }

    public double getJononKeskikesto() {
        return jononKeskikesto;
    }

    public void setJononKeskikesto(double jononKeskikesto) {
        this.jononKeskikesto = jononKeskikesto;
    }

    public int getPalvelupisteenOdotusarvo() {
        return palvelupisteenOdotusarvo;
    }

    public void setPalvelupisteenOdotusarvo(int jononOdotusarvo) {
        this.palvelupisteenOdotusarvo = jononOdotusarvo;
    }

    public int getPalvelupisteenVarianssi() {
        return palvelupisteenVarianssi;
    }

    public void setPalvelupisteenVarianssi(int jononVarianssi) {
        this.palvelupisteenVarianssi = jononVarianssi;
    }

    public int getMetronKapasiteetti() {
        return metronKapasiteetti;
    }

    public void setMetronKapasiteetti(int metronKapasiteetti) {
        this.metronKapasiteetti = metronKapasiteetti;
    }
}
