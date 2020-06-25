import java.io.*;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;


public class TwitConnector {


    public static StringBuilder getHtml (String mention) throws Exception {

        try {
            BufferedReader bis;
            HttpsURLConnection con;
            URL url;
            StringBuilder sb;

            System.out.println("Downloading your posts....");
            url = new URL("https://twitter.com/" + mention);

            con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            con.setDoOutput(true);
            con.setRequestMethod("GET");

            bis = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            sb = new StringBuilder();
            int num;
             while (true) {

                if ((num = bis.read()) != -1) {
                    sb.append((char)num);
                } else
                    break;
            }
            con.disconnect();
            
            return sb;
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new Exception("that ID doesn't exists!!!");
        }
        
    }
}