package application;

import java.io.IOException;

import application.controller.IKontrolleri;
import application.view.IVisualisointi;
import application.view.RootLayoutController;
import application.view.StatsTabController;
import application.simu.framework.Trace;
import application.view.simviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private static IKontrolleri kontrol;

    @Override
    public void init(){

        Trace.setTraceLevel(Trace.Level.INFO);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Metrosimulaattori");

        initRootLayout();

        showStatsTab();
    }

    public static void setKontrol(IKontrolleri ii){
        kontrol = ii;
    }


    public static IKontrolleri getKontrol(){
        return kontrol;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Give the controller access to the main app.

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showStatsTab() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StatsTab.fxml"));
            AnchorPane statsTab = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(statsTab);

            // Give the controller access to the main app.
            StatsTabController controller = loader.getController();
            controller.setMainApp(this);


            kontrol.setUi((IVisualisointi) controller);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSimview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/simview.fxml"));
            AnchorPane sw = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(sw);

            // Give the controller access to the main app.
            simviewController controller = loader.getController();
            controller.setMainApp(this);


            kontrol.setUi((IVisualisointi) controller);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}