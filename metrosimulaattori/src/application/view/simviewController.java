package application.view;

import application.MainApp;
import application.controller.IKontrolleri;
import application.controller.Kontrolleri;
import application.simu.framework.IMoottori;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import application.simu.model.Palvelupiste;
import application.simu.model.TapahtumanTyyppi;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class simviewController implements IVisualisointi{
    @FXML
    private GridPane gp;

    @FXML
    private Pane bg;

    private MainApp mainApp;

    private Palvelupiste[] pt;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private ArrayList<ppVisualizer> lista = new ArrayList<ppVisualizer>();

    @FXML
    private void initialize() {

        gp.setAlignment(Pos.CENTER);
        gp.toFront();
        bg.toBack();

        lista.add(new ppVisualizer(1, 1, TapahtumanTyyppi.ENTRANCE));
        lista.add(new ppVisualizer(2, 2, TapahtumanTyyppi.TICKETSALES));
        lista.add(new ppVisualizer(3, 1, TapahtumanTyyppi.TICKETCHECK));
        lista.add(new ppVisualizer(4, 1, TapahtumanTyyppi.METRO));



        Platform.runLater(new Runnable() {
            @Override public void run() {



                gp.addColumn(0,lista.get(0).getGroup());
                gp.addColumn(1, new Text(""));
                gp.addColumn(1,lista.get(1).getGroup());
                gp.addColumn(2,lista.get(2).getGroup());
                gp.addColumn(3,lista.get(3).getGroup());


                if (MainApp.getKontrol().getMoottori() != null){
                    Palvelupiste[] ppt = MainApp.getKontrol().getMoottori().getPalvelupisteet();

                    for (Palvelupiste p : ppt){

                        for (ppVisualizer v : lista){

                            if (p.getTapahtumanTyyppi() == v.getTapahtumanTyyppi()){
                                v.setNumber(p.getJonopituus());
                            }

                        }

                    }

                } // if loppuu


                viivapiirra(lista.get(0), lista.get(1));
                viivapiirra(lista.get(0), lista.get(2));
                viivapiirra(lista.get(1), lista.get(2));
                viivapiirra(lista.get(2), lista.get(3));

                System.out.println(bg.getBoundsInLocal());
                System.out.println(bg.getBoundsInParent());
                System.out.println(gp.getBoundsInParent());
                System.out.println(gp.getBoundsInLocal());

            }
        });

    }


    @FXML
    public void paivitaUI(Tapahtuma t){

        Platform.runLater(new Runnable() {
            @Override public void run() {



                pt = MainApp.getKontrol().getPalvelupisteet();


                for (Palvelupiste p : pt){

                    for (ppVisualizer v : lista){

                        if (p.getTapahtumanTyyppi() == v.getTapahtumanTyyppi()){
                            v.setNumber(p.getJonopituus());
                        }

                    }

                }


                switch (t.getTyyppi()){

                    case ENTRANCE:

                        if (ThreadLocalRandom.current().nextBoolean()){
                            smartpiirra(lista.get(0), lista.get(1));
                        } else {
                            smartpiirra(lista.get(0), lista.get(2));
                        }

                        break;

                    case TICKETCHECK:

                        smartpiirra(lista.get(2), lista.get(3));

                        break;

                    case TICKETSALES:

                        smartpiirra(lista.get(1), lista.get(2));

                        break;

                }



            }
        });




    }

    private Point2D getboxc2(int x, int y){

        Bounds b = bg.getBoundsInLocal();


        double boxw = b.getWidth() / 4;
        double boxh = b.getHeight() / 5;

        double endx = (boxw*x)-(boxw/2);
        double endy = (boxh*y)-(boxh/2);

        return new Point2D(endx, endy);


    }

    private void viivapiirra(ppVisualizer x1, ppVisualizer x2){
        Point2D startpos = getboxc2(x1.getXaxis(), x1.getYaxis());
        Point2D endpos = getboxc2(x2.getXaxis(), x2.getYaxis());

        bg.getChildren().add(new Line(startpos.getX(), startpos.getY(), endpos.getX(), endpos.getY()));


    }


    /*
    Piirtää Panelle p liikkuvan pallon pisteestä x pisteeseen y.
    (Point2D on classi jolla on x ja y arvo)
    preffered metodi piirtämiseen on smartpiirrä()
     */
    private void draw(Point2D x, Point2D y, Pane p){

        Platform.runLater(new Runnable() {
            @Override public void run() {

                int cwidth = 10;

                Circle ball = new Circle(cwidth, Color.BLACK);

                ball.relocate(x.getX(), x.getY() - (cwidth));
                p.getChildren().add(ball);


                //korkeus lasketaan näin päin, sillä
                //javafx koordinaatistossa orego on
                //vasemmassa yläkulmassa. neg y on ylös
                double xmatka = y.getX() - x.getX();
                double ymatka = y.getY() - x.getY();


                Timeline timeline = new Timeline();

                KeyFrame moveBall = new KeyFrame(Duration.seconds(.005),
                        (ActionEvent event) -> {
                            ball.setTranslateX(ball.getTranslateX() + (xmatka/100));
                            ball.setTranslateY(ball.getTranslateY() + (ymatka/100));
                        });

                timeline.getKeyFrames().add(moveBall);
                timeline.setCycleCount(100);
                timeline.play();

                //poistetaan pallo
                timeline.setOnFinished(event -> p.getChildren().remove(ball));


            }
        });

    }

    private void smartpiirra(ppVisualizer p1, ppVisualizer p2){

        Point2D startpos = getboxc2(p1.getXaxis(), p1.getYaxis());
        Point2D endpos = getboxc2(p2.getXaxis(), p2.getYaxis());

        draw(startpos, endpos, bg);

    }



}
