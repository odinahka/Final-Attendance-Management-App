package DatabaseTransaction;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by odinahka on 9/14/2019.
 */
public class DBQuery {
    public static void query(TextField id, TextField firstName, TextField lastName,TextField otherName, TextField email, DatePicker date, String radioButtonLabel, TextField phoneNo )
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;
        try {
            String query = "INSERT INTO YearThreeStudents (RegdNumber, FirstName, LastName, OtherName, Email, DOB, Gender, PhoneNumber) VALUES (?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, id.getText());
            ps.setString(2, firstName.getText());
            ps.setString(3, lastName.getText());
            ps.setString(4,otherName.getText());
            ps.setString(5, email.getText());
            ps.setString(6, ((TextField) date.getEditor()).getText());
            ps.setString(7, radioButtonLabel);
            ps.setString(8,phoneNo.getText());


            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User has been created");
            alert.showAndWait();

            ps.close();
        } catch (Exception exp) {
            //label.setText("SQL error");
            System.err.println(exp);
        }
    }

    public static void updateQuery(TextField id, TextField firstName, TextField lastName,TextField otherName, TextField email, DatePicker date, String radioButtonLabel, TextField phoneNo )
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;
        try {
            String query = "update yearthreestudents set RegdNumber=?, FirstName=?, LastName=?, OtherName=?, Email=?, DOB=?, Gender=?, PhoneNumber=? where ID ='"+id.getText()+"' ";
            ps = conn.prepareStatement(query);
            ps.setString(1, id.getText());
            ps.setString(2, firstName.getText());
            ps.setString(3, lastName.getText());
            ps.setString(4,otherName.getText());
            ps.setString(5, email.getText());
            ps.setString(6, ((TextField) date.getEditor()).getText());
            ps.setString(7, radioButtonLabel);
            ps.setString(8,phoneNo.getText());


            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User has been Updated");
            alert.showAndWait();

            ps.close();
        } catch (Exception exp) {
            //label.setText("SQL error");
            System.err.println(exp);
        }
    }
}
