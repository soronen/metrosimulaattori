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
        lista.add(new ppVisualizer(2, 1, TapahtumanTyyppi.TICKETCHECK));
        lista.add(new ppVisualizer(2, 2, TapahtumanTyyppi.TICKETSALES));
        lista.add(new ppVisualizer(3, 1, TapahtumanTyyppi.TICKETCHECK));
        lista.add(new ppVisualizer(4, 1, TapahtumanTyyppi.METRO));

        Platform.runLater(new Runnable() {
            @Override public void run() {



                gp.addColumn(0,lista.get(0).getGroup());
                gp.addColumn(1,lista.get(1).getGroup());
                gp.addColumn(1,lista.get(2).getGroup());
                gp.addColumn(2,lista.get(3).getGroup());
                gp.addColumn(3,lista.get(4).getGroup());


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


                for (int i = 1; i<5; i++){
                    viivapiirra(i, i+1);
                }

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


                lista.get(0).setNumber(pt[0].getJonopituus());

                if (t.getTyyppi() != TapahtumanTyyppi.ENTRANCE && t.getTyyppi() != TapahtumanTyyppi.ARRIVAL){
                    Point2D startpos = null;
                    Point2D endpos = null;

                    int sx = 0;

                    for (ppVisualizer ppv: lista){
                        if (ppv.getTapahtumanTyyppi() == t.getTyyppi() && ppv.getTapahtumanTyyppi() != TapahtumanTyyppi.ENTRANCE){
                            startpos = getboxc2(ppv.getXaxis(), ppv.getYaxis());
                            sx = ppv.getXaxis();
                        }
                    }

                    for (ppVisualizer ppv: lista){
                        if (ppv.getXaxis() == sx+1){

                            for (Palvelupiste v: pt){
                                if (ppv.getTapahtumanTyyppi() == v.getTapahtumanTyyppi()){
                                    ppv.setNumber(v.getJonopituus());
                                }
                            }

                            if (startpos == null){
                                System.out.println("Tyypilla " + t.getTyyppi().name());
                            }

                            endpos = getboxc2(ppv.getXaxis(), ppv.getYaxis());
                        }
                    }

                    if (startpos == null || endpos == null){
                        System.out.println("Null tapahtumalla" + t.getTyyppi().name());
                    }

                    draw(startpos, endpos, bg);
                } else if (t.getTyyppi() == TapahtumanTyyppi.ENTRANCE){

                    Point2D startpos = getboxc2(1, 1);
                    Point2D endpos1 = getboxc2(2, 1);
                    Point2D endpos2 = getboxc2(2, 2);

                    if (ThreadLocalRandom.current().nextBoolean()){
                        draw(startpos, endpos1, bg);
                        lista.get(1).setNumber(pt[2].getJonopituus());
                    } else {
                        draw(startpos, endpos2, bg);
                        lista.get(2).setNumber(pt[1].getJonopituus());
                    }


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

    private void viivapiirra(int x1, int x2){
        ArrayList<ppVisualizer> start = new ArrayList<ppVisualizer>();
        ArrayList<ppVisualizer> end = new ArrayList<ppVisualizer>();


        for (ppVisualizer p : lista){
            if (p.getXaxis() == x1){
                start.add(p);
            } else if (p.getXaxis() == x2){
                end.add(p);
            }
        }

        for (ppVisualizer p : start){

            for (ppVisualizer o : end){
                Point2D startpos = getboxc2(p.getXaxis(), p.getYaxis());
                Point2D endpos = getboxc2(o.getXaxis(), o.getYaxis());

                bg.getChildren().add(new Line(startpos.getX(), startpos.getY(), endpos.getX(), endpos.getY()));

            }

        }

    }


    /*
    Piirtää Panelle p liikkuvan pallon pisteestä x pisteeseen y.
    (Point2D on classi jolla on x ja y arvo)
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



}
