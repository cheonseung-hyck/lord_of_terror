import java.net.*;
import java.util.Scanner;
import java.io.*;

public class UDPChatter{

    public static int PORT = 38621;
    public static InetAddress host;
    public static ChatReceiver receiver;
    public static void main(String[] args){
        try{
            host = InetAddress.getByName(args[0]);
            receiver = new ChatReceiver();
            receiver.start();
            new ChatSender().start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
class ChatReceiver extends Thread{

    DatagramSocket socket;
    DatagramPacket packet;
    public static int length = 1024;
    byte[] data;
    
    public void run(){
        try{
            data = new byte[length];
            socket = new DatagramSocket(UDPChatter.PORT);
            while(true){

                socket.receive(packet = new DatagramPacket(data,0,length));
                System.out.println("Stranger : "+ new String(packet.getData(),0,packet.getLength()));
            }
        }
        catch(IOException e){
            System.out.println("--connection closed--");
        }
    }

}
class ChatSender extends Thread{

    DatagramPacket packet;
    DatagramSocket socket;
    Scanner scanner;
    byte[] data;
    String msg;

    public void run(){
        try{
            scanner = new Scanner(System.in);
            socket = new DatagramSocket();
            System.out.print("you : ");
            while(true){
                
                if((msg=scanner.nextLine()).equals("quit"))break;
                System.out.print("you : ");
                socket.send(new DatagramPacket(msg.getBytes(),0,msg.getBytes().length,UDPChatter.host,UDPChatter.PORT));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        UDPChatter.receiver.socket.close();
        UDPChatter.receiver.interrupt();
        scanner.close();

    }

}