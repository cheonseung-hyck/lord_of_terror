import java.util.Scanner;
import java.util.Vector;
import java.io.*;

public class TypingPractice{


    public static void main(String[] args){

        try{
            Scanner scanner = new Scanner(System.in);
            Scanner fscanner = new Scanner(new FileInputStream(new File(args[0])));
            Vector<String> sentences = new Vector<String>();
            while(fscanner.hasNextLine()){
                sentences.add(fscanner.nextLine());
            }
            int sentenceSize=sentences.size();
            double averageAccuracy=0;
            double averageSpeed=0;
            while(sentences.size()>0){
                int index = (int)(Math.random()*sentences.size());
                String sentence = sentences.get(index);
                System.out.println(sentence);
                long startTime = System.currentTimeMillis();
                String input = scanner.nextLine();
                double accuracy = 100;

                int maxLength=sentence.length()>input.length()?sentence.length():input.length();
                int minLength=sentence.length()<input.length()?sentence.length():input.length();
                double unitAccuracy = 1.0/maxLength*100;
                for(int i=0;i<maxLength;i++){
                    
                    if(i>=minLength||sentence.charAt(i)!=input.charAt(i))
                        accuracy-=unitAccuracy;
                }
                long time = (System.currentTimeMillis()-startTime)/1000;
                System.out.println("your typing speed : "+input.length()*1.0/time+"type(s) per second");
                System.out.println("your typing accuracy : "+accuracy+"%");

                sentences.remove(index);
                averageAccuracy+=accuracy;
                averageSpeed += input.length()*1.0/time;
            }
            averageAccuracy/=sentenceSize;
            averageSpeed/=sentenceSize;
            System.out.println("you've got all sentences!!! Excellent!!");
            System.out.println("your average accuracy : "+averageAccuracy+"%");
            System.out.println("your average speed :  "+averageSpeed+"type(s) per second("+averageSpeed*60+"per minute)");

            scanner.close();
            fscanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
}