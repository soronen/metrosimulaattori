package application.view;

import application.simu.model.TapahtumanTyyppi;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Palvelupistettä kuvastava luokka simviewille {@link simviewController}
 */
public class ppVisualizer {
    /**
     * StackPane joka asettaa palvelupisteen nimen ja kuvan päällekkäin
     */
    private final StackPane group = new StackPane();
    /**
     * Palvelupisteen nimi + jononpituus
     */
    private final Text pptext;
    /**
     * Palvelupisteen tapahtumantyyppi/nimi
     */
    private final TapahtumanTyyppi tt;

    /**
     * palvelupisteen x sijainti gridpanessa
     */
    private int xaxis;

    /**
     * palvelupisteen y sijainti gridpanessa
     */
    private int yaxis;

    /**
     * Konstruktori
     * @param x palvelupisteen x sijainti gridpanessa
     * @param y palvelupisteen y sijainti gridpanessa
     * @param tt Tapahtuman tyyppi / palvelupisteen nimi
     */
    public ppVisualizer(int x, int y, TapahtumanTyyppi tt){
        this.xaxis = x;
        this.yaxis = y;
        this.tt = tt;

        this.pptext = new Text(tt.name() + " 0");
        pptext.setTextAlignment(TextAlignment.CENTER);
        pptext.setFill(Color.RED);
        pptext.setFont(new Font( 40));


        csquare();
        group.getChildren().add(pptext);

        StackPane.setMargin(pptext, new Insets(0,0,160,0));

        GridPane.setHalignment(group, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(group, VPos.CENTER); // To align vertically in the cell



    }

    /**
     * Luo palvelupisteen kuvan
     * kutsutaan konstruktorista {@link #ppVisualizer(int, int, TapahtumanTyyppi)}
     */
    private void csquare(){
        Rectangle r1 = new Rectangle(80,80);
        r1.setFill(Color.BLACK);
        Image img = null;
        switch (tt){

            case ENTRANCE:
                img = new Image(ppVisualizer.class.getResourceAsStream("images/asema.png"));
                r1.setFill(new ImagePattern(img));
                break;
            case METRO:
                img = new Image(ppVisualizer.class.getResourceAsStream("images/metro.png"));
                r1.setFill(new ImagePattern(img));
                break;
            case TICKETSALES:
                img = new Image(ppVisualizer.class.getResourceAsStream("images/lipunmyynti.png"));
                r1.setFill(new ImagePattern(img));
                break;
            case TICKETCHECK:
                img = new Image(ppVisualizer.class.getResourceAsStream("images/tarkastus.png"));
                r1.setFill(new ImagePattern(img));
                break;

        }

        group.getChildren().add(r1);

    }

    /**
     * getteri
     * @return {@link #group}
     */
    public StackPane getGroup(){
        return this.group;
    }

    /**
     * getteri
     * @return {@link #tt}
     */
    public TapahtumanTyyppi getTapahtumanTyyppi(){
        return this.tt;
    }

    /**
     * Asettaa palvelupisteen tekstin numeron (käytetään jononpituuden visualisointiin)
     * @param x uusi numero
     */
    public void setNumber(int x){
        String temp = this.pptext.getText();
        pptext.setText(temp.replaceAll("\\d","") + Integer.toString(x));
    }

    /**
     * getteri
     * @return {@link #xaxis}
     */
    public int getXaxis() {
        return this.xaxis;
    }

    /**
     * getteri
     * @return {@link #yaxis}
     */
    public int getYaxis() {
        return this.yaxis;
    }

}
