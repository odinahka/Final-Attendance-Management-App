package scenes;

import DatabaseTransaction.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Created by odinahka on 9/19/2019.
 */
public class YearThreeCourses {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    private ObservableList<String> courses;
    final ObservableList data = FXCollections.observableArrayList();
    TableView<Students> tb;
    Tables table = new Tables();
    TextField search, fn, ln, on, rn;

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
                "ELE 311 - Circuit Theory II",
                "ELE 341 - Electromagnetic Fields and Waves",
                "ELE 343 - Electromech Devices and Machines I",
                "ELE 353 - Power Systems",
                "ECE 321 - Telecommunication I",
                "ECE 323 - Electronic Devices and Circuits I",
                "ECE 332 - Signals Analysis and Systems",
                "ECE 333 - Digital System Design I",
                "FEG 303 - Engineering Mathematics III"
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

    public void secondSemester() {

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
                "ECE 322 - Telecommunication II",
                "ECE 326 - Power Electronics",
                "ELE 344 - Electromech Devices and Machine II",
                "ECE 328 - Electronic ",
                "ECE 328 - Electronic Devices and Circuits II",
                "ECE 334 - Digital System Design II",
                "ELE 312 - Circuit Theory III",
                "ELE 342 - Electrodynamics",
                "ELE 372 - Instrumentation and Measurement I",
                "ELE 382 - Feedback and Control Engineering"
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
        Texts text = new Texts();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        BoxAndPane bp = new BoxAndPane();
        BorderPane borderpane = new BorderPane();
        Scene scene = new Scene(borderpane, 700,400);
        tb = table.CoursesTable();
        ScrollPane sp = bp.scrollpane(600,400,tb);
        HBox spContainer = new HBox(10);
        spContainer.setPadding(new Insets(10));
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
        search = text.textField(200,15,"Regd Number");
       /* fn = text.textField(200,15,"First Name");
        ln = text.textField(200,15,"Last Name");
        rn = text.textField(200,15,"Regd Number");
        fn.setEditable(false);
        ln.setEditable(false);
        rn.setEditable(false); */

        Button searchButton = bp.button(50,10,"Search");
        spContainer.getChildren().addAll(search, searchButton);

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
        others.getItems().addAll(addStudent,new SeparatorMenuItem(),generateReport);

        menuBar.getMenus().addAll(update, others);

        borderpane.setTop(menuBar);
        window.setScene(scene);
        window.setMinWidth(850);
        window.setMinHeight(700);
        window.show();
    }

}
