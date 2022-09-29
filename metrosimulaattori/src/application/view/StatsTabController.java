package application.view;

import application.MainApp;
import application.controller.Kontrolleri;
import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static application.simu.model.TapahtumanTyyppi.*;

public class StatsTabController {
    @FXML
    private TextField tfAsemanKapasiteetti;
    @FXML
    private TextField tfMetronKapasiteetti;
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


    private IMoottori moottori;
    private Palvelupiste[] palvelupisteet;
    private TapahtumanTyyppi painettuNappi = TapahtumanTyyppi.ENTRANCE;
    private Kontrolleri kontrolleri;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        kontrolleri = new Kontrolleri(this);
    }


    // Moottorin ohjausta:
    public void kaynnista() {
        kontrolleri.getMoottori();
        palvelupisteet = kontrolleri.getPalvelupisteet();
        moottori = kontrolleri.getMoottori();

        setSimulaattorinAsetukset();
        asetaAsemanTiedot();
        kontrolleri.kaynnistaSimulointi();

    }

    public boolean setSimulaattorinAsetukset() {
        try {
            int asemanKapasiteetti = Integer.parseInt(tfAsemanKapasiteetti.getText());
            int metronKapasiteetti = Integer.parseInt(tfMetronKapasiteetti.getText());

            int simukesto = Integer.parseInt(tfSimuloinninKesto.getText());
            int simuviive = Integer.parseInt(tfSimuloinninViive.getText());

            kontrolleri.setAsemanKapasiteetti(asemanKapasiteetti);
            kontrolleri.setMetronKapasiteetti(metronKapasiteetti);
            kontrolleri.setsimulaattorinKesto(simukesto);
            kontrolleri.setSimulaattorinViive(simuviive);
            return true;

        } catch (NumberFormatException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText("Virheellinen syöte");
            alert.setContentText("Syötöksen pitää olla kokonaisluku");
            alert.showAndWait();
        }
        return false;
    }

    public void hidasta() { // hidastetaan moottorisäiettä
        kontrolleri.hidasta();
        tfSimuloinninViive.setText(String.valueOf(kontrolleri.getViive()));
    }


    public void nopeuta() { // nopeutetaan moottorisäiettä
        kontrolleri.nopeuta();
        tfSimuloinninViive.setText(String.valueOf(kontrolleri.getViive()));
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
        tfMetronKapasiteetti.setText(String.valueOf(kontrolleri.getMetronKapasiteetti()));
        tfAsemanKapasiteetti.setText(String.valueOf(kontrolleri.getAsemanKapasiteetti()));
        labelAsemassaOlevatAsiakkaat.setText(String.valueOf(kontrolleri.getAsiakkaatAsemassa()));
        labelMetroPoistuneetAsiakkaat.setText(String.valueOf(kontrolleri.getPalvellutAsaiakkaat()));

    }

    private void asetaPavelupisteenTiedot(TapahtumanTyyppi palvelupiste) {
        int index = 0;
        switch (palvelupiste) {
            case ENTRANCE:
                index = 0;
                labelPalvelupiste.setText("Palvelupisteen \"Saapuminen\" tilastot");
                   break;
            case TICKETSALES:
                index = 1;
                labelPalvelupiste.setText("Palvelupisteen \"Lipunmyynti\" tilastot");
                break;
            case TICKETCHECK:
                labelPalvelupiste.setText("Palvelupisteen \"Lipuntarkastus\" tilastot");
                index = 2;
                break;
            case METRO:
                labelPalvelupiste.setText("Palvelupisteen \"Metro\" tilastot");
                index = 3;
                break;
        }
        if (palvelupisteet[index].onVarattu()) {
            labelPavelunTila.setText("Varattu");
        } else {
            labelPavelunTila.setText("Vapaa");
        }
        labelJonossaOlevatAsiakkaat.setText(String.valueOf(palvelupisteet[index].getJonopituus()));
        labelJononKeskipituus.setText(String.valueOf(palvelupisteet[index].getKeskijonoaika()));
        labelPavellutAsiakkaat.setText(String.valueOf(palvelupisteet[index].getPalvelunro()));
        labelPavelunKeskipituus.setText(String.valueOf(palvelupisteet[index].getKeskiarvoaika()));
    }

    private void setPainettuNappi(TapahtumanTyyppi tt) {
        painettuNappi = tt;
    }

    private TapahtumanTyyppi getPainettuNappi() {
        return painettuNappi;
    }


    @FXML
    private void valittuPalvelupiste(Event evt) {

        // hakee tapahtuman kutsujan fxid:n mustalla magialla
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
    public void nollaaSimulaattori() {
        kontrolleri.resetSimulator();
        asetaAsemanTiedot();
        asetaPavelupisteenTiedot(ENTRANCE);
    }

    // Simulointitulosten välittämistä käyttöliittymään.
    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:

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