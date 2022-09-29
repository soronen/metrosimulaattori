package application.controller;

import application.simu.framework.IMoottori;
import application.simu.model.Palvelupiste;

public interface IKontrolleri {

    // Rajapinta, joka tarjotaan  käyttöliittymälle:

    public void kaynnistaSimulointi();
    public void nopeuta();
    public void hidasta();

    // Rajapinta, joka tarjotaan moottorille:
    public void naytaLoppuaika(double aika);
    public void visualisoiAsiakas();

    public void paivitaUI();


    /* ehkä jonain päivänä
    public IMoottori getMoottori();
    public void asetaMoottorinParametrit();
    public Palvelupiste[] getPalvelupisteet();
    */
}
