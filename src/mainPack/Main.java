package mainPack;

import DatabaseTransaction.DBQuery;
import DatabaseTransaction.DBconnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import storageUnit.Students;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

public class Main extends Application {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn;
    String ID;
    Scene loginScene, secondScene;
    private String gender;
    private VBox fields, options;
    //fields and labels setup
    private TextField id, fn, ln, em, ph, on,sc, pc;
    private PasswordField pw;
    private DatePicker dob;
    private RadioButton m,fm;

    TextField id1, fn1, ln1, em1, ph1, on1;
    DatePicker dob1;
    RadioButton m1,fm1;
    Button save1, back;

    private String radioButtonLabel;
    private Label label2;
    private MenuBar menuBar;
    private Menu fileMenu, subMenu, toolBar, viewMenu, mode;
    private MenuItem item1, exitMenu;
    private CheckMenuItem firstSem,secondSem,check1,check2;
    private RadioMenuItem mode1, mode2;
    private ToggleGroup tGroup;
    final ObservableList data = FXCollections.observableArrayList();
    TableView<Students> tb;


    @Override
    public void start(Stage primaryStage) throws Exception{
        BoxAndPane bp = new BoxAndPane();
        fields = bp.vbox();
        options = bp.vbox();
        BorderPane place = new BorderPane();
        Tables table = new Tables();
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
            Scene levelThree = new Scene(container,1200,700);
            tertiaryStage.setScene(levelThree);
            tertiaryStage.show();
            MenuBar menuBar = menu();

            //table view and scrollpane setup
            tb = table.studentsTable(); //creating for students with the required attributes
            ScrollPane sp = bp.scrollpane(700,600, tb); //adding the table in a scrollpane


            sc = texts.textField(200,15, "search");
            Label label1 = texts.label("Enter new Student");
            id = texts.textField(200,15,"ID");
            fn = texts.textField(200,15,"First Name");
            ln = texts.textField(200,15,"Last Name");
            on = texts.textField(200,15,"Other Name");
            em = texts.textField(200,15,"Email");
            ph = texts.textField(200,15,"Phone Number");
            dob = texts.datePicker(200, "Date of Birth");
            pc = texts.textField(200,15,"Registration Number");
            pc.setEditable(false);
            ToggleGroup gender = new ToggleGroup();
            m = texts.radioButton("Male");
            refreshTable();
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

            label2 = texts.label("Edit Student Details");
            id1 = texts.textField(200,15,"ID");
            fn1 = texts.textField(200,15,"First Name");
            ln1 = texts.textField(200,15,"Last Name");
            on1 = texts.textField(200,15,"Other Name");
            em1 = texts.textField(200,15,"Email");
            ph1 = texts.textField(200,15,"Phone Number");
            dob1 = texts.datePicker(200, "Date of Birth");
            ToggleGroup gender1 = new ToggleGroup();
            m1 = texts.radioButton("Male");
            refreshTable();
            m1.setOnAction(male ->
            {
                m1.setToggleGroup(gender1);
                radioButtonLabel = m1.getText();
            });
            fm1 = texts.radioButton("Female");
            fm1.setOnAction(female ->
            {
                fm1.setToggleGroup(gender1);
            });


            tableMove();
            tableClick();

            Button save = bp.button(50,10,"Save");
            save.setOnAction(sv ->
            {
               if(validate()) {
                   DBQuery.query(id, fn, ln, on, em, dob, radioButtonLabel, ph);
                   clearFields();
                   refreshTable();
               }
            });
            Button delete = bp.button(50,10,"Delete");
            delete.setOnAction(de ->
            {
                delete();
            });
            Button load = bp.button(50,10,"Edit");
            edit();
            load.setOnAction( ld -> {

                editWindow();
            });
            tableFilter();

            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(10,10,10,10));
            hBox.setSpacing(15);
            hBox.getChildren().addAll(save, delete, load);

            HBox tableBox = new HBox(10);
            tableBox.setPadding(new Insets (10,200,10,10));
            tableBox.setAlignment(Pos.CENTER_RIGHT);
            tableBox.getChildren().add(pc);
            fields.getChildren().addAll(sc,label1,id,fn,ln,on,em,ph,dob,m,fm,hBox); //adding the contents of the fields and labels to a box

            container.setLeft(fields); // setting the box at the left position on the border pane
            container.setRight(sp); //setting the scroll pane and table on the right position on the border pane
            container.setBottom(tableBox);
            container.setTop(menuBar);
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
    Boolean validate2 ()
    {
        Validation vb = new Validation();
        if(vb.validateID(id1) & vb.validateFirstName(fn1)&vb.validateLastName(ln1)&vb.validateEmail(em1)&vb.validatePhoneNumber(ph1)& vb.validateField(on1,dob1,m1,fm1))
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
        sc.clear();
        dob.setValue(null);
        dob.getEditor().setText(null);
        m.setSelected(false);
        fm.setSelected(false);
    }

