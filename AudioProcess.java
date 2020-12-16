import java.io.*;
import java.util.ArrayList;

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
        //trainData(new File("./data/musicdata.csv"));
        System.out.println(getMood(new File("a.wav")));
    }

    public static ArrayList<String> getFeatures(File f){
        try{
            ArrayList<String> resList = new ArrayList<String>();
            String fileName = f.getName();

            if(!(fileName.endsWith("wav")||fileName.endsWith("mp3")))
                return null;

            Process p = Runtime.getRuntime().exec("py FeatureExtract.py "+f.getCanonicalPath());
            char c=' ';
            StringBuilder result = new StringBuilder();
            boolean flag=false;;
            while(true){
                if(p.getInputStream().available()>0){
                    c = (char)p.getInputStream().read();
                    if(c=='#'){
                        break;
                    }
                    if(c=='?'){
                        flag=false;
                        resList.add(result.toString());
                        result.delete(0,result.length());
                    }
                    
                    if(flag){
                        result.append(c);
                    }
                    
                    if(c=='!'){
                        flag=true;
                    }
                }
            }
            return resList;
         }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void trainData(File file){

        VersatileDataSource source = new CSVDataSource(file, true, CSVFormat.DECIMAL_POINT);
        VersatileMLDataSet data = new VersatileMLDataSet(source);
        data.defineSourceColumn("zero_crossing_rate",0,ColumnType.continuous);
        for(int i=0;i<20;i++){
            data.defineSourceColumn("mfcc"+(i+1),i+1,ColumnType.continuous);
        }
        ColumnDefinition outputColumn = data.defineSourceColumn("label", 21, ColumnType.nominal);
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

       
        String[] result = getFeatures(file).toArray(new String[21]);
        
        if(helper==null||bestMethod==null)
            loadModel();

        MLData input = helper.allocateInputVector();
        helper.normalizeInputVector(result,input.getData(),false);
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