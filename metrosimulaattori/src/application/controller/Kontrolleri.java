package application.controller;

import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.view.StatsTabController;

public class Kontrolleri implements IKontrolleri {

    private IMoottori moottori;
    private StatsTabController ui;

    private int simukesto = 1000;
    private int simuviive = 100;
    private int metronKapasiteetti = 40;
    private int asemanKapasiteetti = 200;

    boolean kaynnissa = false;



    private int entranceMean = 4;
    private int	entranceVariance = 8;
    private int salesMean = 20;
    private int salesVariance = 10;
    private int checkMean = 7;
    private int checkVariance = 3;
    private int metroMean = 360;
    private int metroVariance = 60;
    private int arrivalMean = 5;
    private int arrivalVariance = 3;

    public Kontrolleri(StatsTabController ui) {
        this.ui = ui;

    }
    public Kontrolleri() {

    }


    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        getMoottori();

        asetaMoottorinParametrit();

        if (!kaynnissa) {
            kaynnissa = true;
            ((Thread)moottori).start();
        }
    }

    @Override
    public void asetaMoottorinParametrit() {
        setMetronKapasiteetti(metronKapasiteetti);
        setAsemanKapasiteetti(asemanKapasiteetti);

        setEntranceJakauma(entranceMean, entranceVariance);
        setSalesJakauma(arrivalMean, arrivalVariance);
        setCheckJakauma(arrivalMean, arrivalVariance);
        setMetroJakauma(arrivalMean, arrivalVariance);


    }

    @Override
    public void resetSimulator() {
        moottori.setSimulointiaika(0);
        moottori = null;
    }

    @Override
    public IMoottori getMoottori() {
        if (moottori == null) {
            moottori = new OmaMoottori(this, arrivalMean, arrivalVariance); // luodaan uusi moottorisäie jokaista simulointia varten
        }
        return moottori;
    }

    @Override
    public Palvelupiste[] getPalvelupisteet() {
        return moottori.getPalvelupisteet();
    }

    @Override
    public void nopeuta() {
        moottori.setViive((long)(moottori.getViive()*0.9));
    }

    @Override
    public void hidasta() {
        moottori.setViive((long)(moottori.getViive()*1.10));
    }

    @Override
    public void muutaNopeutta(long viive) {
        moottori.setViive(viive);
    }

    @Override
    public void naytaLoppuaika(double aika) {

    }
    @Override
    public void visualisoiAsiakas() {

    }
    @Override
    public void paivitaUI() {
        ui.paivitaUI();
    }

    @Override
    public void setAsemanKapasiteetti(int asemanKapasiteetti) {
        this.asemanKapasiteetti = asemanKapasiteetti;
        moottori.setStationCapacity(asemanKapasiteetti);
    }
    @Override
    public void setMetronKapasiteetti(int metronKapasiteetti) {
        this.metronKapasiteetti = metronKapasiteetti;
        moottori.setMetroCapacity(metronKapasiteetti);
    }
    @Override
    public void setsimulaattorinKesto(int simukesto) {
        this.simukesto = simukesto;
        moottori.setSimulointiaika(simukesto);
    }
    @Override
    public void setSimulaattorinViive(int simuviive) {
        this.simuviive = simuviive;
        moottori.setViive(simuviive);

    }
    @Override
    public int getMetronKapasiteetti() {
        return moottori.getMetroCapacity();
    }

    @Override
    public int getAsemanKapasiteetti() {
        return moottori.getStationCapacity();
    }

    @Override
    public int getAsiakkaatAsemassa() {
        return moottori.getCustomersWithin();
    }
    @Override
    public int getPalvellutAsaiakkaat() {
        return moottori.getServedCustomers();
    }

    @Override
    public long getViive() {
        return moottori.getViive();
    }
    @Override
    public void setKaynnissa(boolean kaynnissa) {
        this.kaynnissa = kaynnissa;
    }
    @Override
    public int getMobiililippujakauma() {
        return moottori.getMobiililippujakauma();
    }

    @Override
    public void setMobiililippujakauma(int mobiililippujakauma) {
        moottori.setMobiililippujakauma(mobiililippujakauma);
    }


    public void setArrivalJakauma (int mean, int variance) {
        arrivalMean = mean;
        arrivalVariance = variance;
    }
    public void setEntranceJakauma (int mean, int variance) {
        moottori.setEntranceJakauma(mean, variance);
    }
    public void setSalesJakauma (int mean, int variance) {
        moottori.setSalesJakauma(mean, variance);
    }
    public void setCheckJakauma (int mean, int variance) {
        moottori.setCheckJakauma(mean, variance);
    }
    public void setMetroJakauma (int mean, int variance) {
        moottori.setMetroJakauma(mean, variance);
    }


    public int getEntranceMean() {
        return entranceMean;
    }

    public int getEntranceVariance() {
        return entranceVariance;
    }

    public int getSalesMean() {
        return salesMean;
    }

    public int getSalesVariance() {
        return salesVariance;
    }

    public int getCheckMean() {
        return checkMean;
    }

    public int getCheckVariance() {
        return checkVariance;
    }

    public int getMetroMean() {
        return metroMean;
    }

    public int getMetroVariance() {
        return metroVariance;
    }

    public int getArrivalMean() {
        return arrivalMean;
    }

    public int getArrivalVariance() {
        return arrivalVariance;
    }
}
