package models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import storageUnit.*;
/**
 * Created by odinahka on 9/7/2019.
 */
public class Tables {


    public TableView authorizedUsersTable(){
        TableView<AuthorizedUsers> table = new TableView<>();
        TableColumn column1 = new TableColumn("ID");
        column1.setMinWidth(20);
        column1.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn column2 = new TableColumn("First Name");
        column2.setMinWidth(80);
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn column3 = new TableColumn("Last Name");
        column3.setMinWidth(80);
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn column4 = new TableColumn("Email");
        column4.setMinWidth(150);
        column4.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn column5 = new TableColumn("Username");
        column5.setMinWidth(80);
        column5.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn column6 = new TableColumn("Gender");
        column6.setMinWidth(50);
        column6.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        TableColumn column7 = new TableColumn("Phone No.");
        column7.setMinWidth(50);
        column7.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        TableColumn column8 = new TableColumn("Password.");
        column8.setMinWidth(50);
        column8.setCellValueFactory(new PropertyValueFactory<>("Password"));


        table.getColumns().addAll(column1, column2, column3, column4,column5, column6, column7, column8);
        table.setTableMenuButtonVisible(true);
        return table;
    }

    public TableView studentsTable()
    {
        TableView<Students> table = new TableView<>();

        TableColumn<Students, String> column1 = new TableColumn<>("Regd Number");
        column1.setMinWidth(20);
        column1.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Students, String> column2  = new TableColumn<>("First Name");
        column2.setMinWidth(80);
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn <Students, String> column3 = new TableColumn<>("Other Name");
        column3.setMinWidth(80);
        column3.setCellValueFactory(new PropertyValueFactory<>("otherName"));

        TableColumn <Students, String> column4 = new TableColumn<>("Last Name");
        column4.setMinWidth(80);
        column4.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn <Students, String> column5 = new TableColumn<>("Email");
        column5.setMinWidth(150);
        column5.setCellValueFactory(new PropertyValueFactory<>("email"));


        TableColumn <Students, String> column6 = new TableColumn<>("Phone No.");
        column6.setMinWidth(50);
        column6.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        TableColumn <Students, String> column7 = new TableColumn<>("Gender");
        column7.setMinWidth(50);
        column7.setCellValueFactory(new PropertyValueFactory<>("gender"));


        table.getColumns().addAll(column1, column2, column3, column4,column5, column6, column7);
        table.setTableMenuButtonVisible(true);
        return table;
    }

    public TableView CoursesTable()
    {
        TableView<CourseDetails> table = new TableView<>();

        TableColumn<CourseDetails, String> column2  = new TableColumn<>("Regd Number");
        column2.setMinWidth(80);
        column2.setCellValueFactory(new PropertyValueFactory<>("regdNumber"));

        TableColumn <CourseDetails, String> column3 = new TableColumn<>("First Name");
        column3.setMinWidth(80);
        column3.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn <CourseDetails, String> column4 = new TableColumn<>("Other Name");
        column4.setMinWidth(80);
        column4.setCellValueFactory(new PropertyValueFactory<>("otherName"));

        TableColumn <CourseDetails, String> column5 = new TableColumn<>("Last Name");
        column5.setMinWidth(80);
        column5.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn <CourseDetails, Integer> column6 = new TableColumn<>("Lecture Number");
        column6.setMinWidth(150);
        column6.setCellValueFactory(new PropertyValueFactory<>("lectureNumber"));


        TableColumn <CourseDetails, Integer> column7 = new TableColumn<>("Lectures Attended");
        column7.setMinWidth(50);
        column7.setCellValueFactory(new PropertyValueFactory<>("lecturesAttended"));

        TableColumn <CourseDetails, Integer> column8 = new TableColumn<>("Attendance Percentage");
        column8.setMinWidth(50);
        column8.setCellValueFactory(new PropertyValueFactory<>("attendancePecentage"));


        table.getColumns().addAll(column2, column3, column4,column5, column6, column7, column8);
        table.setTableMenuButtonVisible(true);
        return table;
    }
}
