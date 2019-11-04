package models;

import DatabaseTransaction.DBconnection;
import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by odinahka on 11/4/2019.
 */
public class ExcelForm {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn;
   public ExcelForm(){
        conn = DBconnection.Dbconnect(); // checkConnection();
    }

   public void exportToExcel(String databaseName)
    {
        try {
            String query = "Select * from "+databaseName;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sh = wb.createSheet("Students Attendance Report");
            HSSFRow rw = sh.createRow(0);
            rw.createCell(0).setCellValue("Regd Number");
            rw.createCell(1).setCellValue("First Name");
            rw.createCell(2).setCellValue("Other Name");
            rw.createCell(3).setCellValue("Last Name");
            rw.createCell(4).setCellValue("LectureNumber");
            rw.createCell(5).setCellValue("LecturesAttended");
            rw.createCell(6).setCellValue("Attendance Percentage");

            sh.setColumnWidth(0, 200*25);
            sh.setColumnWidth(1, 200*25);
            sh.setColumnWidth(2, 200*25);
            sh.setColumnWidth(3, 200*25);
            sh.setColumnWidth(4, 200*25);
            sh.setColumnWidth(5, 200*25);
            sh.setColumnWidth(6, 200*25);

            sh.setZoom(150);

            int index = 1;

            while(rs.next())
            {
                HSSFRow rw1 = sh.createRow(index);
                index++;
                rw1.createCell(0).setCellValue(rs.getString("RegdNumber"));
                rw1.createCell(1).setCellValue(rs.getString("FirstName"));
                rw1.createCell(2).setCellValue(rs.getString("OtherName"));
                rw1.createCell(3).setCellValue(rs.getString("LastName"));
                rw1.createCell(4).setCellValue(rs.getString("LectureNumber"));
                rw1.createCell(5).setCellValue(rs.getString("LecturesAttended"));
                rw1.createCell(6).setCellValue(rs.getString("AttendancePercentage"));

            }
            FileOutputStream fileOut = new FileOutputStream(databaseName+".xls");
            wb.write(fileOut);
            fileOut.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Attendance report exported to EXCEL");
            alert.showAndWait();

            ps.close();
            rs.close();


        }
        catch(Exception eec)
        {
            System.err.println(eec);
        }

    }

  public void importFromExcel(String databaseName)
    {
        try
        {
            String query = "Insert into UserDatabase (RegdNumber, FirstName, LastName, LastName) values (?,?,?,?)";
            ps = conn.prepareStatement(query);
            FileInputStream fileIn = new FileInputStream(databaseName+".xls");
            HSSFWorkbook workbook = new HSSFWorkbook(fileIn);
            HSSFSheet sh = workbook.getSheetAt(0);
            Row row;
            for(int i = 1; i <= sh.getLastRowNum(); i++ )
            {
                row = sh.getRow(i);

                ps.setInt(1, (int) row.getCell(0).getNumericCellValue());
                ps.setString(2, row.getCell(1).getStringCellValue());
                ps.setString(3, row.getCell(2).getStringCellValue());
                ps.setString(4, row.getCell(3).getStringCellValue());
                ps.execute();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User Details imported from Excel to Database");
            alert.showAndWait();
            fileIn.close();
            workbook.close();
            ps.close();

        }
        catch (Exception ecc)
        {
            System.err.println(ecc);
        }
    }
}
