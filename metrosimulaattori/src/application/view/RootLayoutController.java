package application.view;

import application.MainApp;
import application.controller.Kontrolleri;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RootLayoutController {

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

    @FXML
    private void initialize(){
        Kontrolleri kontrolleri = new Kontrolleri();
    }


    @FXML
    public void avaaSimulaattori() {
        mainApp.showStatsTab();
    }

    @FXML
    public void avaasimview() {
        mainApp.showSimview();
    }

}
