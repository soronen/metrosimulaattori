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

public class graphviewcontroller {

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Button jonoaika;

    @FXML
    private ListView lv;

    @FXML
    private Button palvelluasiakkaat;

    @FXML
    private Button palveluaika;

    private int currentnum = 1;




    public graphviewcontroller(){}

    public void setChart(int x){
        this.currentnum = x;
        MainApp.getKontrol().asetachart(this, x);
    }

    public BarChart getbarChart(){
        return this.barChart;
    }

    public CategoryAxis getxAxis(){
        return this.xAxis;
    }

    public ListView getListView(){
        return this.lv;
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

    @FXML
    private void lvhandleclick(){
        MainApp.getKontrol().asetachart(this, currentnum);
    }


}
