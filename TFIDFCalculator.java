import java.util.Arrays;
import java.util.List;

public class TFIDFCalculator {
   
    public static double tf(String doc, String term) {
        double result = 0;
        for(int i =0; i<doc.length(); i++) {
        	 if(doc.contains(term))
                result++;
        }
        return result / doc.length();
    }

   
    public static double idf(List<String> docs, String term) {
        double n = 0;
        for (String doc : docs) {
        	for(int i =0; i<doc.length(); i++) {
        		if(doc.contains(term)) {
                    n++;
                    break;
                }
            }
        }
        if(n<=0)
            return 0;
        double idf = Math.log(docs.size()/n);
        return idf;
        
    }

    
    public static double tfIdf(String doc, List<String> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }


}
