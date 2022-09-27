package application.controller;

import application.MainApp;
import application.controller.IKontrolleri;
import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static application.simu.model.TapahtumanTyyppi.*;

public class StatsTabController implements IKontrolleri {
    @FXML
    private Label labelMetroKapasiteetti;
    @FXML
    private Label labelSimuloinninTila;
    @FXML
    private Label labelAsemassaOlevatAsiakkaat;
    @FXML
    private Label labelMetroPoistuneetAsiakkaat;
    @FXML
    private Label labelAika;
    @FXML
    private Label labelPavelunTila;
    @FXML
    private Label labelPavellutAsiakkaat;
    @FXML
    private Label labelPavelunKeskipituus;
    @FXML
    private Label labelJonossaOlevatAsiakkaat;
    @FXML
    private Label labelJononKeskipituus;
    @FXML
    private TextField tfSimuloinninKesto;
    @FXML
    private TextField tfSimuloinninViive;
    @FXML
    private Label labelPalvelupiste;
    @FXML
    private Label labelAsemanKapasiteetti;


    private IMoottori moottori;

    private Palvelupiste[] palvelupisteet;

    private TapahtumanTyyppi painettuNappi = TapahtumanTyyppi.ENTRANCE;

    // Moottorin ohjausta:
    @Override
    public void kaynnistaSimulointi() {
        moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista simulointia varten

        int simukesto = Integer.parseInt(tfSimuloinninKesto.getText());
        int simuviive = Integer.parseInt(tfSimuloinninViive.getText());

        moottori.setSimulointiaika(simukesto);
        moottori.setViive(simuviive);


        visualisoiAsiakas();
        asetaAsemanTiedot();

        ((Thread)moottori).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?

        palvelupisteet = moottori.getPalvelupisteet();


    }


    @Override
    public void hidasta() { // hidastetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*1.10));
        tfSimuloinninViive.setText(String.valueOf(moottori.getViive()));
    }

    @Override
    public void nopeuta() { // nopeutetaan moottorisäiettä
        moottori.setViive((long)(moottori.getViive()*0.9));
        tfSimuloinninViive.setText(String.valueOf(moottori.getViive()));
    }

    public void paivitaUI() {
        Platform.runLater(new Runnable() {
            public void run() {
                asetaAsemanTiedot();
                asetaPavelupisteenTiedot(getPainettuNappi());
            }
        });
    }

    private void asetaAsemanTiedot() {
        if (Kello.getInstance().getAika() < Integer.parseInt(tfSimuloinninKesto.getText())) {
            labelSimuloinninTila.setText("Käynnissä");
        } else {
            labelSimuloinninTila.setText("Ei käynnissä");
        }
        labelAika.setText(String.valueOf(Kello.getInstance().getAika()));
        labelMetroKapasiteetti.setText(String.valueOf(moottori.getStationCapacity()));
        labelAsemanKapasiteetti.setText(String.valueOf(moottori.getMetroCapacity()));
        labelAsemassaOlevatAsiakkaat.setText(String.valueOf(moottori.getCustomersWithin()));
        labelMetroPoistuneetAsiakkaat.setText(String.valueOf(moottori.getServedCustomers()));

    }

    private void asetaPavelupisteenTiedot(TapahtumanTyyppi palvelupiste) {
            switch (palvelupiste) {
                case ENTRANCE:
                    if (palvelupisteet[0].onVarattu()) {
                        labelPavelunTila.setText("Varattu");
                    } else {
                        labelPavelunTila.setText("Vapaa");
                    }
                    labelPalvelupiste.setText("Palvelupisteen \"Saapuminen\" tilastot");
                    labelJonossaOlevatAsiakkaat.setText(String.valueOf(palvelupisteet[0].getJonopituus()));
                    labelJononKeskipituus.setText(String.valueOf(palvelupisteet[0].getKeskijonoaika()));
                    labelPavellutAsiakkaat.setText(String.valueOf(palvelupisteet[0].getPalvelunro()));
                    labelPavelunKeskipituus.setText(String.valueOf(palvelupisteet[0].getKeskiarvoaika()));

                    break;
                case TICKETSALES:
                    labelPalvelupiste.setText("Palvelupisteen \"Lipunmyynti\" tilastot");
                    labelJonossaOlevatAsiakkaat.setText(String.valueOf(palvelupisteet[1].getJonopituus()));
                    labelJononKeskipituus.setText(String.valueOf(palvelupisteet[1].getKeskijonoaika()));
                    labelPavellutAsiakkaat.setText(String.valueOf(palvelupisteet[1].getPalvelunro()));
                    labelPavelunKeskipituus.setText(String.valueOf(palvelupisteet[1].getKeskiarvoaika()));

                    break;
                case TICKETCHECK:
                    labelPalvelupiste.setText("Palvelupisteen \"Lipuntarkastus\" tilastot");
                    labelJonossaOlevatAsiakkaat.setText(String.valueOf(palvelupisteet[2].getJonopituus()));
                    labelJononKeskipituus.setText(String.valueOf(palvelupisteet[2].getKeskijonoaika()));
                    labelPavellutAsiakkaat.setText(String.valueOf(palvelupisteet[2].getPalvelunro()));
                    labelPavelunKeskipituus.setText(String.valueOf(palvelupisteet[2].getKeskiarvoaika()));

                    break;
                case METRO:
                    labelPalvelupiste.setText("Palvelupisteen \"Metro\" tilastot");
                    labelJonossaOlevatAsiakkaat.setText(String.valueOf(palvelupisteet[3].getJonopituus()));
                    labelJononKeskipituus.setText(String.valueOf(palvelupisteet[3].getKeskijonoaika()));
                    labelPavellutAsiakkaat.setText(String.valueOf(palvelupisteet[3].getPalvelunro()));
                    labelPavelunKeskipituus.setText(String.valueOf(palvelupisteet[3].getKeskiarvoaika()));

                    break;
            }
    }

    private void setPainettuNappi(TapahtumanTyyppi tt) {
        painettuNappi = tt;
    }

    private TapahtumanTyyppi getPainettuNappi() {
        return painettuNappi;
    }

    @FXML
    private void valittuPalvelupiste(Event evt) {

        String napinID = ((Control)evt.getSource()).getId();
        switch (napinID) {
            case "bSaapuminen":
                asetaAsemanTiedot();
                asetaPavelupisteenTiedot(ENTRANCE);
                setPainettuNappi(ENTRANCE);
                break;
            case "bLipunmyynti":
                asetaAsemanTiedot();
                asetaPavelupisteenTiedot(TICKETSALES);
                setPainettuNappi(TICKETSALES);
                break;
            case "bLipuntarkastus":
                asetaAsemanTiedot();
                asetaPavelupisteenTiedot(TICKETCHECK);
                setPainettuNappi(TICKETCHECK);

                break;
            case "bMetro":
                asetaAsemanTiedot();
                asetaPavelupisteenTiedot(METRO);
                setPainettuNappi(METRO);
                break;
        }
    }

    @FXML
    private void bSaapuminen() {
        setPainettuNappi(ENTRANCE);
    }
    @FXML
    private void bLipunmyynti() {
        setPainettuNappi(TICKETSALES);
    }
    @FXML
    private void bLipuntarkastus() {
        setPainettuNappi(TICKETCHECK);
    }
    @FXML
    private void bMetro() {
        setPainettuNappi(METRO);
    }


    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

    @Override
    public void naytaLoppuaika(double aika) {
        //Platform.runLater(()->ui.setLoppuaika(aika));
    }
    @Override
    public void visualisoiAsiakas() {
    }

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}
