package models;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created by odinahka on 9/11/2019.
 */
public class Texts {
    public TextField textField(int maxWidth, int fontSize, String promptText)
    {
        TextField text;
        text = new TextField();
        text.setPromptText(promptText);
        text.setFont(Font.font("SanSerif", fontSize));
        text.setMaxWidth(maxWidth);
        return text;
    }
    public DatePicker datePicker(int maxWidth, String entry)
    {
        DatePicker date;
        date = new DatePicker();
        date.setPromptText(entry);
        date.setMaxWidth(maxWidth);
        date.setStyle("-fx-font-size: 15");
        return date;
    }
    public PasswordField passwordField (int maxWidth, String promptText)
    {
        PasswordField password;
        password = new PasswordField();
        password.setPromptText(promptText);
        password.setFont(Font.font("SanSerif", 15));
        password.setMaxWidth(maxWidth);
        return password;
    }
    public RadioButton radioButton(String sex)
    {
        RadioButton gd;;
        gd = new RadioButton(sex);
        return gd;
    }
    public Label label(String lb)
    {
        Label label = new Label(lb);
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font("SanSerif", 20));
        return label;
    }

}
