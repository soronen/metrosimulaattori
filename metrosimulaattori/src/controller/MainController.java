package controller;

import controller.Kontrolleri;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;
import view.ISimulaattorinUI;

import static java.lang.String.valueOf;
import static simu.model.TapahtumanTyyppi.METRO;

public class MainController implements IKontrolleri {

    @FXML
    private Label ppTila;
    @FXML
    private Label ppKasittelyAika;
    @FXML
    private Label ppKasitellyAsiakkaat;
    @FXML
    private Label ppJononPituus;
    @FXML
    private Label ppJononKKesto;
    @FXML
    private Button bMetroasema;
    @FXML
    private Button bSaapuminen;
    @FXML
    private Button bLipunmyynti;
    @FXML
    private Button bLipuntarkastus;
    @FXML
    private Button bMetro;


    @FXML
    private void initialize() {
        ppTila.setText("I'm a Label.");
    }

    public void setMainApp(ISimulaattorinUI ui) {
        this.ui = ui;
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        ppTila.setText("im not a label!");

        this.kaynnistaSimulointi();
    }


    private IMoottori moottori;
    private ISimulaattorinUI ui;


    //private String[] ppnimet = { "ENTRANCE" , "TICKETSALES" , "CHECK" , "METRO" };


    private Palvelupiste[] palvelupisteet;


    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten
        moottori.setSimulointiaika(ui.getAika());
        moottori.setViive(ui.getViive());

        //ui.getVisualisointi().tyhjennaNaytto();
        this.visualisoiAsiakas();

        ((Thread)moottori).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?

        moottori.getPalvelupisteet();


    }

    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*1.10));
    }

    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*0.9));
    }



    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    @Override
    public void naytaLoppuaika(double aika) {
        Platform.runLater(()->ui.setLoppuaika(aika));
    }


    @Override
    public void visualisoiAsiakas() {
        Platform.runLater(new Runnable(){
            public void run(){
                int i = getJonottajienlkm(METRO);
                String s = valueOf(i);

                ppJononPituus.setText(s);
            }
        });
    }


    // palvelupisteiden getterit
    public int getJonottajienlkm(TapahtumanTyyppi tt) {
        switch (tt) {
            case ENTRANCE:
                return palvelupisteet[0].getJonopituus();
            case TICKETSALES:
                return palvelupisteet[1].getJonopituus();
            case TICKETCHECK:
                return palvelupisteet[2].getJonopituus();
            case METRO:
                return palvelupisteet[3].getJonopituus();
        }
        return 0;
    }
    public double getJononkeskipituus(TapahtumanTyyppi tt) {
        switch (tt) {
            case ENTRANCE:
                return palvelupisteet[0].getKeskijonoaika();
            case TICKETSALES:
                return palvelupisteet[1].getKeskijonoaika();
            case TICKETCHECK:
                return palvelupisteet[2].getKeskijonoaika();
            case METRO:
                return palvelupisteet[3].getKeskijonoaika();
        }
        return 0;
    }

    // aseman (moottorin) getterit
    public double getLapimenoaika() {
        return moottori.getLapimenoaika();
    }
    public int getMetroCapacity() {
        return moottori.getMetroCapacity();
    }
    public int getStationCapacity() {
        return moottori.getStationCapacity();
    }
    public int getCustomersWithin() {
        return moottori.getCustomersWithin();
    }


}