    public void refreshTable()
    {
        try
        {
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
                                rs.getString("Othername"),
                                rs.getString("Email"),
                                rs.getString("PhoneNumber"),
                                rs.getString("Gender"))
                );
                tb.setItems(data);

            }
            ps.close();
            rs.close();
        }catch(Exception exce)
        {
            System.err.println(exce);
        }
    }
    void tableFilter()
    {
        FilteredList<Students> filteredData = new FilteredList<>(data, e -> true);
        sc.setOnKeyReleased( e-> {
            sc.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super  Students>) students -> {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }
                    String lowercaseFilter = newValue.toLowerCase();
                    if(students.getID().contains(newValue))
                    {
                        return true;
                    }
                    else if(students.getFirstName().toLowerCase().contains(lowercaseFilter))
                    {
                        return true;
                    }
                    else if(students.getLastName().toLowerCase().contains(lowercaseFilter))
                    {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<Students> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tb.comparatorProperty());
            tb.setItems(sortedData);
        });
    }
    public void tableMove ()
    {
        tb.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT)
            {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from yearthreestudents where RegdNumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while(rs.next())
                    {
                        pc.setText(rs.getString("RegdNumber"));

                    }
                    ps.close();
                    rs.close();
                }
                catch(SQLException excc)
                {
                    System.out.print(excc);
                }
            }
        });
    }
    void tableClick()
    {
        tb.setOnMouseClicked(e ->
        {
            try {
                Students students = (Students) tb.getSelectionModel().getSelectedItem();
                clearFields();
                String query = "select * from yearthreestudents where RegdNumber = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, students.getID());
                rs = ps.executeQuery();

                while (rs.next()) {
                    pc.setText(rs.getString("RegdNumber"));
                }
                ps.close();
                rs.close();
            } catch (SQLException excc) {
                System.out.print(excc);
            }
        });
    }

    void delete()
    {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                try {
                    String query = "delete from yearthreestudents where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, pc.getText());
                    ps.executeUpdate();

                    ps.close();
                } catch (SQLException exc) {
                    System.err.println(exc);
                }
            }
            clearFields();
            refreshTable();
    }
    void edit()
    {
        tb.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN)
            {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    String query = "select * from yearthreestudents where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while(rs.next())
                    {
                        pc.setText(rs.getString("RegdNumber"));
                        id1.setText(rs.getString("RegdNumber"));
                        fn1.setText(rs.getString("FirstName"));
                        ln1.setText(rs.getString("LastName"));
                        on1.setText(rs.getString("OtherName"));
                        ph1.setText(rs.getString("PhoneNumber"));
                        em1.setText(rs.getString("Email"));
                        ((TextField)dob1.getEditor()).setText(rs.getString("DOB"));

                        if( null != rs.getString("Gender"))
                            switch(rs.getString("Gender"))
                            {
                                case "Male": m1.setSelected(true);
                                    break;
                                case "Female": fm1.setSelected(true);
                                    break;
                            }
                        else
                        {
                            m.setSelected(false);
                            fm.setSelected(false);
                        }
                    }
                    ps.close();
                    rs.close();
                }
                catch(SQLException excc)
                {
                    System.out.print(excc);
                }
            }
        });

        tb.setOnMouseClicked(e ->
        {
            try {
                Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    String query = "select * from yearthreestudents where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while(rs.next())
                    {
                        id1.setText(rs.getString("RegdNumber"));
                        fn1.setText(rs.getString("FirstName"));
                        ln1.setText(rs.getString("LastName"));
                        on1.setText(rs.getString("OtherName"));
                        ph1.setText(rs.getString("PhoneNumber"));
                        em1.setText(rs.getString("Email"));
                        ((TextField)dob1.getEditor()).setText(rs.getString("DOB"));

                        if( null != rs.getString("Gender"))
                            switch(rs.getString("Gender"))
                            {
                                case "Male": m1.setSelected(true);
                                    break;
                                case "Female": fm1.setSelected(true);
                                    break;
                            }
                        else
                        {
                            m1.setSelected(false);
                            fm1.setSelected(false);
                        }
                    }
                    ps.close();
                    rs.close();
                }
                catch(SQLException excc)
                {
                    System.out.print(excc);
                }
        });
    }

    public MenuBar menu() {
         menuBar =new MenuBar();

        fileMenu = new Menu("File");
        subMenu = new Menu("Open Courses");
        item1 = new MenuItem("Edit Students...");
        item1.setOnAction(e -> editWindow());


        firstSem = new CheckMenuItem("First Semester");
        secondSem = new CheckMenuItem("First Semester");
        exitMenu = new MenuItem("Exit");
        subMenu.getItems().addAll(firstSem, secondSem, exitMenu);


        exitMenu.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(item1, subMenu, new SeparatorMenuItem(), exitMenu);
        //add view menu
        viewMenu = new Menu("View");
        check1 = new CheckMenuItem("Editor");
        check1.setSelected(true);
        check2 = new CheckMenuItem("Line Number");
        check2.setSelected(true);
        toolBar = new Menu("Toolbar");
        toolBar.getItems().addAll(new CheckMenuItem ("File"),
                new CheckMenuItem("Run"),
                new CheckMenuItem("Debug"));
        viewMenu.getItems().addAll(check1, check2, new SeparatorMenuItem(),toolBar);

        //mode menu
        mode = new Menu("Mode");
        tGroup = new ToggleGroup();
        mode1 = new RadioMenuItem("Desktop");
        mode1.setToggleGroup(tGroup);
        mode2 = new RadioMenuItem("Tablet");
        mode2.setToggleGroup(tGroup);
        mode2.setSelected(true);
        mode.getItems().addAll(mode1,mode2);
        menuBar.getMenus().addAll(fileMenu,viewMenu,mode);

        return menuBar;
    }

    void editWindow()
    {

        Stage window = new Stage();
        BoxAndPane bnp = new BoxAndPane();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setWidth(300);
        window.setHeight(550);

        save1 = bnp.button(50,10,"Update");
        save1.setOnAction(sv ->
        {
            if(validate2()) {
                DBQuery.updateQuery(id1, fn1, ln1, on1, em1, dob1, radioButtonLabel, ph1);
                clearFields();
                refreshTable();
                window.close();
            }
        });
        back = bnp.button(50,10,"Back");
        back.setOnAction( ld -> window.close());

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(15);
        hBox.getChildren().addAll(save1, back);


        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setSpacing(20);
        layout.getChildren().addAll(label2,id1,fn1, ln1, on1, em1, ph1, dob1, m1, fm1, hBox);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
