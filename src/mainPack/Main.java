package mainPack;

import DatabaseTransaction.DBQuery;
import DatabaseTransaction.DBconnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.BoxAndPane;
import models.Tables;
import models.Texts;
import models.Validation;
import reusable.Actions;
import reusable.TableListener;
import storageUnit.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Main extends Application {
    PreparedStatement ps;
    ResultSet rs;
    Connection conn;
    String ID;
    Scene loginScene, secondScene;
    private String gender;
    VBox fields, options;
    //fields and labels setup
    private TextField id, fn, ln, em, ph, on,sc;
    private PasswordField pw;
    private DatePicker dob;
    private RadioButton m,fm;
    private String radioButtonLabel;
    TableListener tl;

    final ObservableList option = FXCollections.observableArrayList();
    Actions ac;


    @Override
    public void start(Stage primaryStage) throws Exception{
        BoxAndPane bp = new BoxAndPane();
        BorderPane place = new BorderPane();
        Tables table = new Tables();
         fields = bp.vbox();
         options = bp.vbox();
        Texts texts = new Texts();
        Stage secondaryStage = new Stage();
        Stage tertiaryStage = new Stage();
        //Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        secondaryStage.setTitle("Attendance Manager");
        conn = DBconnection.Dbconnect(); // checkConnection();
        primaryStage.initStyle(StageStyle.TRANSPARENT); //makes the primary stage transparent
        Group root = new Group(); //creates a container for all contents of scene1
        loginScene = new Scene(root, 280, 200, Color.rgb(0,0,0,0)); //adds the group "root" to the login scene
        secondScene = new Scene(place,280,500);
        secondaryStage.setResizable(false);
        //scene1.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());



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

        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

        //Defining buttons
        btn1 = bp.button(200,20,"100 Level");
        btn2 = bp.button(200,20,"200 Level");
        btn3 = bp.button(200,20,"300 Level");
        btn4 = bp.button(200,20,"400 Level");
        btn5 = bp.button(200,20,"500 Level");
        btn6 = bp.button(200,20,"Lecturers");
        btn7 = bp.button(50,15,"Exit");

        //Assigning actions to the different buttons
        btn3.setOnAction(event ->
        {

            //Stage and scene setup
            BorderPane container = new BorderPane();
            secondaryStage.hide();
            Scene levelThree = new Scene(container,1000,600);
            tertiaryStage.setScene(levelThree);
            tertiaryStage.show();

            //table view and scrollpane setup
            TableView<Students> tb = table.studentsTable(); //creating for students with the required attributes
            ScrollPane sp = bp.scrollpane(600,600, tb); //adding the table in a scrollpane


            sc = texts.textField(200,15, "search");
            Label label1 = texts.label("Enter new Student");
            id = texts.textField(200,15,"ID");
            fn = texts.textField(200,15,"First Name");
            ln = texts.textField(200,15,"Last Name");
            em = texts.textField(200,15,"Email");
            ph = texts.textField(200,15,"Phone Number");
            on = texts.textField(200,15,"Username");
            dob = texts.datePicker(200, "Date of Birth");
            ToggleGroup gender = new ToggleGroup();
            m = texts.radioButton("Male");
            m.setOnAction(male ->
            {
                m.setToggleGroup(gender);
                radioButtonLabel = m.getText();
            });
            fm = texts.radioButton("Female");
            fm.setOnAction(female ->
            {
                fm.setToggleGroup(gender);
            });

            Button save = bp.button(50,10,"Save");
            save.setOnAction(sv ->
            {
                validate();
                DBQuery.query(id, fn, ln, on,em, dob,radioButtonLabel,ph);
                clearFields();
            });
            Button delete = bp.button(50,10,"Delete");
            delete.setOnAction(de ->
            {
                deleteStudent(tb);
                tb.setItems(refreshTable());
            });
            Button load = bp.button(50,10,"Load");
            load.setOnAction( ld -> tb.setItems(refreshTable()));

            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(10,10,10,10));
            hBox.setSpacing(15);
            hBox.getChildren().addAll(save, delete, load);
            fields.getChildren().addAll(sc,label1,id,fn,ln,em,on,ph,dob,m,fm,hBox); //adding the contents of the fields and labels to a box

            container.setLeft(fields); // setting the box at the left position on the border pane
            container.setRight(sp); //setting the scroll pane and table on the right position on the border pane
        });






        options.getChildren().addAll(btn1,btn2,btn3,btn4, btn5, btn6, btn7);

        place.setCenter(options);


        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    Boolean validate ()
    {
        Validation vb = new Validation();
        if(vb.validateID(id) & vb.validateFirstName(fn)&vb.validateLastName(ln)&vb.validateEmail(em)&vb.validatePhoneNumber(ph)& vb.validateField(on,dob,m,fm))
            return true;
        else
            return false;
    }

    public void clearFields()
    {
        id.clear();
        fn.clear();
        ln.clear();
        em.clear();
        ph.clear();
        on.clear();
        dob.setValue(null);
        dob.getEditor().setText(null);
        m.setSelected(false);
        fm.setSelected(false);
    }

    public ObservableList<Students> refreshTable()
    {
        ObservableList<Students> data = FXCollections.observableArrayList();
        try
        {
            conn = DBconnection.Dbconnect();
            data.clear();
            String query = "select * from YearThreeStudents";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                data.add(
                        new Students(
                                rs.getString("RegdNumber"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("OtherName"),
                                rs.getString("Email"),
                                rs.getString("PhoneNumber"),
                                rs.getString("Gender"))
                );
            }
            ps.close();
            rs.close();
        }catch(Exception exce)
        {
            System.err.println(exce);
        }
        return data;
    }
    public void deleteStudent (TableView<Students> table)
    {
        table.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN)
            {
                try {
                    Students students = (Students) table.getSelectionModel().getSelectedItem();
                    String query = "select * from YearThreeStudents where RegdNumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        ID = rs.getString("RegdNumber");

                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get() == ButtonType.OK) {
                        try {
                            String q = "delete from YearThreeStudents where RegdNumber = ?";
                            ps = conn.prepareStatement(q);
                            ps.setString(1, ID);
                            ps.executeUpdate();
                        } catch (SQLException exc) {
                            System.err.println(exc);
                        }
                    }
                    ps.close();
                    rs.close();
                }
                catch(SQLException sqlException)
                {
                    System.out.println("Exception: "+ sqlException);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
