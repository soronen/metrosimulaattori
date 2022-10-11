package application;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import application.controller.IKontrolleri;
import application.view.*;
import application.simu.framework.Trace;
import datasource.MySqlJpaConn;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private static IKontrolleri kontrol;

    private EntityManager em;

    @Override
    public void init(){

        Trace.setTraceLevel(Trace.Level.INFO);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Metrosimulaattori");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("view/images/metrosim.png")));

        initRootLayout();
        showStatsTab();

        //luodaan tietokantayhteys sovelluksen käynnistyessä
        Thread thread = new Thread(){
            public void run(){
                em = MySqlJpaConn.getInstance();
                return;
            }
        };
        thread.start();
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

    public void showgraphview() {

        if (em == null) {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virhe");
            alert.setHeaderText("Virhe..");
            alert.setContentText("Ei yhteyttä tietokantaan");
            alert.showAndWait();
            return;
        }

        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/graphview.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();



            dialogStage.setTitle("Kraaffi näkymä");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);



            // Set the persons into the controller.
            graphviewcontroller controller = loader.getController();


            dialogStage.show();


            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                        kontrol.initchart(controller);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            scene.setOnKeyPressed(event -> {
                String codeString = event.getCode().toString();
                if (codeString.equals("DELETE")){
                    kontrol.dChart(controller);
                }
            });


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