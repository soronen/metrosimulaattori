package application.view;

import application.MainApp;
import application.controller.Kontrolleri;
import application.eduni.distributions.Normal;
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
    @FXML
    private TextField tfEsiostetutliput;
    @FXML
    private TextField tfPalvelupisteenOdotusarvo;
    @FXML
    private TextField tfPalvelupisteenVarianssi;
    @FXML
    private TextField tfSaapumisenOdotusarvo;
    @FXML
    private TextField tfSaapumisenVarianssi;

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
        setSimunSaapumisJakauma();
        kontrolleri.getMoottori();

        setSimulaattorinAsetukset();
        asetaAsemanTiedot();
        kontrolleri.kaynnistaSimulointi();

        salliSimunasetuksienmuutos(false);

    }
    private void salliSimunasetuksienmuutos(boolean sallitaanko) {
        tfPalvelupisteenOdotusarvo.setEditable(sallitaanko);
        tfPalvelupisteenVarianssi.setEditable(sallitaanko);
        tfAsemanKapasiteetti.setEditable(sallitaanko);
        tfMetronKapasiteetti.setEditable(sallitaanko);
        tfEsiostetutliput.setEditable(sallitaanko);
        tfSaapumisenVarianssi.setEditable(sallitaanko);
        tfSaapumisenOdotusarvo.setEditable(sallitaanko);
    }

    public void setSimunSaapumisJakauma() {
        try {
            int arrmean = Integer.parseInt(tfSaapumisenOdotusarvo.getText());
            int arrvar = Integer.parseInt(tfSaapumisenVarianssi.getText());
            kontrolleri.setArrivalJakauma(arrmean, arrvar);
        } catch (NumberFormatException e) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText("Virheellinen syöte");
            alert.setContentText("Syötöksen pitää olla kokonaisluku");
            alert.showAndWait();
        }
    }

    public boolean setSimulaattorinAsetukset() {
        try {
            kontrolleri.setAsemanKapasiteetti(Integer.parseInt(tfAsemanKapasiteetti.getText()));
            kontrolleri.setMetronKapasiteetti(Integer.parseInt(tfMetronKapasiteetti.getText()));
            kontrolleri.setsimulaattorinKesto(Integer.parseInt(tfSimuloinninKesto.getText()));
            kontrolleri.setSimulaattorinViive(Integer.parseInt(tfSimuloinninViive.getText()));
            kontrolleri.setMobiililippujakauma(Integer.parseInt(tfEsiostetutliput.getText()));
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
                // kun simulaattori nollataan, paivitaUI voi heittää NullPointerExceptionin
                try {
                    asetaAsemanTiedot();
                    asetaPavelupisteenTiedot(getPainettuNappi());
                } catch (NullPointerException e) {
                    System.out.println("Simulaattori ei ole käynnissä!");
                }
            }
        });
    }

    private void asetaAsemanTiedot() {
        if (kontrolleri.onkoKaynnissa()) {
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
    }

    private void asetaPavelupisteenTiedot(TapahtumanTyyppi palvelupiste) {
        switch (palvelupiste) {
            case ENTRANCE:
                labelPalvelupiste.setText("Palvelupisteen \"Sisäänkäynti\" tilastot");
                break;
            case TICKETSALES:
                labelPalvelupiste.setText("Palvelupisteen \"Lipunmyynti\" tilastot");
                break;
            case TICKETCHECK:
                labelPalvelupiste.setText("Palvelupisteen \"Lipuntarkastus\" tilastot");
                break;
            case METRO:
                labelPalvelupiste.setText("Palvelupisteen \"Metro\" tilastot");
                break;
        }

        if (kontrolleri.onkoPPVarattu(palvelupiste)) {
            labelPavelunTila.setText("Varattu");
        } else {
            labelPavelunTila.setText("Vapaa");
        }
        labelJonossaOlevatAsiakkaat.setText(String.valueOf(kontrolleri.getPPjononpituus(palvelupiste)));
        labelJononKeskipituus.setText(String.valueOf(kontrolleri.getPPkeskijonoaika(palvelupiste)));
        labelPavellutAsiakkaat.setText(String.valueOf(kontrolleri.getPPpalvellutAsiakkaat(palvelupiste)));
        labelPavelunKeskipituus.setText(String.valueOf(kontrolleri.getPPkeskiarvoaika(palvelupiste)));

    }

    private void setPainettuNappi(TapahtumanTyyppi tt) {
        painettuNappi = tt;

        int jakauma[] = kontrolleri.getPPJakauma(tt);
        tfPalvelupisteenOdotusarvo.setText(String.valueOf(jakauma[0]));
        tfPalvelupisteenVarianssi.setText(String.valueOf(jakauma[1]));
    }

    private TapahtumanTyyppi getPainettuNappi() {
        return painettuNappi;
    }

    @FXML
    private void setPPJakauma() {
        if (!kontrolleri.onkoKaynnissa()) {
            try {
                int mean = Integer.valueOf(tfPalvelupisteenOdotusarvo.getText());
                int variance = Integer.valueOf(tfPalvelupisteenVarianssi.getText());
                kontrolleri.setPPJakauma(getPainettuNappi(), mean, variance);

            } catch (NumberFormatException e) {
                // Show the error message.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Virhe");
                alert.setHeaderText("Virheellinen syöte");
                alert.setContentText("Syötöksen pitää olla kokonaisluku");
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void valittuPalvelupiste(Event evt) {

        if (kontrolleri.onkoKaynnissa()) {
            asetaAsemanTiedot();
        }
        // hakee tapahtuman kutsujan fxid:n mustalla magialla
        String napinID = ((Control)evt.getSource()).getId();
        switch (napinID) {
            case "bSisaankaynti":
                asetaPavelupisteenTiedot(ENTRANCE);
                setPainettuNappi(ENTRANCE);
                break;
            case "bLipunmyynti":
                asetaPavelupisteenTiedot(TICKETSALES);
                setPainettuNappi(TICKETSALES);
                break;
            case "bLipuntarkastus":
                asetaPavelupisteenTiedot(TICKETCHECK);
                setPainettuNappi(TICKETCHECK);
                break;
            case "bMetro":
                asetaPavelupisteenTiedot(METRO);
                setPainettuNappi(METRO);
                break;
        }
    }

    @FXML
    public void nollaaSimulaattori() {
        kontrolleri.resetSimulator();
        salliSimunasetuksienmuutos(true);

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