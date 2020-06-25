import java.io.*;

import org.encog.ConsoleStatusReportable;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;

public class AudioProcess {
    
    static NormalizationHelper helper;
    static MLRegression bestMethod;
    public static void main(String[] args){
        File file = new File(args[0]);
        System.out.println("tempo : "+getTempo(file));
        System.out.println("Chord : "+getChord(file));
    }

    public static int getTempo(File f){

        try{
            String fileName = f.getName();

            if(!fileName.endsWith("wav"))
                return -1;

            Process p = Runtime.getRuntime().exec(".\\marsyas-0.5.0\\bin\\tempo.exe "+f.getCanonicalPath());

            char c=' ';
            String str = new String();
            boolean flag=false;
            while(true){
                if(p.getInputStream().available()>0){
                   if(flag&&c=='\r'){
                        return new Integer(str.trim());
                   }

                   if(flag)
                        str = str + c; 

                    if((c = (char)p.getInputStream().read())=='='){
                        c = (char)p.getInputStream().read();
                        flag=true;
                   }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static String getChord(File f){
        try{
            String fileName = f.getName();

            if(!fileName.endsWith("wav"))
                return null;

            Process p = Runtime.getRuntime().exec(".\\marsyas-0.5.0\\bin\\pitchextract.exe -m key "+f.getCanonicalPath());
            char c=' ';
            String result = new String();
            boolean flag=false;;
            while(true){
                if(p.getInputStream().available()>0){
                    c = (char)p.getInputStream().read();
                    if(c=='=')
                        flag=true;
                   if(flag&&"ABCDEFG".indexOf(c)>0){
                       result += c;
                       if((c=(char)p.getInputStream().read())!='\t')
                            result += c;
                        result += " ";
                        while((c=(char)p.getInputStream().read())!='\r'){
                            if(c!='\t')
                                result += c;
                        }
                        break;
                   }
                }
            }
            return result;
         }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void trainData(File file){

        VersatileDataSource source = new CSVDataSource(file, false, CSVFormat.EG_FORMAT);
        VersatileMLDataSet data = new VersatileMLDataSet(source);
        data.defineSourceColumn("tempo",0,ColumnType.continuous);
        data.defineSourceColumn("chord-a",1,ColumnType.nominal);
        data.defineSourceColumn("chord-b",2,ColumnType.nominal);
        ColumnDefinition outputColumn = data.defineSourceColumn("mood", 3, ColumnType.nominal);
        data.analyze();

        data.defineSingleOutputOthersInput(outputColumn);
        EncogModel model = new EncogModel(data);
        model.selectMethod(data,MLMethodFactory.TYPE_FEEDFORWARD);
        model.setReport(new ConsoleStatusReportable());
        data.normalize();

        model.holdBackValidation(0.3,true,1001);
        model.selectTrainingType(data);
        bestMethod = (MLRegression)model.crossvalidate(5,true);

        helper = data.getNormHelper();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".\\data\\object.dat"));
            oos.writeObject(helper);
            oos.writeObject(bestMethod);

            oos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String getMood(File file){

        int tempo;
        String[] chord;
        String chordA, chordB;

        tempo = getTempo(file);
        chord = getChord(file).split(" ");
        
        chordA = chord[0];
        chordB = chord[1];
        
        if(helper==null||bestMethod==null)
            loadModel();

        MLData input = helper.allocateInputVector();
        helper.normalizeInputVector(new String[]{String.valueOf(tempo),chordA,chordB},input.getData(),false);
        MLData output = bestMethod.compute(input);
        return helper.denormalizeOutputVectorToString(output)[0];
    }

    public static void loadModel(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(".\\data\\object.dat")));
            helper = (NormalizationHelper) ois.readObject();
            bestMethod = (MLRegression) ois.readObject();

            ois.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}