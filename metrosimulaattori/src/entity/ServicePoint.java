package entity;

import application.simu.model.TapahtumanTyyppi;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ServicePoint {

    private TapahtumanTyyppi palvelupiste;

    private int palvellutAsiakkaat;
    private int jonossaOlevatAsiakkaat;
    private double palvelunKeskiaika;
    private double jononKeskikesto;

    private int jononOdotusarvo;
    private int jononVarianssi;

    private int metronKapasiteetti;
    private Long id;

    public ServicePoint() {};

    public void setId(Long id) {
        this.id = id;
    }


    @Id
    public Long getId() {
        return id;
    }
}
