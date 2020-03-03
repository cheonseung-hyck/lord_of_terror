import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyConnect{

    static BufferedInputStream bis;
    static BufferedOutputStream bos;

    public static void main(String[] args){
        try{
            String hostName = args[0];
            int port = Integer.parseInt(args[1]);
            Scanner sc = new Scanner(System.in);

            Socket socket = new Socket(hostName, port);
            System.out.println("successfully connected to "+hostName);
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());

            String msg;
            int n=0;
            byte arr[] = new byte[100];
            System.out.print("host: ");
            while((msg=sc.nextLine())!="quit"){
                bos.write((msg+"\r\n").getBytes());
                bos.flush();
                while((n=bis.available())>0){
                    while(n>0){
                        int tmp = n>=100?100:n;
                        bis.read(arr,0,tmp);
                        System.out.print(new String(arr,0,tmp));
                        n-=100;
                    }
                }
                System.out.print("host: ");
            }
            socket.close();
            sc.close();
        }
        catch(IOException e){
            System.out.println("something bad happens!");
        }
    }
}