
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
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


    public void setDirectory(JFrame cp, JLabel jl)throws Exception{
        try{
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("."));
            jfc.setDialogTitle("Select Music Folder...");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.showOpenDialog(cp);
            directory = jfc.getSelectedFile();
            jl.setText(directory.getPath());
        }
        catch(IndexOutOfBoundsException e){
            throw new Exception("no matched music!!");
        }
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
    public String getCurrentMood()throws Exception{

        ArrayList<String> texts = new ArrayList<String>();
        for(int i=1;i<6;i++){
            StringBuilder sb = new StringBuilder();
            InputStreamReader fr = new InputStreamReader(new FileInputStream(new File("./data/post_"+i+".txt")),"utf-8");
            int c;
            while((c=fr.read())!=-1){
                sb.append((char)c);
            }
            texts.add(sb.toString());
        }
        System.out.println(twitID);
        String mood = Word.getResult(texts);
        this.mood=mood;
        System.out.println("Text Analyzing Result : "+mood);

        return mood;
    }
    public ArrayList<File> getRecommendedFile()throws Exception{
        FileWriter fw;
        if (directory==null){
            return null;
        }
        if(!new File("./data/metadata.json").exists()){
            
            JSONObject jo = new JSONObject();
            jo.put("count","0");
            jo.writeJSONString(fw=new FileWriter(new File("./data/metadata.json")));
            fw.close();
        }
        ArrayList<File> resFiles = new ArrayList<File>();
        String txt_mood = getCurrentMood();
        JSONObject jo_all = readMetaFile();
        int count = new Integer((String)jo_all.get("count"));

        for(int i=0;i<count;i++){
            JSONObject jo_target;
            if((jo_target=(JSONObject)jo_all.get("d"+i)).get("name").equals(directory.getCanonicalPath())){
                if(jo_target.get("hashcode").equals(String.valueOf(getHash(directory.listFiles())))){
                    JSONArray ja = (JSONArray)(jo_target.get(txt_mood));
                    
                    if(ja.size()==0) throw new Exception("no matched result...");
                    
                    for(int j=0;j<ja.size();j++){
                        resFiles.add(new File((String)ja.get(j)));
                    }
                    return resFiles;
                }
                else{
                    
                    
                    for(int j=i;j<count-1;j++){
                        jo_all.replace("d"+i, jo_all.get("d"+(i+1)));
                    }
                    jo_all.remove("d"+(count-1));
                    jo_all.replace("count",String.valueOf(count-1));
                    jo_all.writeJSONString(fw=new FileWriter(new File("./data/metadata.json")));
                    mu.showMessage("some changes was made on this directory... ");
                    fw.close();
                    break;
                }   
            }
        }
        makeMetaFile();
        throw new Exception("analyze is done... try it again..");
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
    public void makeMetaFile()throws Exception{
        try{
            mu.showMessage("it takes several minutes analyzing your files...");
            File metaFile;
            JSONParser jp = new JSONParser();
            int count=0;
            JSONObject jo, jo_target;
            FileWriter fw;
            if(!(metaFile=new File("./data/metadata.json")).exists()){
                metaFile.createNewFile();
                jo = new JSONObject();
                jo.put("count","0");
                jo.writeJSONString(fw=new FileWriter(metaFile));
                fw.close();
            }
            else{
                jo = readMetaFile();
                count = new Integer((String)jo.get("count"));
                
            }
            StringBuilder contents = new StringBuilder();
            
            JSONArray ja_happy, ja_sad, ja_calm, ja_energetic;
            
            jo.put("d"+count,jo_target=new JSONObject());
            jo_target.put("hashcode",getHash(directory.listFiles()));
            jo_target.put("name",directory.getCanonicalPath());
            jo_target.put("happy",ja_happy=new JSONArray());
            jo_target.put("sad",ja_sad=new JSONArray());
            jo_target.put("calm",ja_calm=new JSONArray());
            jo_target.put("energetic",ja_energetic=new JSONArray());

            
            for(File ff:directory.listFiles()){
                String mood = AudioProcess.getMood(ff);
                if(mood.equals("happy")){
                    ja_happy.add(ff.getCanonicalPath());
                }
                else if(mood.equals("sad")){
                    ja_sad.add(ff.getCanonicalPath());
                }
                else if(mood.equals("calm")){
                    ja_calm.add(ff.getCanonicalPath());
                }
                else if(mood.equals("energetic")){
                    ja_energetic.add(ff.getCanonicalPath());
                }
            }
            count++;
            jo.replace("count", String.valueOf(count));

            jo.writeJSONString(fw=new FileWriter(metaFile));
            fw.close();
            
        }catch(Exception e){
            throw new Exception("error occured when creating metafile.");
        }
    }
    public JSONObject readMetaFile()throws Exception{
        JSONParser js=null;
        JSONObject jo=null;
        File f = new File("./data/metadata.json");
        try{

            StringBuilder metaContent = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
            
            String tmp;
            while((tmp=br.readLine())!=null){
                metaContent.append(tmp);
            }
            js = new JSONParser();
            jo = (JSONObject) js.parse(metaContent.toString());
        }catch(Exception e){
            throw new Exception("error occured in reading metadata");
        }
        return jo;
    }
    public String getHash(File[] f)throws Exception{
        StringBuilder sb = new StringBuilder();
        BufferedReader br; 
        try{
            for(File ff:f){
                br = new BufferedReader(new InputStreamReader(new FileInputStream(ff),"utf-8"));
                String tmp;
                while((tmp=br.readLine())!=null){
                    sb.append(tmp);
                }
                br.close();
            }
            return String.valueOf(sb.toString().hashCode());
        }catch(Exception e){
            throw new Exception("error occured when hashing");
        }
    }
}