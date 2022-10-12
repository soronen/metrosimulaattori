package application.view;

import application.MainApp;
import entity.ServicePoint;
import entity.Simulaattori;
import entity.Station;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Taulukko ikkunan controlleri
 */
public class graphviewcontroller {
    /**
     * FXML viittaus sivun Taulukkoon
     */
    @FXML
    private BarChart<String, Double> barChart;

    /**
     * FXML viittaus sivun taulukon {@link #barChart} x axeliin
     */
    @FXML
    private CategoryAxis xAxis;

    /**
     * Jonoaika nappi
     */
    @FXML
    private Button jonoaika;

    /**
     * Näkymässä sijaitseva lista, johon listataan simulointi historia
     */
    @FXML
    private ListView lv;

    /**
     * Palvellutasiakkaat nappi
     */
    @FXML
    private Button palvelluasiakkaat;

    /**
     * Palveluaika nappi
     */
    @FXML
    private Button palveluaika;

    /**
     * Muuttuja joka kuvastaa mitä näppäintä on painettu viimmeiseksi
     */
    private int currentnum = 1;

    @FXML
    private Text skesto;
    @FXML
    private Label sid;
    @FXML
    private Label cmode;
    @FXML
    private Text akap;

    @FXML
    private Text p0txt1;
    @FXML
    private Text p0txt2;

    @FXML
    private Label p1txt1;
    @FXML
    private Text p1txt2;
    @FXML
    private Text p1txt3;

    @FXML
    private Label p2txt1;
    @FXML
    private Text p2txt2;
    @FXML
    private Text p2txt3;

    @FXML
    private Label p3txt1;
    @FXML
    private Text p3txt2;
    @FXML
    private Text p3txt3;

    @FXML
    private Label p4txt1;
    @FXML
    private Text p4txt2;
    @FXML
    private Text p4txt3;


    /**
     * oletuskonstruktori
     */
    public graphviewcontroller(){}

    /**
     * Asettaa taulukon arvot
     * @param x mikä näppäin on painettu
     */
    public void setChart(int x){
        this.currentnum = x;
        MainApp.getKontrol().asetachart(this, x);
    }

    /**
     * getteri
     * @return {@link #barChart}
     */
    public BarChart getbarChart(){
        return this.barChart;
    }

    /**
     * getteri
     * @return {@link #xAxis}
     */
    public CategoryAxis getxAxis(){
        return this.xAxis;
    }

    /**
     * Getteri
     * @return {@link #lv}
     */
    public ListView getListView(){
        return this.lv;
    }


    /**
     * {@link #palveluaika} kutsuu tätä kun sitä painetaan
     */
    @FXML
    private void nappain1(){
        setChart(1);
    }

    /**
     * {@link #palvelluasiakkaat}  kutsuu tätä kun sitä painetaan
     */
    @FXML
    private void nappain2(){
        setChart(2);
    }

    /**
     * {@link #palveluaika} kutsuu tätä kun sitä painetaan
     */
    @FXML
    private void nappain3(){
        setChart(3);
    }

    /**
     * Käsittelee mitä käy kun {@link #lv} painetaan
     */
    @FXML
    private void lvhandleclick(){
        MainApp.getKontrol().asetachart(this, currentnum);
    }

    /**
     * Asettaa graphviewin oiken infopalkin tiedot
     * @param sim simulaattori josta tiedot otetaan palkkiin
     */
    public void setInfo(Simulaattori sim){

        sid.setText("Simu id :" + sim.getId());

        switch (this.currentnum){
            case 1:
                cmode.setText(jonoaika.getText());
                break;
            case 2:
                cmode.setText(palvelluasiakkaat.getText());
                break;
            case 3:
                cmode.setText(palveluaika.getText());
                break;
        }

        skesto.setText(Integer.toString(sim.getSimunKesto()));

        Station asema = sim.getAsema();

        akap.setText(Integer.toString(asema.getAsemanKapasiteetti()));


        p0txt1.setText("Odotusarvo : " + Integer.toString(asema.getAsiakkaidenSaapumisenOdotusarvo()));
        p0txt2.setText("Varianssi : " + Integer.toString(asema.getAsiakkaidenSaapumisenVarianssi()));

        ServicePoint sp;

        sp = sim.getEntrance();
        p1txt1.setText(sp.getPalvelupiste().name());
        p1txt2.setText("Odotusarvo : " + Integer.toString(sp.getPalvelupisteenOdotusarvo()));
        p1txt3.setText("Varianssi : " + Integer.toString(sp.getPalvelupisteenVarianssi()));

        sp = sim.getTicketsales();
        p2txt1.setText(sp.getPalvelupiste().name());
        p2txt2.setText("Odotusarvo : " + Integer.toString(sp.getPalvelupisteenOdotusarvo()));
        p2txt3.setText("Varianssi : " + Integer.toString(sp.getPalvelupisteenVarianssi()));

        sp = sim.getTicketcheck();
        p3txt1.setText(sp.getPalvelupiste().name());
        p3txt2.setText("Odotusarvo : " + Integer.toString(sp.getPalvelupisteenOdotusarvo()));
        p3txt3.setText("Varianssi : " + Integer.toString(sp.getPalvelupisteenVarianssi()));

        sp = sim.getMetro();
        p4txt1.setText(sp.getPalvelupiste().name());
        p4txt2.setText("Odotusarvo : " + Integer.toString(sp.getPalvelupisteenOdotusarvo()));
        p4txt3.setText("Varianssi : " + Integer.toString(sp.getPalvelupisteenVarianssi()));

    }


}
