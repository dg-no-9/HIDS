package networkmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import neuron.KohonenNeuron;
import neuron.NeuronModel;
import topology.TopologyModel;

public class DefaultNetwork implements NetworkModel{
   
    private NeuronModel[] neuronList;
   
    private TopologyModel topology;
    
    private Map BMUMap;
    
    private Map Mean_SD;
    
    public DefaultNetwork(int weightNumber, double[] maxWeight, TopologyModel topology) {
        this.topology = topology;
        int numberOfNeurons = topology.getNumbersOfNeurons();
        neuronList = new KohonenNeuron[numberOfNeurons];
        for (int i=0; i<numberOfNeurons; i++){
            neuronList[i] = new KohonenNeuron(weightNumber,maxWeight);
        }
        
        BMUMap = new HashMap();
        Mean_SD = new HashMap();
    }
   
    public DefaultNetwork(String fileName, TopologyModel topology){
        File file = new File(fileName);
        int neuronNumber = topology.getNumbersOfNeurons();
        neuronList = new KohonenNeuron[neuronNumber];
        String[] tempTable;
        double[] tempList;
        int rows = 0;
        try{
            FileReader fr = new FileReader(file);
            BufferedReader input = new BufferedReader(fr);
            String line;
            System.out.println("Data from: \"" + fileName + "\" are importing...");
            while((line = input.readLine()) != null){
                tempTable = line.split("\t");
                int tableLenght = tempTable.length;
                tempList = new double[tableLenght];
                for(int i = 0; i< tableLenght; i++){
                    tempList[i] = Double.valueOf(tempTable[i]);
                }
                neuronList[rows] = new KohonenNeuron(tempList);
                rows ++;
             }
            fr.close();
            System.out.println(rows + " rows was imported");
        }catch(IOException e){
            System.out.println("File can not be read!. Error: " + e);
        }
        this.topology = topology;
    }
 
    @Override
    public NeuronModel getNeuron(int neuronNumber) {
        return neuronList[neuronNumber];
    }
 
    @Override
    public int getNumbersOfNeurons() {
        return neuronList.length;
    }
   
    @Override
    public TopologyModel getTopology() {
        return topology;
    }
   
    @Override
    public void setTopology(TopologyModel topology){
        this.topology = topology;
    }
 
    @Override
    public String toString(){
        String text = "";
        int networkSize = neuronList.length;
        for (int i=0; i< networkSize; i++ ){
            text +="Neuron number "+ (i + 1) + ": " +  neuronList[i];
            if(i < networkSize-1){
                text += "\n";
            }
        }
        return text;
    }
   
    @Override
    public void networkToFile(String fileName){
        File outFile =  new File(fileName);
        String weightList;
        double[] weight;
        try{
        FileWriter fw = new FileWriter(outFile);
        PrintWriter pw = new PrintWriter(fw);
        int networkSize = neuronList.length;
        for (int i=0; i< networkSize; i++ ){
            weightList ="";
            weight = neuronList[i].getWeight();
            for (int j=0; j< weight.length; j++){
                weightList += (weight[j]);
                if (j < weight.length -1){
                    weightList += "\t";
                }
            }
            pw.println(weightList);
        }
        fw.close();
        }catch(IOException e){
            System.out.println("File can not be read!. Error: " + e);
        }
    }

    @Override
    public HashMap getBMUMap() {
        return (HashMap) BMUMap;
    }

    @Override
    public void setBMUMap(HashMap BMUMap) {
        this.BMUMap = BMUMap;
    }
    
    
    @Override
    public void AmmendBMUList(){
        Iterator it = BMUMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            ArrayList temp = (ArrayList) entry.getValue();
            if (temp.size() < 9){
                //BMUMap.remove(entry.getKey());
            }
            
            else{
                int starting = 9;
                double sum = 0.0;
                for (int i = starting; i < temp.size(); i++){
                    sum += (Double) temp.get(i);
                }
                
                double mean = sum / (temp.size() - starting);
                double sd = 0.0;
                for (int i = starting; i < temp.size(); i++){
                    sd = (mean - (Double) temp.get(i)) * (mean - (Double) temp.get(i));
                }
                sd = Math.sqrt(sd) / (temp.size() - starting);
                
                ArrayList list = new ArrayList();
                list.add(mean);
                list.add(sd);
                Mean_SD.put(entry.getKey(), list);
            }
        }
    }
    
    @Override
    public void BMUListToText(){
        Iterator it = Mean_SD.entrySet().iterator();
        File file = new File("datasets/trainednetwork/mean-sd.txt");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Best Matchung Nodes:\tMean\t\tStandard Deviation\n");
        
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                
                ArrayList temp = new ArrayList();
                temp = (ArrayList) entry.getValue();
                
                //System.out.println("BMU = " + entry.getKey() + " Mean, SD = " + entry.getValue());
                String line = entry.getKey() + "\t" + temp.get(0) + "\t" + temp.get(1) + "\n";
                //System.out.println(line);
                
                writer.write(line);
            }
            
            writer.close();
        
        } catch (IOException ex) {
            Logger.getLogger(DefaultNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
