//created by andyshen on 15.7.2019

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadJson {

    public static String[] ReadLoadFile(String Path, int nItem) {
        BufferedReader reader = null;
        String[] jsarr = new String[nItem];
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            int count = 0;
            while ((tempString = reader.readLine()) != null ) {
                if(count%2==1)
                    jsarr[count/2] = tempString;
                count++;

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsarr;
    }
}
