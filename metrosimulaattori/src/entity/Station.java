package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Station {

    private int asemanKapasiteetti;
    private int esiostettujenLippujenSuhde;
    private int asiakkaidenSaapumisenOdotusarvo;
    private int asiakkaidenSaapumisenVarianssi;

    private int asemassaOlevatAsiakkaat;
    private int asemastaPoistuneetAsiakkaat;
    private Long id;

    public Station() {};


    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
