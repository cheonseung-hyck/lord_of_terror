
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class MainProgram {

    mainUI mu;
    MusicPlayer mplayer;
    String twitID;
    String mood=null;
    File directory;

	int music_num = -1;

	ArrayList<String> musicFileList = new ArrayList<String>();
    ArrayList<String> musicNameList = new ArrayList<String>();
    
    public MainProgram(mainUI mu, MusicPlayer mplayer){

        this.mu=mu;
        this.mplayer=mplayer;
    }
    public static void main(String[] args){

        mainUI mu = new mainUI();
        MusicPlayer mplayer = new MusicPlayer();
        MainProgram mainp = new MainProgram(mu,mplayer);
        
        mu.setPointer(mainp, mplayer);
        mplayer.setPointer(mainp, mu);
    }


    public void setDirectory(JFrame cp, JLabel jl){
        try{
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("."));
            jfc.setDialogTitle("Select Music Folder...");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showOpenDialog(cp);
            directory = jfc.getSelectedFile();
            jl.setText(directory.getPath());
        }
        catch(Exception e){}
    }
    public void setTwitId(JTextField tf){
        twitID = tf.getText();
    }

    public void loadMusicFiles() throws Exception{
        
        initializeData();
        music_num=0;
        ArrayList<File> fileList = new ArrayList<File>();
        for(File f:directory.listFiles()){
            fileList.add(f);
        }
        
        if(mood!=null){
            System.out.println(mood);
            ArrayList<File> recommended = getRecommendedFile();
            fileList.retainAll(recommended);
        }
        if(fileList.size() > 0){

            for(int i=0; i < fileList.size(); i++){ //all file
                  if(fileList.get(i).toString().toLowerCase().endsWith("wav")) { // wav file ***
                      musicFileList.add(music_num, fileList.get(i).toString()); // add music path data
                      musicNameList.add(music_num,fileList.get(i).getName()); // add music name data
                      music_num++;
                      
                  }
            }
         }
         System.out.println("loaded "+musicFileList.size()+" files...");
    }
    public void getCurrentMood()throws Exception{

        System.out.println(twitID);
        String mood = Word.getResult(HtmlParser.getWords(TwitConnector.getHtml(twitID)));
        this.mood=mood;
        System.out.println("Text Analyzing Result : "+mood);
    }
    public ArrayList<File> getRecommendedFile()throws Exception{
        if (directory==null){
            return null;
        }
        ArrayList<File> resFiles = new ArrayList<File>();

        for(File f:directory.listFiles(new FilenameFilter(){

            public boolean accept(File f, String s){

                if(s.endsWith("wav"))
                    return true;
                else
                    return false;
            }
        })){
            String musicResult = AudioProcess.getMood(f);
            if(musicResult.equals(mood)){
                resFiles.add(f);
            }
            
            if(resFiles.size()==0)
                System.out.println("Not matched ... "+f.getName()+" : "+musicResult);
            else
                System.out.println("Matched!! :"+f.getName());
        }
        return resFiles;
    }
    public void initializeData(){

        mplayer.clip=null;
        mplayer.music_now=0;
        mplayer.currentMusic=null;
        mplayer.lastPoint=0;
        musicFileList.clear();
        musicNameList.clear();
        music_num=0;
    }
}