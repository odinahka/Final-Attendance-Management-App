package reusable;

import DatabaseTransaction.DBconnection;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import storageUnit.Students;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by odinahka on 9/15/2019.
 */
public class TableListener {

    Connection conn = DBconnection.Dbconnect();
    PreparedStatement ps;
    ResultSet rs;
    String ID;
    public void deleteStudent (TableView table)
    {
        table.setOnKeyReleased( e->
        {
            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN)
            {
                try {
                    Students students = (Students) table.getSelectionModel().getSelectedItem();
                    String query = "select * from YearThreeStudents where ID = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, students.getID());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        ID = rs.getString("ID");

                    }
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get() == ButtonType.OK) {
                        try {
                            String q = "delete from UserDatabase where id = ?";
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


}
