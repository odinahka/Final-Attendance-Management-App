package DatabaseTransaction;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sun.security.x509.OtherName;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by odinahka on 9/14/2019.
 */
public class DBQuery {
    public static void query(String studentsTable, TextField id, TextField firstName, TextField otherName,TextField lastName, TextField email, DatePicker date, String radioButtonLabel, TextField phoneNo )
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;

        if(studentsTable.compareToIgnoreCase("yearthreestudents") == 0) {
            try {
                String query = "INSERT INTO " + studentsTable + " (RegdNumber, FirstName, OtherName, LastName, Email, DOB, Gender, PhoneNumber) VALUES (?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8, phoneNo.getText());
                ps.execute();

                for(int i = 1; i <= 18; i++) {
                    String course = "";
                    if(i==1)
                        course = "CircuitTheoryII";
                    if(i==2)
                        course = "ElectromagneticFieldsAndWaves";
                    if(i==3)
                        course = "ElectromechDevicesAndMachinesI";
                    if(i==4)
                        course = "PowerSystems";
                    if(i==5)
                        course = "TelecommunicationI";
                    if(i==6)
                        course = "ElectronicDevicesAndCircuitI";
                    if(i==7)
                        course = "SignalAnalysisAndSystems";
                    if(i==8)
                        course = "DigitalSystemDesignI";
                    if(i==9)
                        course = "EngineeringMathematicsIII";
                    if(i==10)
                        course = "CircuitTheoryIII";
                    if(i==11)
                        course = "Electrodynamics";
                    if(i==12)
                        course = "ElectromechDevicesAndMachinesII";
                    if(i==13)
                        course = "InstrumentationAndMeasurementI";
                    if(i==14)
                        course = "FeedbackAndControlSystem";
                    if(i==15)
                        course = "TelecommunicationII";
                    if(i==16)
                        course = "ElectronicDevicesAndCircuitII";
                    if(i==17)
                        course = "PowerElectronics";
                    if(i==18)
                        course = "DigitalSystemDesignII";
                    String query2 = "INSERT INTO " + course + " (RegdNumber, FirstName, OtherName, LastName, LectureNumber, LecturesAttended, AttendancePercentage ) VALUES (?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.setInt(5,0);
                    ps.setInt(6,0);
                    ps.setInt(7,0);
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been added");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }

        if(studentsTable.compareToIgnoreCase("yearfourstudents") == 0) {
            try {
                String query = "INSERT INTO " + studentsTable + " (RegdNumber, FirstName, OtherName, LastName, Email, DOB, Gender, PhoneNumber) VALUES (?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8, phoneNo.getText());
                ps.execute();

                for(int i = 1; i <= 8; i++) {
                    String course = "";
                    if(i==1)
                        course = "EngineeringMathematicsIV";
                    if(i==2)
                        course = "EngineeringContractsAndSpecification";
                    if(i==3)
                        course = "CircuitTheoryIV";
                    if(i==4)
                        course = "InstrumentationAndMeasurementII";
                    if(i==5)
                        course = "MIcroprocessorsAndComputers";
                    if(i==6)
                        course = "AssemblyLanguageProgramming";
                    if(i==7)
                        course = "AdvancedCircuitTechniques";
                    if(i==8)
                        course = "FundamentalOfDigitalCommunications";
                    String query2 = "INSERT INTO " + course + " (RegdNumber, FirstName, OtherName, LastName, LectureNumber, LecturesAttended, AttendancePercentage ) VALUES (?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.setInt(5,0);
                    ps.setInt(6,0);
                    ps.setInt(7,0);
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been added");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }

        if(studentsTable.compareToIgnoreCase("yearfivestudents") == 0) {
            try {
                String query = "INSERT INTO " + studentsTable + " (RegdNumber, FirstName, OtherName, LastName, Email, DOB, Gender, PhoneNumber) VALUES (?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8, phoneNo.getText());
                ps.execute();

                for(int i = 1; i <= 15; i++) {
                    String course = "";
                    if(i==1)
                        course = "ComputerAidedDesign";
                    if(i==2)
                        course = "RealtimeComputingAndControl";
                    if(i==3)
                        course = "SolidStateElectronics";
                    if(i==4)
                        course = "DigitalSignalProcessing";
                    if(i==5)
                        course = "ArtificialIntelligenceAndRobotics";
                    if(i==6)
                        course = "WirelessCommunicationAndNetwork";
                    if(i==7)
                        course = "SystemProgramming";
                    if(i==8)
                        course = "SoftwareEngineering";
                    if(i==9)
                        course = "DatabaseManagementSystems";
                    if(i==10)
                        course = "DataCommunicationNetwork";
                    if(i==11)
                        course = "NetworkAnalysisAndSynthesis";
                    if(i==12)
                        course = "ControlSystemEngineering";
                    if(i==13)
                        course = "MicrowaveEngineering";
                    if(i==14)
                        course = "TVSatelliteAndRadarSystem";
                    if(i==15)
                        course = "OpticFibreCommunication";
                    String query2 = "INSERT INTO " + course + " (RegdNumber, FirstName, OtherName, LastName, LectureNumber, LecturesAttended, AttendancePercentage ) VALUES (?,?,?,?,?,?,?)";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.setInt(5,0);
                    ps.setInt(6,0);
                    ps.setInt(7,0);
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been added");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }
    }

    public static void query2(String authorizedTable, TextField id, TextField fn, TextField ln, TextField un, TextField pw, TextField em, TextField pn, String radioBL)
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;


        try {
            String query = "INSERT INTO " + authorizedTable + " (ID, FirstName, LastName, Username, Password, Email, PhoneNumber, Gender) VALUES (?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, id.getText());
            ps.setString(2, fn.getText());
            ps.setString(3, ln.getText());
            ps.setString(4, un.getText());
            ps.setString(5, pw.getText());
            ps.setString(6, em.getText());
            ps.setString(7, pn.getText());
            ps.setString(8, radioBL);
            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User has been Added");
            alert.showAndWait();

            ps.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }



    public static void updateQuery(String studentsTable, TextField id, TextField firstName, TextField otherName,TextField lastName, TextField email, DatePicker date, String radioButtonLabel, TextField phoneNo )
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;
        if(studentsTable.compareToIgnoreCase("yearthreestudents") == 0){

            try {
                String query = "update " + studentsTable+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=?, Email=?, DOB=?, Gender=?, PhoneNumber=? where RegdNumber ='"+id.getText()+"'";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4,lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8,phoneNo.getText());
                ps.execute();
                for(int i = 1; i <= 18; i++) {
                    String course = "";
                    if(i==1)
                        course = "CircuitTheoryII";
                    if(i==2)
                        course = "ElectromagneticFieldsAndWaves";
                    if(i==3)
                        course = "ElectromechDevicesAndMachinesI";
                    if(i==4)
                        course = "PowerSystems";
                    if(i==5)
                        course = "TelecommunicationI";
                    if(i==6)
                        course = "ElectronicDevicesAndCircuitI";
                    if(i==7)
                        course = "SignalAnalysisAndSystems";
                    if(i==8)
                        course = "DigitalSystemDesignI";
                    if(i==9)
                        course = "EngineeringMathematicsIII";
                    if(i==10)
                        course = "CircuitTheoryIII";
                    if(i==11)
                        course = "Electrodynamics";
                    if(i==12)
                        course = "ElectromechDevicesAndMachinesII";
                    if(i==13)
                        course = "InstrumentationAndMeasurementI";
                    if(i==14)
                        course = "FeedbackAndControlSystem";
                    if(i==15)
                        course = "TelecommunicationII";
                    if(i==16)
                        course = "ElectronicDevicesAndCircuitII";
                    if(i==17)
                        course = "PowerElectronics";
                    if(i==18)
                        course = "DigitalSystemDesignII";
                    String query2 = "update "+course+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=? where RegdNumber = '" + id.getText() + "'";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been updated");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }

        if(studentsTable.compareToIgnoreCase("yearfourstudents") == 0){

            try {
                String query = "update " + studentsTable+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=?, Email=?, DOB=?, Gender=?, PhoneNumber=? where RegdNumber ='"+id.getText()+"'";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8, phoneNo.getText());
                ps.execute();
                for(int i = 1; i <= 8; i++) {
                    String course = "";
                    if(i==1)
                        course = "EngineeringMathematicsIV";
                    if(i==2)
                        course = "EngineeringContractsAndSpecification";
                    if(i==3)
                        course = "CircuitTheoryIV";
                    if(i==4)
                        course = "InstrumentationAndMeasurementII";
                    if(i==5)
                        course = "MIcroprocessorsAndComputers";
                    if(i==6)
                        course = "AssemblyLanguageProgramming";
                    if(i==7)
                        course = "AdvancedCircuitTechniques";
                    if(i==8)
                        course = "FundamentalOfDigitalCommunications";
                    String query2 = "update "+course+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=? where RegdNumber = '" + id.getText() + "'";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been updated");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }

        if(studentsTable.compareToIgnoreCase("yearfivestudents") == 0){

            try {
                String query = "update " + studentsTable+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=?, Email=?, DOB=?, Gender=?, PhoneNumber=? where RegdNumber ='"+id.getText()+"'";
                ps = conn.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, firstName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, lastName.getText());
                ps.setString(5, email.getText());
                ps.setString(6, ((TextField) date.getEditor()).getText());
                ps.setString(7, radioButtonLabel);
                ps.setString(8, phoneNo.getText());
                ps.execute();
                for(int i = 1; i <= 15; i++) {
                    String course = "";
                    if(i==1)
                        course = "ComputerAidedDesign";
                    if(i==2)
                        course = "RealtimeComputingAndControl";
                    if(i==3)
                        course = "SolidStateElectronics";
                    if(i==4)
                        course = "DigitalSignalProcessing";
                    if(i==5)
                        course = "ArtificialIntelligenceAndRobotics";
                    if(i==6)
                        course = "WirelessCommunicationAndNetwork";
                    if(i==7)
                        course = "SystemProgramming";
                    if(i==8)
                        course = "SoftwareEngineering";
                    if(i==9)
                        course = "DatabaseManagementSystems";
                    if(i==10)
                        course = "DataCommunicationNetwork";
                    if(i==11)
                        course = "NetworkAnalysisAndSynthesis";
                    if(i==12)
                        course = "ControlSystemEngineering";
                    if(i==13)
                        course = "MicrowaveEngineering";
                    if(i==14)
                        course = "TVSatelliteAndRadarSystem";
                    if(i==15)
                        course = "OpticFibreCommunication";
                    String query2 = "update "+course+ " set RegdNumber=?, FirstName=?, OtherName=?, LastName=? where RegdNumber = '" + id.getText() + "'";
                    ps = conn.prepareStatement(query2);
                    ps.setString(1, id.getText());
                    ps.setString(2, firstName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, lastName.getText());
                    ps.execute();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Students details have been updated");
                alert.showAndWait();

                ps.close();
            } catch (Exception exp) {
                //label.setText("SQL error");
                System.err.println(exp);
            }
        }

    }

    public static void updateQuery1(String authorizedTable, TextField id, TextField fn, TextField ln, TextField un, TextField pw, TextField em, TextField pn, String radioBL)
    {
        PreparedStatement ps;
        Connection conn = DBconnection.Dbconnect(); // checkConnection();;


        try {
            String query = "update " + authorizedTable + " set ID=?, FirstName=?, LastName=?, Username=?, Password=?, Email=?, PhoneNumber=?, Gender=? where ID ='" + id.getText() + "'";
            ps = conn.prepareStatement(query);
            ps.setString(1, id.getText());
            ps.setString(2, fn.getText());
            ps.setString(3, ln.getText());
            ps.setString(4, un.getText());
            ps.setString(5, pw.getText());
            ps.setString(6, em.getText());
            ps.setString(7, pn.getText());
            ps.setString(8, radioBL);
            ps.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("User has been Updated");
            alert.showAndWait();

            ps.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }


}
