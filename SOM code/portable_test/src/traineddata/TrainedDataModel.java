package traineddata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THE BOSS
 */
public class TrainedDataModel {
    Map BMUMap;
    
    public TrainedDataModel(String filename){
        BMUMap = new HashMap();
        File file = new File(filename);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader input = new BufferedReader(reader);
            
            input.readLine();
            input.readLine();
            String line;
            int node;
            double sd, mean;
            while ((line = input.readLine()) != null){
                String [] temp = line.split("\t");
                node = Integer.valueOf(temp[0]);
                mean = Double.valueOf(temp[1]);
                sd = Double.valueOf(temp[2]);
                ArrayList mean_sd = new ArrayList();
                mean_sd.add(mean);
                mean_sd.add(sd);
                BMUMap.put(node, mean_sd);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(TrainedDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Iterator it = BMUMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry en = (Map.Entry) it.next();
            ArrayList temp = new ArrayList();
            temp = (ArrayList) en.getValue();
            //System.out.println("Node = " + en.getKey() + " Mean = " + temp.get(0) + " SD = " + temp.get(1));
        }
    }

    public Map getBMUMap() {
        return BMUMap;
    }
    
    
    
    
}
