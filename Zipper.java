import java.util.zip.*;
import java.io.*;

public class Zipper {

    static FileInputStream fis;
    static ZipOutputStream zos;

    public static void main(String[] args) {
        try {
            zos = new ZipOutputStream(new FileOutputStream(new File(args[1])));
            File curFile = new File(args[0]);

            if (curFile.isFile()) {
                zipFile(curFile,"");
            } else if (curFile.isDirectory()) {
                for(File f:curFile.listFiles(new FilenameFilter(){

                    public boolean accept(File f, String s){
                        
                        return !s.endsWith("zip");
                    }
                })){
                    if(f.isFile()){
                        zipFile(f,"");
                    }
                    else if(f.isDirectory()){
                        searchDirectory(f,"");
                    }
                }
            }
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFile(File curFile, String path) throws IOException {
        System.out.print("current file : "+ curFile.getName()+" ");
        fis = new FileInputStream(curFile);
        zos.putNextEntry(new ZipEntry(path+curFile.getName()));
        byte[] tmp = new byte[1024];
        int nOfRead;
        int max_read=0;
        while ((nOfRead = fis.read(tmp)) > 0) {
            zos.write(tmp, 0, nOfRead);
            max_read+=nOfRead;
        }
        System.out.println(max_read+" byte(s)");
        fis.close();
        zos.closeEntry();
    }

    public static void searchDirectory(File curFile,String path) throws IOException {
        String cur_path = path+curFile.getName()+'\\';
        zos.putNextEntry(new ZipEntry(cur_path));
        for (File f : curFile.listFiles(new FilenameFilter() {

            public boolean accept(File f, String s) {
                return !s.endsWith(".zip");
            }
        })) {
            if (f.isFile()) {
                zipFile(f,cur_path);
            } else if (f.isDirectory()) {
                searchDirectory(f,cur_path);
            }
        }
    }
}
