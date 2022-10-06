package application.view;

import application.MainApp;
import application.controller.IKontrolleri;
import application.controller.Kontrolleri;
import application.eduni.distributions.Normal;
import application.simu.framework.IMoottori;
import application.simu.framework.Kello;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static application.simu.model.TapahtumanTyyppi.*;

/**
 * StatsTabController toimii saman nimisen fxml-tiedoston kontrollerina
 *
 * @author Eetu Soronen
 * @version 1
 */

public class StatsTabController implements  IVisualisointi{
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

    /**
     * enum-arvo, joka pitää kirjaa siitä mitä palvelupistettä vastaavaa nappia on painettu
     */
    private TapahtumanTyyppi painettuNappi = TapahtumanTyyppi.ENTRANCE;

    /**
     * Kontrolleri-luokka, joka kommunikoi simulaattorimallin kanssa
     */
    private IKontrolleri kontrolleri;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        kontrolleri = (Kontrolleri) MainApp.getKontrol();

        // simuloinnin viiveen voi antaa enteriä painamalla kun simu on käynnissä
        tfSimuloinninViive.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER && kontrolleri.onkoKaynnissa()){
                kontrolleri.setSimulaattorinViive(Integer.parseInt(tfSimuloinninViive.getText()));
            }
        });
    }


    /**
     * Kutsuu tarvittavia metodeja simulaattorin käynnistämiseen,
     * sen parametrien asettamiseen ja käyttöliittymän tekstikenttien lukitsemiseen.
     */
    public void kaynnista() {
        kontrolleri.setUi(this);
        setSimunSaapumisJakauma();
        kontrolleri.getMoottori();

        setSimulaattorinAsetukset();
        asetaAsemanTiedot();
        kontrolleri.kaynnistaSimulointi();

        salliSimunasetuksienmuutos(false);

    }

    /**
     * kutsuu tekstikenttien, joita ei saa muuttaa simun aikana setEditable() -metodia.
     * @param sallitaanko boolean joka asettaa
     */
    private void salliSimunasetuksienmuutos(boolean sallitaanko) {
        tfPalvelupisteenOdotusarvo.setEditable(sallitaanko);
        tfPalvelupisteenVarianssi.setEditable(sallitaanko);
        tfAsemanKapasiteetti.setEditable(sallitaanko);
        tfMetronKapasiteetti.setEditable(sallitaanko);
        tfEsiostetutliput.setEditable(sallitaanko);
        tfSaapumisenVarianssi.setEditable(sallitaanko);
        tfSaapumisenOdotusarvo.setEditable(sallitaanko);
    }

    /**
     * Lukee aseman saapumisten jakauman arvot käyttöliittymästä ja lähettää ne kontrollerille.
     */

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


    /**
     * Lukee simulaattorin parameteja käyttöliittymästä ja lähettää ne kontrollerille.
     */

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

    /**
     * Kutsuu kontrollerin hidasta() metodia ja päivittää uuden viiveen käyttöliittymään.
     */
    public void hidasta() { // hidastetaan moottorisäiettä
        if (kontrolleri.onkoKaynnissa()) {
            kontrolleri.hidasta();
            tfSimuloinninViive.setText(String.valueOf(kontrolleri.getViive()));
        }
    }

    /**
     * Kutsuu kontrollerin nopeuta() metodia ja päivittää uuden viiveen käyttöliittymään.
     */
    public void nopeuta() { // nopeutetaan moottorisäiettä
        if (kontrolleri.onkoKaynnissa()) {
            kontrolleri.nopeuta();
            tfSimuloinninViive.setText(String.valueOf(kontrolleri.getViive()));
        }
    }


    /**
     * luo Runnable() -olion joka kutsuu asetaAsemanTiedot() ja asetaPavelupisteenTiedot(getPainettuNappi()) metodeja
     * jotka vastaavat käyttöliittymän päivittämisestä.
     */
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

    /**
     * päivittää Metroaseman tiedot käyttöliittymässä kontrollerin tietojen perusteella.
     */
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

    /**
     * Päivittää valitun palvelupisteen tiedot kontrollerin tietojen perusteilla.
     * @param palvelupiste Palvelupiste, jonka tiedot haetaan ja näytetään.
     */
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


    /**
     * Asettaa tämän hetkisen painetun napin, sekä hakee ja näyttää käyttöliittymässä
     * valitun palvelupisteen jakauman arvot
     *
     * @param tt palvelupiste, jota vastaavaa nappia on painettu
     */
    private void setPainettuNappi(TapahtumanTyyppi tt) {
        painettuNappi = tt;

        int jakauma[] = kontrolleri.getPPJakauma(tt);
        tfPalvelupisteenOdotusarvo.setText(String.valueOf(jakauma[0]));
        tfPalvelupisteenVarianssi.setText(String.valueOf(jakauma[1]));
    }

    /**
     * palauttaa painetun napin TapahtumanTyyppi arvon.
     *
     * @return TapahtumanTyyppi painettuNappi, joka vastaa tiettyä palvelupistettä
     */
    private TapahtumanTyyppi getPainettuNappi() {
        return painettuNappi;
    }


    /**
     * Asettaa valitun palvelupisteen jakaumat käyttöliittymän tietojen perusteella
     */
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


    /**
     *  Kun nappia painetaan, asettaa painetun napin ja päivittää käyttöliittymän sitä vastaavan
     *  palvelupisteen tiedoilla.
     * @param evt Napin painallus
     */
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

    /**
     * Kutsuu kontrolleri-luokan nollaaSimulaattori metodia ja sallii simulaattorin parametrien muokkauksen
     */
    @FXML
    public void nollaaSimulaattori() {
        if (kontrolleri.onkoKaynnissa()) {
            kontrolleri.resetSimulator();
            salliSimunasetuksienmuutos(true);
        }

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