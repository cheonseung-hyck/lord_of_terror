import java.util.Scanner;

 class Node{

    String data;
    boolean isLeafNode;

    Node left,right;
}

class Tree{

    Node root;

    public Tree(String question,String yAnswer,String nAnswer){
        root = new Node();
        root.isLeafNode = false;
        root.data = nAnswer;
        insert(root,question,yAnswer);
        
    }
    public void insert(Node location, String question, String expectedAnswer){

        String wrongAnswer = location.data;
        location.isLeafNode = false;
        location.data = question;
        
        Node leftNode = new Node();
        Node rightNode = new Node();
        
        leftNode.data = expectedAnswer;
        leftNode.isLeafNode = true;

        rightNode.data = wrongAnswer;
        rightNode.isLeafNode = true;

        location.left=leftNode;
        location.right=rightNode;

    }

    public void search(){

        Node snode = root;
        String answer;
        while(true){
            if(snode.isLeafNode){
                System.out.println("is you answer '"+snode.data+"'?");
                if((answer=Akinator.scanner.nextLine()).equals("yes")){
                    System.out.println("gotcha!!");
                }
                else if(answer.equals("no")){
                    String question, value;
                    System.out.println("uhhh... then what are you looking for?");
                    value = Akinator.scanner.nextLine();
                    System.out.println("can you tell me a sentence which best describes your answer");
                    question = Akinator.scanner.nextLine();
                    insert(snode,question,value);
                }
                break;
            }
            else{
                System.out.println(snode.data+" (yes or no)");
                answer=Akinator.scanner.nextLine();
                if(answer.equals("yes")){
                    snode=snode.left;
                }
                else if(answer.equals("no")){
                    snode=snode.right;
                }
            }

        }
    }
}
public class Akinator{

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){

        Tree akinator = new Tree("it is a human that really exists","Michel","Kha'zix");

        while(true){
            akinator.search();
            System.out.println("are you going to play again? i'm sure that i can get it now...");
            if(scanner.nextLine().equals("no"))
                break;
            else
                System.out.println("let's start again!!!");
        }

    }
}