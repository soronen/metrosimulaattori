package application.controller;

import application.simu.framework.IMoottori;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import application.view.IVisualisointi;
import application.view.graphviewcontroller;


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

    public void paivitaUI(Tapahtuma t);

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
    public boolean onkoKaynnissa();

    int getMobiililippujakauma();

    void setMobiililippujakauma(int mobiililippujakauma);


    void setCheckJakauma(int mean, int variance);

    void setMetroJakauma(int mean, int variance);

    void setUi(IVisualisointi iv);

    void setEntranceJakauma(int mean, int variance);

    void setSalesJakauma(int mean, int variance);

    int getPPjononpituus(TapahtumanTyyppi palvelupiste);

    double getPPkeskiarvoaika(TapahtumanTyyppi palvelupiste);

    int getPPpalvellutAsiakkaat(TapahtumanTyyppi palvelupiste);

    int[] getPPJakauma(TapahtumanTyyppi tt);

    double getPPkeskijonoaika(TapahtumanTyyppi palvelupiste);

    boolean onkoPPVarattu(TapahtumanTyyppi palvelupiste);

    void setPPJakauma(TapahtumanTyyppi painettuNappi, int mean, int variance);

    void setArrivalJakauma(int arrmean, int arrvar);

    void tallenaEntity(OmaMoottori mtr);

    void asetachart(graphviewcontroller i, int x);

    void initchart(graphviewcontroller i);
}
