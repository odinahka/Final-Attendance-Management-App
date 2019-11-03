package scenes;

import DatabaseTransaction.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.BoxAndPane;
import models.Tables;
import models.Texts;
import storageUnit.CourseDetails;
import storageUnit.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by odinahka on 9/19/2019.
 */
public class YearFourCourses {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    private ObservableList<String> courses;
    final ObservableList data = FXCollections.observableArrayList();
    TableView<CourseDetails> tb;
    Tables table = new Tables();
    Texts texts = new Texts();
    TextField search, fn, ln, on, rn, pc;

    public void firstSemester() {

        conn = DBconnection.Dbconnect();
        Stage window = new Stage();

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        window.setWidth(300);
        window.setHeight(500);

        ListView listView = new ListView();
        courses = FXCollections.observableArrayList(
                "FEG 404 - Engineering Mathematics IV",
                "CVE 423 - Engineering Contracts and Specification",
                "ELE 403 - Circuit Theory IV",
                "ELE 473 - Instrumentation and Measurement II",
                "ECE 405 - Microprocessors and Computers",
                "ECE 421 - Assembly Language Programming",
                "ECE 427 - Advanced Circuit Techniques",
                "ECE 431 - Fundamental of Digital Communication"
        );
        listView.setItems(courses);
        listView.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        String databaseName = "" ;
                        for(String m : courses)
                        {
                            if(m == (String)listView.getSelectionModel().getSelectedItem()) {
                                m = m.replaceAll("\\s", "");
                                databaseName = m.substring(7);
                                System.out.println(databaseName);
                            }
                        }

                        courseScene(databaseName);
                    }
                }
        );
        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
    }

    private void courseScene(String databaseName)
    {
        pc = texts.textField(200,15,"");
        refreshTable(databaseName);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        BoxAndPane bp = new BoxAndPane();
        BorderPane borderpane = new BorderPane();
        Scene scene = new Scene(borderpane, 700,400);
        tb = table.CoursesTable();
        ScrollPane sp = bp.scrollpane(600,400,tb);
        HBox spContainer = new HBox(10);
        spContainer.setPadding(new Insets(10));
        spContainer.setSpacing(50);
        spContainer.setAlignment(Pos.TOP_LEFT);
        //spContainer.getChildren().add(sp);
        sp.setPadding(new Insets(40));
        borderpane.setBottom(spContainer);
        borderpane.setCenter(sp);
        try
        {
            data.clear();
            String query = "select * from "+ databaseName;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                data.add(
                        new CourseDetails(
                                rs.getString("RegdNumber"),
                                rs.getString("FirstName"),
                                rs.getString("OtherName"),
                                rs.getString("LastName"),
                                rs.getInt("LectureNumber"),
                                rs.getInt("LecturesAttended"),
                                rs.getInt("AttendancePercentage")
                        )
                );

            }
            ps.close();
            rs.close();
            tb.setItems(data);
        }catch(Exception exce)
        {
            System.err.println(exce);
        }
        search = texts.textField(200,15,"Regd Number");
       /* fn = text.textField(200,15,"First Name");
        ln = text.textField(200,15,"Last Name");
        rn = text.textField(200,15,"Regd Number");
        fn.setEditable(false);
        ln.setEditable(false);
        rn.setEditable(false); */

        Button delete = bp.button(50,10,"Delete");
        delete.setOnAction(e -> delete(databaseName));
        spContainer.getChildren().addAll(search, delete);

        MenuBar menuBar = new MenuBar();
        Menu update = new Menu("Update");
        MenuItem updateAttendance = new MenuItem("Update Attendance");
        update.getItems().add(updateAttendance);

        Menu others, generateReport;
        others = new Menu("Others");
        generateReport = new Menu("Generate Report");
        MenuItem authenticationReport = new MenuItem("Fingerprint Report");
        MenuItem printableReport = new MenuItem("Printable Report");
        MenuItem addStudent;
        generateReport.getItems().addAll(authenticationReport, printableReport);
        addStudent = new MenuItem("Add Students");
        addStudent.setOnAction(e -> addStudentScene(databaseName));
        others.getItems().addAll(addStudent,new SeparatorMenuItem(),generateReport);

        menuBar.getMenus().addAll(update, others);

        borderpane.setTop(menuBar);
        tableFilter();
        tableClick(databaseName);
        tableMove(databaseName);
        window.setScene(scene);
        window.setMinWidth(850);
        window.setMinHeight(700);
        window.show();
    }
    void addStudentScene(String databaseName){
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        BoxAndPane bnp = new BoxAndPane();
        Stage window = new Stage();
        fn = texts.textField(200,15,"First Name");
        on = texts.textField(200,15,"Other Name");
        ln = texts.textField(200,15,"Last Name");
        rn =texts.textField(200,15,"Regd Number");

        fn.setEditable(false);
        on.setEditable(false);
        ln.setEditable(false);

        Button search = bnp.button(50,10,"Search");
        search.setOnAction(e -> searchAction());
        Button add = bnp.button(50,10,"Add");
        add.setOnAction(e ->
        {
            addAction(databaseName);
            rn.setText("");
            fn.setText("");
            on.setText("");
            ln.setText("");
        });
        hbox.getChildren().addAll(search, add);
        hbox.setSpacing(100);
        vbox.setPadding(new Insets(50));
        vbox.setSpacing(20);
        vbox.getChildren().addAll(rn,hbox,fn,on,ln);
        window.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(vbox, 300,300);
        window.setScene(scene);
        window.show();
    }

    void searchAction(){
        try {
            String query = "select * from yearthreestudents where regdnumber = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, rn.getText());
            rs = ps.executeQuery();

            while (rs.next()) {
                fn.setText(rs.getString("FirstName"));
                ln.setText(rs.getString("LastName"));
                on.setText(rs.getString("OtherName"));
            }
            ps.close();
            rs.close();
        } catch (SQLException excc) {
            System.out.print(excc);
        }
    }

    void addAction(String databaseName)
    {
        try {

            String query2 = "INSERT INTO " + databaseName + " (RegdNumber, FirstName, OtherName, LastName, LectureNumber, LecturesAttended, AttendancePercentage ) VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query2);
            ps.setString(1, rn.getText());
            ps.setString(2, fn.getText());
            ps.setString(3, on.getText());
            ps.setString(4, ln.getText());
            ps.setInt(5,0);
            ps.setInt(6,0);
            ps.setDouble(7,0.0);
            ps.execute();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User has been created");
            alert.showAndWait();


            data.clear();
            String query = "select * from "+ databaseName;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            refreshTable(databaseName);
        } catch (Exception exp) {
            //label.setText("SQL error");
            System.err.println(exp);
        }
    }

    void tableFilter()
    {
        FilteredList<CourseDetails> filteredData = new FilteredList<>(data, e -> true);
        search.setOnKeyReleased( e-> {
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super  CourseDetails>) courseDetails -> {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }
                    String lowercaseFilter = newValue.toLowerCase();
                    if(courseDetails.getRegdNumber().contains(newValue))
                    {
                        return true;
                    }
                    else if(courseDetails.getFirstName().toLowerCase().contains(lowercaseFilter))
                    {
                        return true;
                    }
                    else if(courseDetails.getLastName().toLowerCase().contains(lowercaseFilter))
                    {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<CourseDetails> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tb.comparatorProperty());
            tb.setItems(sortedData);
        });
    }
    public void tableMove (String table)
    {
        tb.setOnMouseClicked(e1 -> {
            try {
                CourseDetails students = (CourseDetails) tb.getSelectionModel().getSelectedItem();
                String query = "select * from "+table+" where RegdNumber = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, students.getRegdNumber());
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
                    CourseDetails students = (CourseDetails) tb.getSelectionModel().getSelectedItem();

                    String query = "select * from "+table+" where RegdNumber = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getRegdNumber());
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
                CourseDetails students = (CourseDetails) tb.getSelectionModel().getSelectedItem();
                String query = "select * from "+table+" where RegdNumber = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, students.getRegdNumber());
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
            refreshTable(table);
        }

    }
    void refreshTable(String databaseName)
    {
        data.clear();
        try
        {
            String query = "select * from "+ databaseName;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                data.add(
                        new CourseDetails(
                                rs.getString("RegdNumber"),
                                rs.getString("FirstName"),
                                rs.getString("OtherName"),
                                rs.getString("LastName"),
                                rs.getInt("LectureNumber"),
                                rs.getInt("LecturesAttended"),
                                rs.getInt("AttendancePercentage")
                        )
                );

            }
            ps.close();
            rs.close();
            tb.setItems(data);
        } catch (Exception exp) {
            //label.setText("SQL error");
            System.err.println(exp);
        }
    }
}


