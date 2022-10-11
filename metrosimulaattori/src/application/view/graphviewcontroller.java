package application.view;

import application.MainApp;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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


}
