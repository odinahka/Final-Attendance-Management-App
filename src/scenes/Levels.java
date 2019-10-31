package scenes;


/**
 * Created by odinahka on 10/31/2019.
 */
import DatabaseTransaction.DBQuery;
import DatabaseTransaction.DBconnection;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import storageUnit.Students;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

public class Levels {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn;
    Scene  secondScene;
    private String gender;
    private VBox fields, options;
    //fields and labels setup
    private TextField id, fn, ln, em, ph, on,sc, pc;
    private DatePicker dob;
    private RadioButton m,fm;

    TextField id1, fn1, ln1, em1, ph1, on1;
    DatePicker dob1;
    RadioButton m1,fm1;
    Button save1, back;

    private String radioButtonLabel;
    private Label label2;
    private MenuBar menuBar;
    private Menu fileMenu, subMenu, toolBar, viewMenu, mode,firstSem,secondSem;
    private MenuItem item1, exitMenu, backMenu;
    private CheckMenuItem check1,check2;
    private RadioMenuItem mode1, mode2;
    private ToggleGroup tGroup;
    final ObservableList data = FXCollections.observableArrayList();
    TableView<Students> tb;
    Stage second, tertiaryStage;
    BoxAndPane bp;
    BorderPane place;
    Tables table;
    Texts texts;

    public Levels(Stage secondaryStage)
    {
        second = secondaryStage;
        bp = new BoxAndPane();
        fields = bp.vbox();
        options = bp.vbox();
        place = new BorderPane();
        table = new Tables();
        texts = new Texts();
        tertiaryStage = new Stage();
        //Parent root = FXMLLoader.load(getClass().getResource("MainController.fxml"));
        conn = DBconnection.Dbconnect(); // checkConnection();
        secondScene = new Scene(place,280,500);
        //scene1.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
    }
    public void level(String sTable)
    {
        //Stage and scene setup
        BorderPane container = new BorderPane();
        Scene levelThree = new Scene(container,1200,700);
        tertiaryStage.setScene(levelThree);
        tertiaryStage.show();
        MenuBar menuBar = menu(sTable);

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
            radioButtonLabel = m1.getText();
        });
        fm1 = texts.radioButton("Female");
        fm1.setOnAction(female ->
        {
            fm1.setToggleGroup(gender1);
        });


        tableMove(sTable);
        tableClick(sTable);

        Button save = bp.button(50,10,"Save");
        save.setOnAction(sv ->
        {
            if(validate()) {
                DBQuery.query(sTable,id, fn, ln, on, em, dob, radioButtonLabel, ph);
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

    public void refreshTable(String table)
    {
        try
        {
            data.clear();
            String query = "select * from " + table;
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
    public void tableMove (String table)
    {
        tb.setOnMouseClicked(e1 -> {
            try {
                Students students = (Students) tb.getSelectionModel().getSelectedItem();
                clearFields();
                String query = "select * from "+table+" where RegdNumber = ?";
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
        });
        tb.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT)
            {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    clearFields();
                    String query = "select * from "+table+" where RegdNumber = ?";
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
    void tableClick(String table)
    {
        tb.setOnMouseClicked(e ->
        {
            try {
                Students students = (Students) tb.getSelectionModel().getSelectedItem();
                clearFields();
                String query = "select * from "+table+" where RegdNumber = ?";
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

    void delete(String table)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            try {
                String query = "delete from " + table +" where regdnumber = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, pc.getText());
                ps.executeUpdate();

                ps.close();
            } catch (SQLException exc) {
                System.err.println(exc);
            }
        }
        clearFields();
        refreshTable(table);
    }
    void edit(String table)
    {
        tb.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN)
            {
                try {
                    Students students = (Students) tb.getSelectionModel().getSelectedItem();
                    String query = "select * from "+table+" where regdnumber = ?";
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
                String query = "select * from " + table+ " where regdnumber = ?";
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

    public MenuBar menu(String table) {


        menuBar =new MenuBar();

        fileMenu = new Menu("File");
        subMenu = new Menu("Open Courses");
        item1 = new MenuItem("Edit Students...");
        item1.setOnAction(e -> editWindow(table));


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
        subMenu.getItems().addAll(firstSem, secondSem, exitMenu);


        exitMenu.setOnAction(e -> Platform.exit());
        backMenu.setOnAction(e -> {
            clearFields();
            second.show();
            tertiaryStage.hide();
            tertiaryStage.close();
        });
        fileMenu.getItems().addAll(item1, subMenu, new SeparatorMenuItem(),backMenu, exitMenu);
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

    void editWindow(String table)
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
                DBQuery.updateQuery(table,id1, fn1, ln1, on1, em1, dob1, radioButtonLabel, ph1);
                clearFields();
                refreshTable(table);
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
}
