package models;

import DatabaseTransaction.DBconnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by odinahka on 11/5/2019.
 */
public class Promote {
    public static void promote()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            ExcelForm export = new ExcelForm();
            PreparedStatement ps;
            Connection conn = DBconnection.Dbconnect(); // checkConnection()
            export.exportFinalToExcel();
            String query = "delete from yearfivestudents";
            String query1 = "insert into yearfivestudents select * from yearfourstudents";
            String query2 = "delete from yearfourstudents";
            String query3 = "insert into yearfourstudents select * from yearthreestudents";
            String query4 = "delete from yearthreestudents";
            try {
                ps = conn.prepareStatement(query);
                ps.execute();
                ps = conn.prepareStatement(query1);
                ps.execute();
                for (int i = 1; i <= 15; i++) {
                    String course = "";
                    if (i == 1)
                        course = "ComputerAidedDesign";
                    if (i == 2)
                        course = "RealtimeComputingAndControl";
                    if (i == 3)
                        course = "SolidStateElectronics";
                    if (i == 4)
                        course = "DigitalSignalProcessing";
                    if (i == 5)
                        course = "ArtificialIntelligenceAndRobotics";
                    if (i == 6)
                        course = "WirelessCommunicationAndNetwork";
                    if (i == 7)
                        course = "SystemProgramming";
                    if (i == 8)
                        course = "SoftwareEngineering";
                    if (i == 9)
                        course = "DatabaseManagementSystems";
                    if (i == 10)
                        course = "DataCommunicationNetwork";
                    if (i == 11)
                        course = "NetworkAnalysisAndSynthesis";
                    if (i == 12)
                        course = "ControlSystemEngineering";
                    if (i == 13)
                        course = "MicrowaveEngineering";
                    if (i == 14)
                        course = "TVSatelliteAndRadarSystem";
                    if (i == 15)
                        course = "OpticFibreCommunication";
                    String query0 = "delete from " + course;
                    ps = conn.prepareStatement(query0);
                    ps.execute();
                }
                for (int i = 1; i <= 15; i++) {
                    String course = "";
                    if (i == 1)
                        course = "ComputerAidedDesign";
                    if (i == 2)
                        course = "RealtimeComputingAndControl";
                    if (i == 3)
                        course = "SolidStateElectronics";
                    if (i == 4)
                        course = "DigitalSignalProcessing";
                    if (i == 5)
                        course = "ArtificialIntelligenceAndRobotics";
                    if (i == 6)
                        course = "WirelessCommunicationAndNetwork";
                    if (i == 7)
                        course = "SystemProgramming";
                    if (i == 8)
                        course = "SoftwareEngineering";
                    if (i == 9)
                        course = "DatabaseManagementSystems";
                    if (i == 10)
                        course = "DataCommunicationNetwork";
                    if (i == 11)
                        course = "NetworkAnalysisAndSynthesis";
                    if (i == 12)
                        course = "ControlSystemEngineering";
                    if (i == 13)
                        course = "MicrowaveEngineering";
                    if (i == 14)
                        course = "TVSatelliteAndRadarSystem";
                    if (i == 15)
                        course = "OpticFibreCommunication";
                    String query11 = "insert into " + course + "(RegdNumber, FirstName, OtherName, LastName, Fingerprint) select RegdNumber, FirstName, OtherName, LastName, Fingerprint from yearfourstudents";
                    ps = conn.prepareStatement(query11);
                    ps.execute();
                }
                ps = conn.prepareStatement(query2);
                ps.execute();
                for (int i = 1; i <= 8; i++) {
                    String course = "";
                    if (i == 1)
                        course = "EngineeringMathematicsIV";
                    if (i == 2)
                        course = "EngineeringContractsAndSpecification";
                    if (i == 3)
                        course = "CircuitTheoryIV";
                    if (i == 4)
                        course = "InstrumentationAndMeasurementII";
                    if (i == 5)
                        course = "MIcroprocessorsAndComputers";
                    if (i == 6)
                        course = "AssemblyLanguageProgramming";
                    if (i == 7)
                        course = "AdvancedCircuitTechniques";
                    if (i == 8)
                        course = "FundamentalOfDigitalCommunications";
                    String query22 = "delete from " + course;
                    ps = conn.prepareStatement(query22);
                    ps.execute();
                }
                ps = conn.prepareStatement(query3);
                ps.execute();
                for (int i = 1; i <= 8; i++) {
                    String course = "";
                    if (i == 1)
                        course = "EngineeringMathematicsIV";
                    if (i == 2)
                        course = "EngineeringContractsAndSpecification";
                    if (i == 3)
                        course = "CircuitTheoryIV";
                    if (i == 4)
                        course = "InstrumentationAndMeasurementII";
                    if (i == 5)
                        course = "MIcroprocessorsAndComputers";
                    if (i == 6)
                        course = "AssemblyLanguageProgramming";
                    if (i == 7)
                        course = "AdvancedCircuitTechniques";
                    if (i == 8)
                        course = "FundamentalOfDigitalCommunications";

                    String query33 = "insert into " + course + "(RegdNumber, FirstName, OtherName, LastName, Fingerprint) select RegdNumber, FirstName, OtherName, LastName, Fingerprint from yearfourstudents";
                    ps = conn.prepareStatement(query33);
                    ps.execute();
                }
                ps = conn.prepareStatement(query4);
                ps.execute();
                for (int i = 1; i <= 18; i++) {
                    String course = "";
                    if (i == 1)
                        course = "CircuitTheoryII";
                    if (i == 2)
                        course = "ElectromagneticFieldsAndWaves";
                    if (i == 3)
                        course = "ElectromechDevicesAndMachinesI";
                    if (i == 4)
                        course = "PowerSystems";
                    if (i == 5)
                        course = "TelecommunicationI";
                    if (i == 6)
                        course = "ElectronicDevicesAndCircuitI";
                    if (i == 7)
                        course = "SignalAnalysisAndSystems";
                    if (i == 8)
                        course = "DigitalSystemDesignI";
                    if (i == 9)
                        course = "EngineeringMathematicsIII";
                    if (i == 10)
                        course = "CircuitTheoryIII";
                    if (i == 11)
                        course = "Electrodynamics";
                    if (i == 12)
                        course = "ElectromechDevicesAndMachinesII";
                    if (i == 13)
                        course = "InstrumentationAndMeasurementI";
                    if (i == 14)
                        course = "FeedbackAndControlSystem";
                    if (i == 15)
                        course = "TelecommunicationII";
                    if (i == 16)
                        course = "ElectronicDevicesAndCircuitII";
                    if (i == 17)
                        course = "PowerElectronics";
                    if (i == 18)
                        course = "DigitalSystemDesignII";
                    String query44 = "delete from " + course;
                    ps = conn.prepareStatement(query44);
                    ps.execute();
                }
                ps.close();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information Dialog");
                alert1.setHeaderText(null);
                alert1.setContentText("Students have been Updated");
                alert1.showAndWait();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }


    }

}
