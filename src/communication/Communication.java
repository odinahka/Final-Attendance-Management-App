package communication;

import java.io.*;
import ArduinoAttendanceProject.PortSerialListener;
import ArduinoAttendanceProject.SerialCommunicator;
import javafx.application.Platform;
import scenes.ILevels;
import scenes.Levels;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by odinahka on 11/5/2019.
 *
 */
public class Communication {
    int i;
    char c;
    String dataBuffer;
    private boolean loop = true;
    static SerialCommunicator comm ;
    String rData;
    public Struct Tables;

   // public static ArrayList<String> otherData = new ArrayList<>();
   // public static Levels lvl;

    //Registration
    public void getFingerprintID(int ID)
    {
        try{
            comm.ClosePort();
        }catch(Exception e){}


        try{
            comm = new SerialCommunicator();
        }catch(Exception ex){
            System.out.println("Chaeck the device");
            return;
        }
        if(!comm.portInitialized){
            System.out.println("Port not initialized");
            return;
        }

        comm.AddListener(new PortSerialListener(){
            @Override
            public void SerialEvent(String data) {
                //synchronized (lvl){
                    //lvl.captureCallBack(Integer.parseInt(data));
                //}

                Platform.runLater(() -> {
                    Levels.captureCallBack(Integer.parseInt(data));
                    System.out.println(data);
                });
                    comm.ClosePort();
                //otherData.add(data);
               /* Levels.fingerprint = Integer.parseInt(data);
                Levels.newDataForRegister = true;*/
            }
        });

        comm.SendCommand(SerialCommunicator.READ_SCANNER, ID);
    }

    //Update the database
   public static void UpdateAttendace(String tableName, ILevels levels){

       try{
           comm.ClosePort();
       }catch(Exception e){}

       try{
           comm = new SerialCommunicator();
       }catch(Exception ex){
           System.out.println("Chaeck the device");
           return;
       }
        if(!comm.portInitialized){
            System.out.println("Port not initialized");
            return;
        }

        comm.AddListener(new PortSerialListener(){
            @Override
            public void SerialEvent(String data) {
              //  System.out.print(data);
                Platform.runLater(() -> {
                    Levels.updateAttendance(data, tableName, levels);
                    //System.out.println(data);
                });
            }
        });

        comm.SendCommand(SerialCommunicator.READ_SD,0);
    }

    //Write data to sed card
   public static void WriteDataToSD(String data){

       try{
           comm.ClosePort();
       }catch(Exception e){}

       try{
           comm = new SerialCommunicator();
       }catch(Exception ex){
           System.out.println("Chaeck the device");
           return;
       }


        if(!comm.portInitialized){
            System.out.println("Port not initialized");

        }

        comm.AddListener(new PortSerialListener(){
            @Override
            public void SerialEvent(String data) {
                System.out.print(data);
            }
        });

        comm.SendCommand(SerialCommunicator.DELETE_WRITE,0,data);

    }

}
