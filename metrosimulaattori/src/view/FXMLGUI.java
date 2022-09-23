package view;

import java.io.IOException;
import java.text.DecimalFormat;

import controller.IKontrolleri;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simu.framework.Trace;

public class FXMLGUI extends Application implements ISimulaattorinUI {


    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IKontrolleri kontrolleri;

    private IVisualisointi naytto;


    private Stage primaryStage;
    private TabPane rootLayout;



    @Override
    public void init(){

        Trace.setTraceLevel(Trace.Level.INFO);

        //kontrolleri = new Kontrolleri(this);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Metrosimulaattori");
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMLGUI.class.getResource("Main.fxml"));
            rootLayout = (TabPane) loader.load();

            MainController controller = loader.getController();
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
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }







    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getAika(){
        return 1000;
    }

    @Override
    public long getViive(){
        return 10;
    }

    @Override
    public void setLoppuaika(double aika){
    }

    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }

}