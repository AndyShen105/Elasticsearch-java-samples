////created by andyshen on 15.7.2019
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class ReadJson {
//
//    public static String ReadLoadFile(String Path) {
//        BufferedReader reader = null;
//        String jsonstr = "";
//        try {
//            FileInputStream fileInputStream = new FileInputStream(Path);
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
//            reader = new BufferedReader(inputStreamReader);
//            String tempString = null;
//            int count = 0;
//            while ((tempString = reader.readLine()) != null ) {
//                if(count%2==1)
//                   // System.out.print(tempString+"\r\n");
//                count++;
//                jsonstr += tempString;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return jsonstr;
//    }
//}
