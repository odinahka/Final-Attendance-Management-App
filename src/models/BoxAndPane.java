package models;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by odinahka on 9/8/2019.
 */
public class BoxAndPane {
   public VBox vbox()
    {
     VBox vb = new VBox(20);
        vb.setPadding(new Insets(30, 0, 0, 40));
        return vb;
    }

    public Rectangle rectangle(int width, int height)
    {
        Rectangle rt = new Rectangle(width, height);
            rt.setX(0);
            rt.setY(0);
            rt.setArcHeight(15);
            rt.setArcWidth(15);
            rt.setFill(Color.rgb(1, 2, 3));
            Color foreground = Color.rgb(2,2,255);
            rt.setStroke(foreground);
            rt.setStrokeWidth(1.5);
        return rt;
    }
   public ScrollPane scrollpane(int prefWidth, int prefHeight, TableView content)
    {
        ScrollPane sp = new ScrollPane();
        sp.setContent(content);
        sp.setPrefSize(prefWidth, prefHeight);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setHmax(3);
        sp.setHvalue(0);
        return sp;
    }
    public Button button (int prefWidth,int fontSize, String detail)
    {
        Button b = new Button(detail);
        b.setPrefWidth(prefWidth);
        b.setStyle("-fx-background-color: MidnightBlue");
        b.setTextFill(Color.WHITESMOKE);
        b.setFont(Font.font("SanSerif", FontWeight.BOLD, fontSize));
        return b;
    }
}
