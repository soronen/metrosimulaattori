package application.controller;

import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.view.ISimulaattorinUI;

public class Kontrolleri implements IKontrolleri {

    private IMoottori moottori;
    private Palvelupiste[] palvelupisteet;
    private StatsTabController ui;

    public Kontrolleri(StatsTabController ui) {
        this.ui = ui;
    }
    public Kontrolleri() {

    }


    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        moottori = getMoottori();
        //moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
        ((Thread)moottori).start();

        palvelupisteet = moottori.getPalvelupisteet();
    }


    //@Override
    public void asetaMoottorinParametrit() {

    }


    public void resetSimulator() {
        moottori.setSimulointiaika(0);
        Kello.getInstance().setAika(0);
        moottori = new OmaMoottori(this);
    }


    public IMoottori getMoottori() {
        if (moottori == null) {
            moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
        }
        return moottori;
    }

    //@Override
    public Palvelupiste[] getPalvelupisteet() {
        return palvelupisteet;
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
    public void naytaLoppuaika(double aika) {

    }

    @Override
    public void visualisoiAsiakas() {

    }

    @Override
    public void paivitaUI() {
        ui.paivitaUI();
    }
}
