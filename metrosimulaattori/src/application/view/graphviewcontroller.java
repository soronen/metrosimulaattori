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
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class graphviewcontroller {

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Button jonoaika;

    @FXML
    private Button palvelluasiakkaat;

    @FXML
    private Button palveluaika;


    @FXML
    private void initialize(){

    }


    public graphviewcontroller(){}

    public void setChart(int x){
        MainApp.getKontrol().asetachart(this, x);
    }

    public BarChart getbarChart(){
        return this.barChart;
    }

    public CategoryAxis getxAxis(){
        return this.xAxis;
    }



    @FXML
    private void nappain1(){
        setChart(1);
    }
    @FXML
    private void nappain2(){
        setChart(2);
    }
    @FXML
    private void nappain3(){
        setChart(3);
    }


}
