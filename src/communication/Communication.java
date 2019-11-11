package communication;

import java.io.*;

/**
 * Created by odinahka on 11/5/2019.
 */
public class Communication {
    int i;
    char c;
    String [] dataBuffer;
    public Communication()
    {
     dataBuffer = new String[10];
    }
    public void retrieveAttendance(String databaseName) throws IOException
    {
        String str = "";
       try( BufferedReader br = new BufferedReader(new FileReader("text.txt"))){
          while((str = br.readLine()) != null)
          {
          }
       }
       catch (IOException e)
       {

       }

    }
}
