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

    public Kontrolleri(StatsTabController ui) {
        this.ui = ui;
        moottori = getMoottori();
        moottori.setViive(100);
    }
    public Kontrolleri() {

    }


    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        moottori = getMoottori();
        setMetronKapasiteetti(metronKapasiteetti);
        setAsemanKapasiteetti(asemanKapasiteetti);
        if (!kaynnissa) {
            kaynnissa = true;
            ((Thread)moottori).start();
        }
    }

    @Override
    public void asetaMoottorinParametrit() {

    }

    @Override
    public void resetSimulator() {
        moottori.setSimulointiaika(0);
        Kello.getInstance().setAika(0);
        moottori = new OmaMoottori(this);
        setMetronKapasiteetti(metronKapasiteetti);
        setAsemanKapasiteetti(asemanKapasiteetti);
    }

    @Override
    public IMoottori getMoottori() {
        if (moottori == null) {
            moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
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

}
