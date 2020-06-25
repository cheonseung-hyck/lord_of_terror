import java.util.ArrayList;
import java.util.Stack;

public class HtmlParser {



    static String[] notGoodTag = {"del", "s"};
    
    public static boolean isNotGoodTag(String tagName){

        for(String s:notGoodTag){
            if(tagName.equals(s))
                return true;
        }
        return false;
    }

    public static String deleteTags(String str) {

        Stack<String> stack = new Stack<String>();
        stack.push("nothing");
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        char tmp;

        for(int i=0;i<str.length();i++){

            if(str.charAt(i)=='<'){
                flag=false;
                if(str.charAt(i+1)!='/'){
                    int endIndex = Math.min(str.indexOf(" ",i),str.indexOf(">",i));
                    stack.push(str.substring(i+1,endIndex));
                }
                else{
                    stack.pop();
                }
            }
                
            if (flag)
                sb.append(str.charAt(i));
            else if(stack.peek().equals("img")){
                if(str.indexOf("class=\"Emoji Emoji--forText\"",i)>=0){
                    i = str.indexOf("title=\"",i)+7;
                    while((tmp=str.charAt(i++))!='"'){
                        sb.append(tmp);
                    }
                }
            }

            if(str.charAt(i)=='>'&&!isNotGoodTag(stack.peek()))
                flag=true;         
        }
        return new String(sb);
    }
    
    public static String deleteNonCharacters(String str){

        StringBuilder sb = new StringBuilder();

        boolean flag=true;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='&'){
                flag=false;
            }

            if(flag){
                sb.append(str.charAt(i));
            }

            if(str.charAt(i)==';'){
                flag=true;
            } 
        }
        return new String(sb);
    }

    public static ArrayList<String> getWords(StringBuilder html) throws Exception{
        System.out.println("Extracting your Writings from posts...");
        ArrayList<String> res = new ArrayList<String>();
        System.out.println(html);
        int tmpIndex, startIndex, endIndex=0;
        for (int i = 0; i < 5; i++) {
            
            String s = "<div class=\"js-tweet-text-container\">";

            if(html.indexOf(s)<0)
                break;
            
            tmpIndex = html.indexOf(s,endIndex) + s.length();
            startIndex = html.indexOf(">", tmpIndex + 1) + 1;
            endIndex = html.indexOf("</p>", startIndex + 1);

            String result = html.substring(startIndex, endIndex);

            res.add(deleteNonCharacters(deleteTags(result)));

        }
        if(res.size()==0)
            throw new Exception("no twit found on your account");
        return res;
    }

}