package application.controller;

import application.simu.framework.IMoottori;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import application.view.IVisualisointi;
import application.view.graphviewcontroller;

/**
 * Kontrolleri-luokan rajapinta. kaikki kontrolleri-luokan kutsut tehdään luomalla IKontrolleri-olio.
 * Melko turha kun meillä on vain 1 kontrolleri luokka..
 *
 * @author Eetu Soronen, Emil Ålgars
 * @version 1
 */
public interface IKontrolleri {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();

    void asetaMoottorinParametrit();

    void resetSimulator();

    IMoottori getMoottori();

    Palvelupiste[] getPalvelupisteet();

    public void nopeuta();

    /**
     * katso toteutus {@link Kontrolleri#hidasta()}
     */
    public void hidasta();


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

    void stopSimulation();

    void dChart(graphviewcontroller i);
}
