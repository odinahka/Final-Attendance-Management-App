package scenes;


/**
 * Created by odinahka on 10/31/2019.
 */
import DatabaseTransaction.DBQuery;
import DatabaseTransaction.DBconnection;
import Interfaces.SerialCommlListener;
import communication.Communication;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import storageUnit.AuthorizedUsers;
import storageUnit.Students;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

public class Levels{
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn;
    Scene  secondScene;
    private String gender;
    private VBox fields, options;
    //fields and labels setup
    private TextField id, fn, ln, em, ph, on,sc, pc, pw;
   private static Label flabel;
    public static int fingerprint = 0;
    public static int [] daata;
    private DatePicker dob;
    private RadioButton m,fm;

    TextField id1, fn1, ln1, em1, ph1, on1, pw1;
    DatePicker dob1;
    RadioButton m1,fm1;
    Button save1, back;

    private String radioButtonLabel, radioBL;
    private Label label2;
    private MenuBar menuBar;
    private Menu manage, openCourses,firstSem,secondSem;
    private MenuItem exitMenu,editStudents, backMenu;
    final ObservableList data = FXCollections.observableArrayList();
    TableView<Students> tb;
    TableView<AuthorizedUsers> tb1;
    Stage second, tertiaryStage;
    BoxAndPane bp;
    BorderPane place;
    Tables table;
    Texts texts;
    Communication comm;


