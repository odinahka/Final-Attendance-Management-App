package mainPack;

import DatabaseTransaction.DBconnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import scenes.Levels;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn;
    String ID;
    Scene loginScene, secondScene;

    private VBox  options;

    Stage secondaryStage, tertiaryStage;
    BoxAndPane bp;
    BorderPane place;
    Tables table;
    Texts texts;
    {
        bp = new BoxAndPane();
        options = bp.vbox();
        place = new BorderPane();
        table = new Tables();
        texts = new Texts();
        secondaryStage = new Stage();
        tertiaryStage = new Stage();
        //Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        secondaryStage.setTitle("Attendance Manager");
        secondaryStage.getIcons().add(new Image("file:Unizik.png"));
        conn = DBconnection.Dbconnect(); // checkConnection();
        secondScene = new Scene(place,280,500);
        secondScene.getStylesheets().add(getClass().getResource("mainDesign.css").toExternalForm());
        secondaryStage.setResizable(false);
        //scene1.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
    }

    //This method is for the admin login
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.initStyle(StageStyle.TRANSPARENT); //makes the primary stage transparent
        primaryStage.getIcons().add(new Image("file:Unizik.png"));
        Group root = new Group(); //creates a container for all contents of scene1
        loginScene = new Scene(root, 280, 200, Color.rgb(0,0,0,0)); //adds the group "root" to the login scene
        //rectangular background
        Rectangle background = new Rectangle(280, 200);
        background.setX(0);
        background.setY(0);
        background.setArcHeight(15);
        background.setArcWidth(15);
        background.setFill(Color.rgb(1, 2, 3));
        Color foreground = Color.rgb(2,2,255);
        background.setStroke(foreground);
        background.setStrokeWidth(1.5);

        //creating a box that would hold the content of the login stage
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        //creating a label that would display login information
        Label label = new Label("Enter Details");
        label.setTextFill(Color.WHITESMOKE);
        label.setFont(Font.font("SanSerif", FontWeight.EXTRA_BOLD, 20));

        //creating a text-field for the username
        TextField userName = new TextField();
        userName.setFont(Font.font("SanSerif",FontWeight.BOLD, 15));
        userName.setPromptText("User Name");

        //creating a password-field for the password
        PasswordField password = new PasswordField();
        password.setFont(Font.font("SanSerif", 15));
        password.setPromptText("Password");
        password.setOnKeyPressed( e -> {  //setting an click on action command for enter key after password has been keyed in
            if(e.getCode() == KeyCode.ENTER)
            {
                try
                {
                    //execute SQL commands for user authentication
                    String query = "select * from Authorized where UserName =? and Password =?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, userName.getText());
                    ps.setString(2,password.getText());
                    rs = ps.executeQuery();

                    if(rs.next()) {
                        userName.clear();
                        password.clear();
                        primaryStage.close();
                        secondaryStage.setScene(secondScene);
                        secondaryStage.show();

                    }
                    else {
                        label.setText("Login Failed!");
                    }
                    ps.close();
                    rs.close();
                }
                catch(Exception exp){
                    label.setText("SQL error");
                    System.err.println(exp);
                }
            }
        });

        //creates buttons for login and exit
        Button b,b1;
        b = new Button("Login");
        b.setPrefWidth(60);
        b.setStyle("-fx-background-color: linear-gradient(MidnightBlue, black)");
        b.setTextFill(Color.WHITESMOKE);
        b.setFont(Font.font("SanSerif", 15));

        b.setOnAction( e -> { //set an on action command for login button
            try
            {
                //execute SQL commands for user authentication
                String query = "select * from Authorized where UserName =? and Password =?";
                ps = conn.prepareStatement(query);
                ps.setString(1, userName.getText());
                ps.setString(2,password.getText());
                rs = ps.executeQuery();

                if(rs.next()) {
                    userName.clear();
                    password.clear();
                    primaryStage.close();
                    secondaryStage.setScene(secondScene);
                    secondaryStage.show();
                }
                else {
                    label.setText("Login Failed!");
                }
                ps.close();
                rs.close();
            }
            catch(Exception exp){
                label.setText("SQL error");
                System.err.println(exp);
            }
        });

        //creating a button for exiting the platform
        b1 = new Button("Exit");
        b1.setPrefWidth(60);
        b1.setFont(Font.font("SanSerif", 15));
        b1.setStyle("-fx-background-color: linear-gradient(MidnightBlue, black)");
        b1.setTextFill(Color.WHITESMOKE);
        System.out.println();
        b1.setOnAction( e ->
        {
            Platform.exit();
        });

        Color foreground2 = Color.rgb(255,255,255,0.9); //(red, green,blue, transparency)

        //creating a pathlock for the login page
        SVGPath padLock = new SVGPath();
        padLock.setFill(foreground2);
        padLock.setContent("M24.875 15.334v-4.876c0-4.894-3.98-8.875-8.875-8.875s-8.875 3.98-8.875" +
                " 8.875v4.876H5.042v15.083h21.916V15.334h-2.083zm-14.25-4.876c0-2.964 2.41-5.375 " +
                "5.375-5.375s5.375 2.41 5.375 5.375v4.876h-10.75v-4.876zm7.647 16.498h-4.545l1.222-3.667a2.37" +
                " 2.37 0 0 1-1.325-2.12 2.375 2.375 0 1 1 4.75 0c0 .932-.542 1.73-1.324 2.12l1.222 3.666z");

        //creating HBoxes
        HBox row1, row2;
        row1 = new HBox(20);
        row2 = new HBox(20);
        row1.prefWidthProperty().bind(primaryStage.widthProperty().subtract(45));//width of row1  = width of the primary stage - 45
        row1.getChildren().addAll(label, padLock);//add label and padlock into the first horizontal box
        row2.prefWidthProperty().bind(primaryStage.widthProperty().subtract(45)); //width of row2 = width of the primary stage -45
        row2.getChildren().addAll(b, b1);//adds login and exit buttons to the second horizontal box
        vbox.getChildren().addAll(row1, userName, password, row2);//adds row1, username, password, row2 to the vertical box
        root.getChildren().addAll(background,vbox);//adds the rectangle "background" and the vertical box into the group "root"
