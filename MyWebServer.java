import java.io.*;
import java.net.*;
import java.util.*;
public class MyWebServer extends Thread {
    Socket sc;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    StringTokenizer st;
    public MyWebServer(Socket sc) {
        try{
            this.sc = sc;
            bis = new BufferedInputStream(sc.getInputStream());
            bos = new BufferedOutputStream(sc.getOutputStream());
        }catch(IOException e){}
    }
    public static void main(String[] args) {
        try{
            while (true) {
                ServerSocket ssc = new ServerSocket(80);
                new MyWebServer(ssc.accept()).start();
            }
        }catch(IOException e){}
    }
    public void run() {
        try {
            int readLength;
            byte[] data = new byte[1000];
            readLength = bis.read(data, 0, 1000);
            st = new StringTokenizer(new String(data, 0, readLength), "\r\n");

            bos.write("HTTP/1.1 200 OK\r\n".getBytes());
            bos.write("Content-Type: text/html; charset=utf-8\r\n".getBytes());
            bos.write("\r\n".getBytes());
            bos.write("<!DOCTYPE HTML>".getBytes());
            bos.write("<HTML><HEAD><meta charset='utf-8'><title>test</title></HEAD><BODY>".getBytes());
            while (st.hasMoreTokens()) {
                bos.write(st.nextToken().getBytes());
                bos.write("<br>".getBytes());
            }
            bos.write("</BODY></HTML>".getBytes());
            bos.flush();
            bos.close();
            sc.getOutputStream().close();
            sc.getInputStream().close();
            sc.close();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