    public Levels(Stage secondaryStage)
    {
        second = secondaryStage;
        second.getIcons().add(new Image ("file:Unizik.png"));
       second.setTitle("Attendance Manager");
        bp = new BoxAndPane();
        fields = bp.vbox();
        options = bp.vbox();
        place = new BorderPane();
        table = new Tables();
        texts = new Texts();
        comm = new Communication();
        tertiaryStage = new Stage();
        flabel = new Label();
        tertiaryStage.getIcons().add(new Image("file:Unizik.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        conn = DBconnection.Dbconnect(); // checkConnection();
        secondScene = new Scene(place,280,500);
        //scene1.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
    }
    public static void captureCallBack(int ID)
    {
       fingerprint = ID;
        if(fingerprint <= 0)
            flabel.setText("Not Captured");
        if(fingerprint > 0)
            flabel.setText("Captured");
    }
    public static void updateAttendance(String data, String tableName, ILevels levels)
    {
        System.out.println(data);
     String [] dataArray = data.split("#");
        System.out.println(dataArray.length);
         daata = new int[dataArray.length];
        for(int i = 0; i < dataArray.length; i++)
        {
            daata[i] = Integer.parseInt(dataArray[i]);

        }
        for(int i: daata)
        System.out.println(i);
        levels.UpdateTable(tableName);
    }


    public void level(String sTable)
    {
        if(sTable.compareToIgnoreCase("yearthreestudents")==0)
            tertiaryStage.setTitle("Attendance Manager - 300 level");
        if(sTable.compareToIgnoreCase("yearfourstudents")==0)
            tertiaryStage.setTitle("Attendance Manager - 400 level");
        if(sTable.compareToIgnoreCase("yearfivestudents")==0)
            tertiaryStage.setTitle("Attendance Manager - 500 level");
        //Stage and scene setup
        BorderPane container = new BorderPane();
        VBox capture = new VBox();
        capture.setSpacing(30);
        capture.setFillWidth(true);
        flabel.setFont(Font.font("SanSerif", FontWeight.BOLD, 20));
        Button btn = new Button("Capture Fingerprint");
        btn.setOnAction(e ->{
            flabel.setText("");
            registerPrint(sTable);
        });

        btn.setStyle("-fx-background-color: MidnightBlue");
        btn.setTextFill(Color.WHITESMOKE);
        capture.getChildren().addAll(btn, flabel);
        Scene levelThree = new Scene(container,1000,570);
        tertiaryStage.setScene(levelThree);
        levelThree.getStylesheets().add(getClass().getResource("levels.css").toExternalForm());
        tertiaryStage.show();
        MenuBar menuBar = menu(sTable);

        //table view and scrollpane setup
        tb = table.studentsTable(); //creating for students with the required attributes
        ScrollPane sp = bp.scrollpane(600,400, tb); //adding the table in a scrollpane


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
        refreshTable(sTable);
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
        refreshTable(sTable);
        m1.setOnAction(male ->
        {
            m1.setToggleGroup(gender1);
            radioBL = m1.getText();
        });
        fm1 = texts.radioButton("Female");
        fm1.setOnAction(female ->
        {
            fm1.setToggleGroup(gender1);
            radioBL = fm1.getText();
        });


        tableMove(sTable);
        tableClick(sTable);

        Button save = bp.button(50,10,"Save");
        save.setOnAction(sv ->
        {
            if(validate()) {
                DBQuery.query(sTable,id, fn, on, ln, em, dob, radioButtonLabel, ph, fingerprint);
                clearFields();
                refreshTable(sTable);
            }
        });
        Button delete = bp.button(50,10,"Delete");
        delete.setOnAction(de ->
        {
            delete(sTable);
        });
        Button load = bp.button(50,10,"Edit");
        edit(sTable);
        load.setOnAction( ld -> {

            editWindow(sTable);
        });
        tableFilter(sTable);

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(15);
        hBox.getChildren().add(save);

        HBox tableBox = new HBox(10);
        tableBox.setPadding(new Insets (10,200,10,10));
        tableBox.setAlignment(Pos.CENTER_RIGHT);
        tableBox.getChildren().addAll(load,delete,pc);
        fields.getChildren().addAll(sc,label1,id,fn,on,ln,em,ph,dob,m,fm,hBox); //adding the contents of the fields and labels to a box

        container.setLeft(fields); // setting the box at the left position on the border pane
        container.setRight(sp); //setting the scroll pane and table on the right position on the border pane
        container.setBottom(tableBox);
        capture.setAlignment(Pos.TOP_CENTER);
        container.setCenter(capture);
        container.setTop(menuBar);
    }
    public void authorizedUsers(String uTable)
    {
            //Stage and scene setup
            tertiaryStage.setTitle("Attendance Manager - Administrators");
            BorderPane container = new BorderPane();
            Scene levelThree = new Scene(container,1200,700);
            levelThree.getStylesheets().add(getClass().getResource("levels.css").toExternalForm());
            tertiaryStage.setScene(levelThree);
            tertiaryStage.show();

            //table view and scrollpane setup
            tb1 = table.authorizedUsersTable(); //creating for students with the required attributes
            ScrollPane sp = bp.scrollpane(700,600, tb1); //adding the table in a scrollpane


            sc = texts.textField(200,15, "search");
            Label label1 = texts.label("Enter new Administrator");
            id = texts.textField(200,15,"ID");
            fn = texts.textField(200,15,"First Name");
            ln = texts.textField(200,15,"Last Name");
            on = texts.textField(200,15,"Username");
            pw = texts.textField(200,15,"Password");
            em = texts.textField(200,15,"Email");
            ph = texts.textField(200,15,"Phone Number");
            pc = texts.textField(200,15,"Registration Number");
            pc.setEditable(false);
            ToggleGroup gender = new ToggleGroup();
            m = texts.radioButton("Male");
            refreshTable(uTable);
            m.setOnAction(male ->
            {
                m.setToggleGroup(gender);
                radioButtonLabel = m.getText();
            });
            fm = texts.radioButton("Female");
            fm.setOnAction(female ->
            {
                fm.setToggleGroup(gender);
                radioBL = fm.getText();
            });

            label2 = texts.label("Edit Administrator's Details");
            id1 = texts.textField(200,15,"ID");
            fn1 = texts.textField(200,15,"First Name");
            ln1 = texts.textField(200,15,"Last Name");
            on1 = texts.textField(200,15,"Username");
            pw1 = texts.textField(200,15,"Password");
            em1 = texts.textField(200,15,"Email");
            ph1 = texts.textField(200,15,"Phone Number");

            ToggleGroup gender1 = new ToggleGroup();
            m1 = texts.radioButton("Male");
            refreshTable(uTable);
            m1.setOnAction(male ->
            {
                m1.setToggleGroup(gender1);
                radioBL = m1.getText();
            });
            fm1 = texts.radioButton("Female");
            fm1.setOnAction(female ->
            {
                fm1.setToggleGroup(gender1);
                radioBL = fm1.getText();
            });


            tableMove(uTable);
            tableClick(uTable);

            Button save = bp.button(50,10,"Save");
            save.setOnAction(sv ->
            {
                if(validate1()) {
                    DBQuery.query2(uTable,id, fn, ln, on, pw, em,ph, radioButtonLabel);
                    clearFields2();
                    refreshTable(uTable);
                }
            });
            Button back = bp.button(50,10,"Back");
            back.setOnAction(e ->
            {
                tertiaryStage.close();
                second.show();
            });
            Button delete = bp.button(50,10,"Delete");
            delete.setOnAction(de ->
            {
                delete(uTable);
            });
            Button load = bp.button(50,10,"Edit");
            edit(uTable);
            load.setOnAction( ld -> {

                editWindow(uTable);
            });
            tableFilter(uTable);

            VBox vBox = new VBox(10);
            vBox.setPadding(new Insets(10,10,10,10));
            vBox.setSpacing(15);
            vBox.getChildren().addAll(save,back);

            HBox tableBox = new HBox(10);
            tableBox.setPadding(new Insets (10,200,10,10));
            tableBox.setAlignment(Pos.CENTER_RIGHT);
            tableBox.getChildren().addAll(load,delete);
            fields.getChildren().addAll(sc,label1,id,fn,ln,on,pw,em,ph,m,fm,vBox); //adding the contents of the fields and labels to a box

            container.setLeft(fields); // setting the box at the left position on the border pane
            container.setRight(sp); //setting the scroll pane and table on the right position on the border pane
            container.setBottom(tableBox);
        }


    Boolean validate ()
    {
        Validation vb = new Validation();
        if(vb.validateID(id) & vb.validateFirstName(fn)&vb.validateLastName(ln)&vb.validateEmail(em)&vb.validatePhoneNumber(ph)& vb.validateField(on,dob,m,fm))
            return true;
        else
            return false;
    }
    Boolean validate1()
    {
        Validation vb = new Validation();
        if(vb.validateID(id) & vb.validateFirstName(fn)&vb.validateLastName(ln)&vb.validateEmail(em)&vb.validatePhoneNumber(ph)& vb.validateField(on,m,fm))
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
    Boolean validate3 ()
    {
        Validation vb = new Validation();
        if(vb.validateID(id1) & vb.validateFirstName(fn1)&vb.validateLastName(ln1)&vb.validateEmail(em1)&vb.validatePhoneNumber(ph1)& vb.validateField(on1,m1,fm1))
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
        flabel.setText("");
        dob.setValue(null);
        dob.getEditor().setText(null);
        m.setSelected(false);
        fm.setSelected(false);
        pc.clear();
        id1.clear();
        fn1.clear();
        ln1.clear();
        on1.clear();
        ph1.clear();
        em1.clear();

        dob1.setValue(null);
        dob1.getEditor().setText(null);
        m1.setSelected(false);
        fm1.setSelected(false);

    }

    public void clearFields2()
    {
        id.clear();
        fn.clear();
        ln.clear();
        em.clear();
        ph.clear();
        on.clear();
        sc.clear();
        pw.clear();
        flabel.setText("");
        m.setSelected(false);
        fm.setSelected(false);
        pc.clear();
        id1.clear();
        fn1.clear();
        ln1.clear();
        on1.clear();
        pw1.clear();
        ph1.clear();
        em1.clear();
        m1.setSelected(false);
        fm1.setSelected(false);

    }

    public void refreshTable(String table)
    {
        try
        {
            data.clear();
            if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
                String query = "select * from " + table;
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    data.add(
                            new Students(
                                    rs.getString("RegdNumber"),
                                    rs.getString("FirstName"),
                                    rs.getString("otherName"),
                                    rs.getString("LastName"),
                                    rs.getString("Email"),
                                    rs.getString("PhoneNumber"),
                                    rs.getString("Gender"))
                    );
                    tb.setItems(data);
                }
                ps.close();
                rs.close();
            }
            else
            {
                String query = "select * from " + table;
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    data.add(
                            new AuthorizedUsers(
                                    rs.getString("ID"),
                                    rs.getString("FirstName"),
                                    rs.getString("LastName"),
                                    rs.getString("email"),
                                    rs.getString("PhoneNumber"),
                                    rs.getString("username"),
                                    rs.getString("Password"),
                                    rs.getString("Gender"))
                    );
                    tb1.setItems(data);
                }
                ps.close();
                rs.close();
            }
        }catch(Exception exce)
        {
            System.err.println(exce);
        }
    }
    void tableFilter(String table) {
        if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
            FilteredList<Students> filteredData = new FilteredList<>(data, e -> true);
            sc.setOnKeyReleased(e -> {
                sc.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super Students>) students -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowercaseFilter = newValue.toLowerCase();
                        if (students.getID().contains(newValue)) {
                            return true;
                        } else if (students.getFirstName().toLowerCase().contains(lowercaseFilter)) {
                            return true;
                        } else if (students.getLastName().toLowerCase().contains(lowercaseFilter)) {
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
        else{
            FilteredList<AuthorizedUsers> filteredData = new FilteredList<>(data, e -> true);
            sc.setOnKeyReleased(e -> {
                sc.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super AuthorizedUsers>) user -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowercaseFilter = newValue.toLowerCase();
                        if (user.getID().contains(newValue)) {
                            return true;
                        } else if (user.getFirstName().toLowerCase().contains(lowercaseFilter)) {
                            return true;
                        } else if (user.getLastName().toLowerCase().contains(lowercaseFilter)) {
                            return true;
                        }

                        return false;
                    });
                });

                SortedList<AuthorizedUsers> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tb1.comparatorProperty());
                tb1.setItems(sortedData);
            });
        }
        }

    public void tableMove (String table) {

        if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
            tb.setOnMouseClicked(e -> {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from " + table + " where RegdNumber = ?";
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
            tb.setOnKeyReleased(e ->
            {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT) {
                    try {
                        Students students = (Students) tb.getSelectionModel().getSelectedItem();
                        clearFields();
                        String query = "select * from " + table + " where RegdNumber = ?";
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
                }
            });
        } else {
            tb1.setOnMouseClicked(e -> {
                try {
                    AuthorizedUsers user = (AuthorizedUsers) tb1.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from " + table + " where ID = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, user.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        pc.setText(rs.getString("ID"));

                    }
                    ps.close();
                    rs.close();
                } catch (SQLException excc) {
                    System.out.print(excc);
                }
            });
            tb1.setOnKeyReleased(e ->
            {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT) {
                    try {
                        AuthorizedUsers user = (AuthorizedUsers) tb1.getSelectionModel().getSelectedItem();
                        clearFields();
                        String query = "select * from " + table + " where ID = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, user.getID());
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            pc.setText(rs.getString("ID"));

                        }
                        ps.close();
                        rs.close();
                    } catch (SQLException excc) {
                        System.out.print(excc);
                    }
                }
            });
        }
    }
    void tableClick(String table) {
        if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
            tb.setOnMouseClicked(e ->
            {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from " + table + " where RegdNumber = ?";
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
        } else {
            tb1.setOnMouseClicked(e ->
            {
                try {
                    AuthorizedUsers user = (AuthorizedUsers) tb1.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from " + table + " where ID = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, user.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        pc.setText(rs.getString("ID"));
                    }
                    ps.close();
                    rs.close();
                } catch (SQLException excc) {
                    System.out.print(excc);
                }
            });
        }
    }
    void delete(String table) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            if (table.compareToIgnoreCase("yearthreestudents") == 0) {
                try {
                    String query = "delete from " + table + " where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, pc.getText());
                    ps.executeUpdate();

                    for (int i = 1; i <= 19; i++) {
                        String course = "";
                        if (i == 1)
                            course = "CircuitTheoryII";
                        if (i == 2)
                            course = "ElectromagneticFieldsAndWaves";
                        if (i == 3)
                            course = "ElectromechDevicesAndMachinesI";
                        if (i == 4)
                            course = "PowerSystems";
                        if (i == 5)
                            course = "TelecommunicationI";
                        if (i == 6)
                            course = "ElectronicDevicesAndCircuitI";
                        if (i == 7)
                            course = "SignalAnalysisAndSystems";
                        if (i == 8)
                            course = "DigitalSystemDesignI";
                        if (i == 9)
                            course = "EngineeringMathematicsIII";
                        if (i == 10)
                            course = "CircuitTheoryIII";
                        if (i == 11)
                            course = "Electrodynamics";
                        if (i == 12)
                            course = "ElectromechDevicesAndMachinesII";
                        if (i == 13)
                            course = "InstrumentationAndMeasurementI";
                        if (i == 14)
                            course = "FeedbackAndControlSystem";
                        if (i == 15)
                            course = "TelecommunicationII";
                        if (i == 16)
                            course = "ElectronicDevicesAndCircuitII";
                        if (i == 17)
                            course = "PowerElectronics";
                        if (i == 18)
                            course = "DigitalSystemDesignII";

                        String query2 = "delete from " + course + " where regdnumber = ?";
                        ps = conn.prepareStatement(query2);
                        ps.setString(1, pc.getText());
                        ps.executeUpdate();
                    }

                    ps.close();
                } catch (SQLException exc) {
                    System.err.println(exc);
                }
                clearFields();
            }

            if (table.compareToIgnoreCase("yearfourstudents") == 0) {
                try {
                    String query = "delete from " + table + " where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, pc.getText());
                    ps.executeUpdate();

                    for (int i = 1; i <= 9; i++) {
                        String course = "";
                        if (i == 1)
                            course = "EngineeringMathematicsIV";
                        if (i == 2)
                            course = "EngineeringContractsAndSpecification";
                        if (i == 3)
                            course = "CircuitTheoryIV";
                        if (i == 4)
                            course = "InstrumentationAndMeasurementII";
                        if (i == 5)
                            course = "MicroprocessorsAndComputers";
                        if (i == 6)
                            course = "AssemblyLanguageProgramming";
                        if (i == 7)
                            course = "AdvancedCircuitTechniques";
                        if (i == 8)
                            course = "FundamentalOfDigitalCommunications";

                        String query2 = "delete from " + course + " where regdnumber = ?";
                        ps = conn.prepareStatement(query2);
                        ps.setString(1, pc.getText());
                        ps.executeUpdate();
                    }

                    ps.close();
                } catch (SQLException exc) {
                    System.err.println(exc);
                }
                clearFields();
            }

            if (table.compareToIgnoreCase("yearfivestudents") == 0) {
                try {
                    String query = "delete from " + table + " where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, pc.getText());
                    ps.executeUpdate();

                    for (int i = 1; i <= 16; i++) {
                        String course = "";
                        if (i == 1)
                            course = "ComputerAidedDesign";
                        if (i == 2)
                            course = "RealtimeComputingAndControl";
                        if (i == 3)
                            course = "SolidStateElectronics";
                        if (i == 4)
                            course = "DigitalSignalProcessing";
                        if (i == 5)
                            course = "ArtificialIntelligenceAndRobotics";
                        if (i == 6)
                            course = "WirelessCommunicationAndNetwork";
                        if (i == 7)
                            course = "SystemProgramming";
                        if (i == 8)
                            course = "SoftwareEngineering";
                        if (i == 9)
                            course = "DatabaseManagementSystems";
                        if (i == 10)
                            course = "DataCommuniationNetwork";
                        if (i == 11)
                            course = "NetworkAnalysisAndSynthesis";
                        if (i == 12)
                            course = "ControlSystemEngineering";
                        if (i == 13)
                            course = "Microwave Engineering";
                        if (i == 14)
                            course = "TVSatelliteAndRadar";
                        if (i == 15)
                            course = "OpticFibreCommunication";

                        String query2 = "delete from " + course + " where regdnumber = ?";
                        ps = conn.prepareStatement(query2);
                        ps.setString(1, pc.getText());
                        ps.executeUpdate();
                    }

                    ps.close();
                } catch (SQLException exc) {
                    System.err.println(exc);
                }
                clearFields();
            }
            if (table.compareToIgnoreCase("authorized") == 0) {
                try {
                    String query = "delete from " + table + " where ID = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, pc.getText());
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException exc) {
                    System.err.println(exc);
                }
                clearFields2();
            }

            refreshTable(table);
        }
    }
    void edit(String table) {
        if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
            tb.setOnKeyReleased(e ->
            {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                    try {

                        Students students = (Students) tb.getSelectionModel().getSelectedItem();
                        String query = "select * from " + table + " where regdnumber = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, students.getID());
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            pc.setText(rs.getString("RegdNumber"));
                            id1.setText(rs.getString("RegdNumber"));
                            fn1.setText(rs.getString("FirstName"));
                            ln1.setText(rs.getString("LastName"));
                            on1.setText(rs.getString("OtherName"));
                            ph1.setText(rs.getString("PhoneNumber"));
                            em1.setText(rs.getString("Email"));
                            ((TextField) dob1.getEditor()).setText(rs.getString("DOB"));

                            if (null != rs.getString("Gender"))
                                switch (rs.getString("Gender")) {
                                    case "Male":
                                        m1.setSelected(true);
                                        break;
                                    case "Female":
                                        fm1.setSelected(true);
                                        break;
                                }
                            else {
                                m.setSelected(false);
                                fm.setSelected(false);
                            }
                        }
                        ps.close();
                        rs.close();

                    } catch (SQLException excc) {
                        System.out.print(excc);
                    }
                }

            });

            tb.setOnMouseClicked(e ->
            {
                try {

                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    String query = "select * from " + table + " where regdnumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        pc.setText(rs.getString("RegdNumber"));
                        id1.setText(rs.getString("RegdNumber"));
                        fn1.setText(rs.getString("FirstName"));
                        ln1.setText(rs.getString("LastName"));
                        on1.setText(rs.getString("OtherName"));
                        ph1.setText(rs.getString("PhoneNumber"));
                        em1.setText(rs.getString("Email"));
                        ((TextField) dob1.getEditor()).setText(rs.getString("DOB"));

                        if (null != rs.getString("Gender"))
                            switch (rs.getString("Gender")) {
                                case "Male":
                                    m1.setSelected(true);
                                    break;
                                case "Female":
                                    fm1.setSelected(true);
                                    break;
                            }
                        else {
                            m.setSelected(false);
                            fm.setSelected(false);
                        }
                    }
                    ps.close();
                    rs.close();

                } catch (SQLException excc) {
                    System.out.print(excc);
                }

            });
        } else {
            tb1.setOnMouseClicked(e ->
            {
                try {
                    AuthorizedUsers user = (AuthorizedUsers) tb1.getSelectionModel().getSelectedItem();
                    String query = "select * from " + table + " where ID = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, user.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        pc.setText(rs.getString("ID"));
                        id1.setText(rs.getString("ID"));
                        fn1.setText(rs.getString("FirstName"));
                        ln1.setText(rs.getString("LastName"));
                        on1.setText(rs.getString("UserName"));
                        pw1.setText(rs.getString("Password"));
                        ph1.setText(rs.getString("PhoneNumber"));
                        em1.setText(rs.getString("Email"));

                        if (null != rs.getString("Gender"))
                            switch (rs.getString("Gender")) {
                                case "Male":
                                    m1.setSelected(true);
                                    break;
                                case "Female":
                                    fm1.setSelected(true);
                                    break;
                            }
                        else {
                            m.setSelected(false);
                            fm.setSelected(false);
                        }
                    }
                    ps.close();
                    rs.close();
                } catch (SQLException excc) {
                    System.out.print(excc);
                }
            });

            tb1.setOnKeyReleased(e ->
            {
                if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                    try {
                        AuthorizedUsers user = (AuthorizedUsers) tb1.getSelectionModel().getSelectedItem();
                        String query = "select * from " + table + " where ID = ?";
                        ps = conn.prepareStatement(query);
                        ps.setString(1, user.getID());
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            pc.setText(rs.getString("ID"));
                            id1.setText(rs.getString("ID"));
                            fn1.setText(rs.getString("FirstName"));
                            ln1.setText(rs.getString("LastName"));
                            on1.setText(rs.getString("UserName"));
                            pw1.setText(rs.getString("Password"));
                            ph1.setText(rs.getString("PhoneNumber"));
                            em1.setText(rs.getString("Email"));

                            if (null != rs.getString("Gender"))
                                switch (rs.getString("Gender")) {
                                    case "Male":
                                        m1.setSelected(true);
                                        break;
                                    case "Female":
                                        fm1.setSelected(true);
                                        break;
                                }
                            else {
                                m.setSelected(false);
                                fm.setSelected(false);
                            }
                        }
                        ps.close();
                        rs.close();
                    } catch (SQLException excc) {
                        System.out.print(excc);
                    }
                }

            });
        }
    }

    public MenuBar menu(String table) {


        menuBar =new MenuBar();


        manage = new Menu("Manage");
        openCourses = new Menu("Open Courses");
        editStudents = new MenuItem("Edit Students...");
        editStudents.setOnAction(e -> editWindow(table));


        firstSem = new Menu("First Semester");
        secondSem = new Menu("Second Semester");
        firstSem.setOnAction(e ->
        {
            if(table.compareToIgnoreCase("yearthreestudents") == 0) {
                YearThreeCourses ytc = new YearThreeCourses();
                ytc.firstSemester();
            }
            else if(table.compareToIgnoreCase("yearfourstudents") == 0) {
                YearFourCourses ytc = new YearFourCourses();
                ytc.firstSemester();
            }
            else {
                YearFiveCourses ytc = new YearFiveCourses();
                ytc.firstSemester();
            }
        });
        secondSem.setOnAction(e ->
        {
            if(table.compareToIgnoreCase("yearthreestudents") == 0) {
                YearThreeCourses ytc = new YearThreeCourses();
                ytc.secondSemester();
            }
            else if(table.compareToIgnoreCase("yearfivestudents") == 0) {
                YearFiveCourses ytc = new YearFiveCourses();
                ytc.secondSemester();
            }
        });

        exitMenu = new MenuItem("Exit");
        backMenu = new MenuItem("Back");
        openCourses.getItems().addAll(firstSem, secondSem, exitMenu);


        exitMenu.setOnAction(e -> Platform.exit());
        backMenu.setOnAction(e -> {
            clearFields();
            second.show();
            tertiaryStage.hide();
            tertiaryStage.close();
        });
        manage.getItems().addAll(editStudents, openCourses, new SeparatorMenuItem(),backMenu, exitMenu);
        menuBar.getMenus().add(manage);
        //add view menu


        return menuBar;
    }

    void editWindow(String table)
    {

        Stage window = new Stage();
        window.getIcons().add(new Image ("file:Unizik.png"));
        window.setTitle("Attendance Manager");
        BoxAndPane bnp = new BoxAndPane();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setWidth(300);
        window.setHeight(600);

        save1 = bnp.button(50,10,"Update");
        save1.setOnAction(sv ->
        {
            if (table.compareToIgnoreCase("yearthreestudents") == 0 || table.compareToIgnoreCase("yearfourstudents") == 0 || table.compareToIgnoreCase("yearfivestudents") == 0) {
            if(validate2()) {
                    DBQuery.updateQuery(table, id1, fn1, on1, ln1, em1, dob1, radioButtonLabel, ph1);
                    clearFields();
                    refreshTable(table);
                }
            }
            else{
            if(validate3())
            {
              DBQuery.updateQuery1(table,id1,fn1,ln1,on1,pw1,em1,ph1,radioBL);
                clearFields2();
                refreshTable(table);
            }}
                window.close();


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
        if(table.compareToIgnoreCase("authorized") ==0)
        {
            layout.getChildren().addAll(label2, id1, fn1, ln1, pw1, on1, ph1, m1, fm1, hBox);
        }
        else {
            layout.getChildren().addAll(label2, id1, fn1, on1, ln1, em1, ph1, dob1, m1, fm1, hBox);
        }
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("levels.css").toExternalForm());
        window.setScene(scene);
        window.show();

    }
    //bad
    public static boolean newDataForRegister = false;

   /* void Getdata(int id){
        //Communication.lvl = this;
        for(long x = 1 ; ; ){
            if(newDataForRegister){
                break;
            }
            for(long y = -9000000; y < 9000000; y++);

            //System.out.print(newDataForRegister);
        }

        captureCallBack(fingerprint);
    }*/


    void registerPrint(String sTable){
        if(sTable.compareToIgnoreCase("yearthreestudents")== 0)
        { int ID = 0;
            int i = 1;
            Boolean check = false;
            try {
                String query = "select * from " + sTable;
                ps= conn.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()) {
                    if(rs.getInt("fingerprint") != i) {
                        ID = i;
                        break;
                    }
                    if (rs.getInt("Fingerprint") == i) {
                        i++;
                    }
                }
                ID = i;
                ps.close();
                rs.close();


            } catch (SQLException excc) {
                System.out.print(excc);
                ID = i;
            }
            System.out.print(ID);
            comm.getFingerprintID(ID);
        }

        if(sTable.compareToIgnoreCase("yearfourstudents")== 0)
        {
            int ID = 0;
            Boolean check = false;
            try {
                int i = 41;
                String query = "select * from " + sTable;
                ps= conn.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()) {
                    if(rs.getInt("fingerprint") != i) {
                        ID = i;
                        break;
                    }
                    if (rs.getInt("Fingerprint") == i) {
                        i++;
                    }
                }
                ID = i;
                ps.close();
                rs.close();


            } catch (SQLException excc) {
                System.out.print(excc);
            }

            comm.getFingerprintID(ID);

        }

        if(sTable.compareToIgnoreCase("yearfivestudents")== 0)
        {
            int ID = 0;
            Boolean check = false;
            try {
                int i = 81;
                String query = "select * from " + sTable;
                ps= conn.prepareStatement(query);
                rs = ps.executeQuery();

                while (rs.next()) {
                    if(rs.getInt("fingerprint") != i) {
                        ID = i;
                        break;
                    }
                    if (rs.getInt("Fingerprint") == i) {
                        i++;
                    }
                }
                ID = i;
                ps.close();
                rs.close();


            } catch (SQLException excc) {
                System.out.print(excc);
            }

            comm.getFingerprintID(ID);
        }
}}
