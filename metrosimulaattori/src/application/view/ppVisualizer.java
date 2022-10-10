package application.view;

import application.simu.model.TapahtumanTyyppi;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ppVisualizer {

    private StackPane group = new StackPane();
    private Text pptext = null;
    private Rectangle box = null;

    private TapahtumanTyyppi tt;

    private int xaxis;

    private int yaxis;

    public ppVisualizer(int x, int y, TapahtumanTyyppi tt){
        this.xaxis = x;
        this.yaxis = y;
        this.tt = tt;

        this.pptext = new Text(tt.name() + " 0");
        pptext.setTextAlignment(TextAlignment.CENTER);
        pptext.setFill(Color.RED);
        pptext.setFont(new Font(40));


        csquare();
        group.getChildren().add(pptext);

        StackPane.setMargin(pptext, new Insets(0,0,160,0));

        GridPane.setHalignment(group, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(group, VPos.CENTER); // To align vertically in the cell



    }

    private void csquare(){
        Rectangle r1 = new Rectangle(80,80);
        r1.setFill(Color.BLACK);
        group.getChildren().add(r1);

    }

    public StackPane getGroup(){
        return this.group;
    }

    public TapahtumanTyyppi getTapahtumanTyyppi(){
        return this.tt;
    }

    public void setNumber(int x){
        String temp = this.pptext.getText();
        pptext.setText(temp.replaceAll("\\d","") + Integer.toString(x));
    }

    public int getXaxis() {
        return this.xaxis;
    }

    public int getYaxis() {
        return this.yaxis;
    }

}
