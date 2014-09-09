/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bunukopoi
 */
public class TestingData implements TestDataModel{
    private ArrayList <TestDataFormat> dataList = new ArrayList<TestDataFormat>();
    
    public TestingData(String filename){
        File file = new File(filename);
        String [] tempList;
        
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader input = new BufferedReader(fr);
            String line;
            int row = 0;
            while((line = input.readLine()) != null){
                TestDataFormat tempData = new TestDataFormat();
                tempList = line.split(" ");
                int length = tempList.length;
                tempData.initialize(length - 1);
                for(int i = 0; i < length - 1; i++){
                    tempData.data[i] = Double.valueOf(tempList[i]);
                }
                
                tempData.label = Double.valueOf(tempList[length - 1]);
                
                dataList.add(tempData);
                //System.out.println(dataList.size());
            }
           
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(TestingData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TestDataFormat getData(int index) {
        return dataList.get(index);
    }

    @Override
    public int getDataSize() {
        return dataList.size();
    }
    
    public int getRecordSize(){
        return dataList.get(0).getDataSize();
    }
}
