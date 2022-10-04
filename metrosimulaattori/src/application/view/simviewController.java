package application.view;

import application.MainApp;
import application.controller.IKontrolleri;
import application.controller.Kontrolleri;
import application.simu.framework.IMoottori;
import application.simu.framework.Tapahtuma;
import application.simu.model.OmaMoottori;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
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


public class simviewController implements IVisualisointi{
    @FXML
    private GridPane gp;

    @FXML
    private Pane bg;

    private MainApp mainApp;

    private IMoottori m;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }



    @FXML
    public void paivitaUI(){

        Platform.runLater(new Runnable() {
            @Override public void run() {
                Group g1 = new Group();
                Group g2 = new Group();

                GridPane.setHalignment(g1, HPos.CENTER); // To align horizontally in the cell
                GridPane.setValignment(g1, VPos.CENTER); // To align vertically in the cell

                GridPane.setHalignment(g2, HPos.CENTER); // To align horizontally in the cell
                GridPane.setValignment(g2, VPos.CENTER); // To align vertically in the cell

                g1.getChildren().add(new Text("Pp 1"));
                Rectangle r1 = new Rectangle(40,40);
                r1.setFill(Color.BLACK);
                g1.getChildren().add(r1);

                g2.getChildren().add(new Text("Pp 2"));
                Rectangle r2 = new Rectangle(40,40);
                r2.setFill(Color.BLUE);
                g2.getChildren().add(r2);



                gp.addColumn(0, g1);
                gp.addColumn(1,g2);

                gp.setStyle(" -fx-background-color: pink;");


                Bounds b = bg.getBoundsInParent();


                Point2D i = getboxc2(1,1);
                Point2D e = getboxc2(2, 1);
                Point2D e2 = getboxc2(2, 2);


                System.out.println("bg min" + b.getMinX() + " : " + b.getMinY());
                System.out.println("bg max" + b.getMaxX() + " : " + b.getMaxY());
                System.out.println("bg w " + b.getWidth() + " bg h " + b.getHeight());
                System.out.println(i);
                System.out.println(e);

                bg.getChildren().add(new Line(i.getX(), i.getY(), e.getX(), e.getY()));
                bg.getChildren().add(new Line(i.getX(), i.getY(), e2.getX(), e2.getY()));

                draw(i, e, bg);
                draw(i, e2, bg);

            }
        });




    }

    private Point2D getboxc2(int x, int y){

        Bounds b = bg.getBoundsInParent();

        double boxw = b.getWidth() / 4;
        double boxh = b.getHeight() / 5;

        double endx = (boxw*x)-(boxw/2);
        double endy = (boxh*y)-(boxh/2);

        return new Point2D(endx, endy);


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
