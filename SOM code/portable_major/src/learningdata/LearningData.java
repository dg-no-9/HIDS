package learningdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LearningData implements LearningDataModel{
   
    ArrayList <double[]> dataList = new ArrayList<double[]>();
   
    public LearningData(String fileName){
        File file = new File(fileName);
        String[] tempTable;
        double[] tempList;
        int rows = 0;
        try{
            FileReader fr = new FileReader(file);
            BufferedReader input = new BufferedReader(fr);
            String line;
            System.out.println("Data from: \"" + fileName + "\" are importing...");
            while((line = input.readLine()) != null){
                rows ++;
                tempTable = line.split(" ");
                int tableLenght = tempTable.length - 1; //64 entry in each row is the level
                tempList = new double[tableLenght];
                for(int i = 0; i< tableLenght; i++){
                    tempList[i] = Double.valueOf(tempTable[i]);
                }
                dataList.add(tempList);     
             }
            fr.close();
            System.out.println(rows + " rows was imported");
        }catch(IOException e){
            System.out.println("File can not be read!. Error: " + e);
        }
    }
   
    @Override
    public double[] getData(int index){
        return dataList.get(index);
    }
   
    @Override
    public String toString(){
        String text="";
        int dataSize = dataList.size();
        double [] vector;
        int vectorSize;
        for (int i=0; i<dataSize; i++){
            text += "[";
            vector = dataList.get(i);
            vectorSize = vector.length;
            for(int j=0; j<vectorSize; j++){
                text += vector[j];
                if( j < vectorSize-1){
                    text += ", ";
                }
            }
            text += "]" + "\n";
        }
        return text;
    }
    
    @Override
    public int getDataSize(){
        return dataList.size();
    }
    
    public int getVectorSize(){
        return dataList.get(0).length;
    }
    
}
