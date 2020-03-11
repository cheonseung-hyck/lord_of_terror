import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class WordChain{

    public static Scanner scanner;
    public static Scanner fscanner;
    public static Vector<String> usedData;
    public static String name;

    public static String search(String s)throws Exception{

        String lastWord = Character.toString(s.charAt(s.length()-1));
        int a = Collections.binarySearch(usedData,lastWord);
        int b = Collections.binarySearch(usedData,Character.toString((char)(lastWord.charAt(0)+1)));
        int c = (int)(a+Math.random()*(b-a));
        if(c<0)c=c*(-1)-1;
        String result="";
        if(usedData.get(c).charAt(0)==lastWord.charAt(0)){
            result=usedData.get(c);
        }
        else throw new Exception("player wins!!");
        
        usedData.remove(result);
        return result;
    }
    public static boolean isValid(String lastWord, String nextWord) throws Exception{


        if(!usedData.contains(nextWord)){
            throw new Exception("the word not exists! you lose!");
        }

        char lastCharacter = lastWord.charAt(lastWord.length()-1);

        return lastCharacter==nextWord.charAt(0);
    }

    public static void main(String[] args){
        try{
            scanner = new Scanner(System.in);
            fscanner = new Scanner(new FileInputStream(new File(args[0])));
            usedData = new Vector<String>();

            while(fscanner.hasNext()){
                usedData.add(fscanner.next());
            }

            Collections.sort(usedData);
            String lastWord=usedData.get((int)(Math.random()*usedData.size()));

            System.out.println("Welocme to WordChain!!");
            System.out.print("Enter Your Name : ");
            name=scanner.nextLine();
            while(true){
                System.out.println("Computer : "+(lastWord=search(lastWord)));
                System.out.print(name+" : ");
                if(isValid(lastWord,lastWord=scanner.nextLine())){
                    usedData.remove(lastWord);
                }
                else{
                    throw new Exception("it is out of rule! you lose!");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}