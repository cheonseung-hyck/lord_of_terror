import java.io.*;

public class My_readInt {

    public static int myReadInt(InputStream in) throws Exception {
        int max_int_length = (int) Math.log10(Integer.MAX_VALUE) + 1;
        boolean is_negative=false;
        int result = 0;
        char c;
        while((c = (char)in.read())==' ');
        for (int i=0; i<max_int_length; i++) {

            if(c=='-'&&i==0){
                is_negative=true;
                max_int_length++;
                c = (char)in.read();
                continue;
            }
            else if(c==' '||c=='\r')break;
            else if(c<'0'||c>'9')throw new Exception();

            result *= 10;
            result += (int) (c - '0');

            c = (char)in.read();
        }
        if(is_negative==true)result*=-1;
        if(c=='\r')in.read();
        return result;
    }

    public static void main(String[] args)throws Exception {

        System.out.print("실행할 횟수 : ");
        int loopCount = myReadInt(System.in);
        for(int i=0;i<loopCount;i++){
            System.out.print("입력할 정수 : ");
            System.out.println("my integer = "+myReadInt(System.in));
        }
    }
}
