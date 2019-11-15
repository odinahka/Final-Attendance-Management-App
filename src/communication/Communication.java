package communication;

import java.io.*;
import ArduinoAttendanceProject.PortSerialListener;
import ArduinoAttendanceProject.SerialCommunicator;
import scenes.Levels;

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

   // public static ArrayList<String> otherData = new ArrayList<>();
   // public static Levels lvl;

    //Registration
    public void getFingerprintID(int ID)
    {
        comm = new SerialCommunicator();
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
                //Levels.captureCallBack(Integer.parseInt(data));
                System.out.println(data);
                //otherData.add(data);
                Levels.fingerprint = Integer.parseInt(data);
                Levels.newDataForRegister = true;
            }
        });

        comm.SendCommand(SerialCommunicator.READ_SCANNER, ID);
    }

    //Update the database
   public void UpdateAttendace(){
        comm = new SerialCommunicator();
        if(!comm.portInitialized){
            System.out.println("Port not initialized");
            return;
        }

        comm.AddListener(new PortSerialListener(){
            @Override
            public void SerialEvent(String data) {
                dataBuffer = data;
            }
        });

        comm.SendCommand(SerialCommunicator.READ_SD,0);
    }

    //Write data to sed card
   public void WriteDataToSD(String data){
        comm = new SerialCommunicator();
        if(!comm.portInitialized){
            System.out.println("Port not initialized");

        }

        comm.AddListener(new PortSerialListener(){
            @Override
            public void SerialEvent(String data) {
            }
        });

        comm.SendCommand(SerialCommunicator.WRITE_SD,0,data);

    }

}
