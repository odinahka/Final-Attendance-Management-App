package reusable;

import DatabaseTransaction.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import storageUnit.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by odinahka on 9/15/2019.
 */
public class Actions {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

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
}