//this marks the end of the login scene

        //FOR SECOND SCENE

        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

        //Defining buttons
        btn1 = bp.button(200,20,"100 Level");
        btn2 = bp.button(200,20,"200 Level");
        btn3 = bp.button(200,20,"300 Level");
        btn4 = bp.button(200,20,"400 Level");
        btn5 = bp.button(200,20,"500 Level");
        btn6 = bp.button(200,20,"Admin");
        btn7 = bp.button(50,15,"Exit");
        btn8 = bp.button(200,20,"Promote");


        //Assigning actions to the different buttons
        btn3.setOnAction(event ->
        {
            secondaryStage.hide();
            Levels prop = new Levels(secondaryStage);
        prop.level("yearthreestudents");
        });

        btn4.setOnAction(event ->
        {
            secondaryStage.hide();
            Levels prop = new Levels(secondaryStage);
            prop.level("yearfourstudents");
        });

        btn5.setOnAction(event ->
        {
            secondaryStage.hide();
            Levels prop = new Levels(secondaryStage);
            prop.level("yearfivestudents");
        });

        btn6.setOnAction(event ->
        {
            secondaryStage.hide();
            Levels prop = new Levels(secondaryStage);
            prop.authorizedUsers("authorized");
        });
         btn7.setOnAction(e -> Platform.exit());
        btn8.setOnAction(e -> Promote.promote());


        options.getChildren().addAll(btn1,btn2,btn3,btn4, btn5, btn6, btn8, btn7);

        place.setCenter(options);


        primaryStage.setScene(loginScene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
