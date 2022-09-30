package application.controller;

import application.simu.framework.IMoottori;
import application.simu.model.Palvelupiste;

public interface IKontrolleri {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();

    void asetaMoottorinParametrit();

    void resetSimulator();

    IMoottori getMoottori();

    Palvelupiste[] getPalvelupisteet();

    public void nopeuta();
    public void hidasta();

    void muutaNopeutta(long viive);

    // Rajapinta, joka tarjotaan moottorille:
    public void naytaLoppuaika(double aika);
    public void visualisoiAsiakas();

    public void paivitaUI();

    void setMetronKapasiteetti(int metronKapasiteetti);

    void setAsemanKapasiteetti(int simukesto);

    void setsimulaattorinKesto(int simukesto);

    void setSimulaattorinViive(int simuviive);

    int getMetronKapasiteetti();

    int getAsemanKapasiteetti();

    int getAsiakkaatAsemassa();

    int getPalvellutAsaiakkaat();

    long getViive();

    void setKaynnissa(boolean kaynnissa);

    int getMobiililippujakauma();

    void setMobiililippujakauma(int mobiililippujakauma);


    void setCheckJakauma(int mean, int variance);

    void setMetroJakauma(int mean, int variance);

    void setEntranceJakauma(int mean, int variance);

    void setSalesJakauma(int mean, int variance);
}
