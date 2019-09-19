package scenes;

import DatabaseTransaction.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.BoxAndPane;
import models.Tables;
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
                "ELE 341 - Electromagnetic Fields & Waves",
                "ELE 343 - Electromech. Devices & Machine I",
                "ELE 353 - Power Systems",
                "ECE 321 - Telecommunication I",
                "ECE 323 - Electronic Devices & Circuits I",
                "ECE 332 - Signals Analysis and Systems",
                "ECE 333 - Digital System Design I"
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
        window.show();
    }

    private void courseScene(String databaseName)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        BoxAndPane bp = new BoxAndPane();
        BorderPane borderpane = new BorderPane();
        Scene scene = new Scene(borderpane, 700,400);
        tb = table.CoursesTable();
        ScrollPane sp = bp.scrollpane(400,400,tb);
        VBox spContainer = new VBox(10);
        spContainer.setPadding(new Insets(10));
        spContainer.setAlignment(Pos.CENTER);
        spContainer.getChildren().add(sp);
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
                                rs.getInt("ID"),
                                rs.getString("RegdNumber"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("Othername"),
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
        window.setScene(scene);
        window.show();
    }

}
