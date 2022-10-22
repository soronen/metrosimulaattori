package application.view;

import application.MainApp;
import application.controller.Kontrolleri;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    /**
     * Kutsutaan kun RootLayout luodaan / kun sovellus käynnistetään. Alustaa simulaattorin luomalla
     * pää kontrollerin.
     */
    @FXML
    private void initialize(){
        Kontrolleri kontrolleri = new Kontrolleri();
    }

    /**
     * Kutsutaan kun tekstinäkymä painiketta painetaan yläpalkista. Avaa päänäkymän.
     */
    @FXML
    public void avaaSimulaattori() {
        mainApp.showStatsTab();
    }

    /**
     * Kusutaan kun Graafinen näkymä painiketta painetaan yläpalkista. Avaa simulaatio näkymän / simviewin.
     */
    @FXML
    public void avaasimview() {
        mainApp.showSimview();
    }

    /**
     * Kutsutaan yläpalkin näytä tilastoja painikkeesta. Avaa tilasto ikkunan.
     */
    @FXML
    public void avaagraphview(){mainApp.showgraphview();}

    /**
     * Kutsutaan kun tietoa mesitä painiketta painetaan. Avaa tieto ikkunan ja asettaa tiedot siihen.
     */
    @FXML
    public void aboutUs() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.mainApp.getPrimaryStage());
        VBox dialogVbox = new VBox(20);
        Text t = new Text("  Tekijät: Eetu Soronen ja Emil Ålgars  ");
        Text filler = new Text("  ");
        Text t2 = new Text("  metrosimulaattori🚇(tm) 2022 all rights reserved  ");
        t.setStyle("-fx-font: 16 arial;");
        t2.setStyle("-fx-font: 20 'Old English Text MT'");
        dialogVbox.getChildren().add(t);
        dialogVbox.getChildren().add(filler);
        dialogVbox.getChildren().add(t2);
        Scene dialogScene = new Scene(dialogVbox, 420, 120);
        dialog.setScene(dialogScene);
        dialog.show();
    }

}
