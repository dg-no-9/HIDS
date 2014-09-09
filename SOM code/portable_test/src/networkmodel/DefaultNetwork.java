package networkmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import neuron.KohonenNeuron;
import neuron.NeuronModel;
import topology.TopologyModel;

public class DefaultNetwork implements NetworkModel{
   
    private NeuronModel[] neuronList;
   
    private TopologyModel topology;
    
    public DefaultNetwork(int weightNumber, double[] maxWeight, TopologyModel topology) {
        this.topology = topology;
        int numberOfNeurons = topology.getNumbersOfNeurons();
        neuronList = new KohonenNeuron[numberOfNeurons];
        for (int i=0; i<numberOfNeurons; i++){
            neuronList[i] = new KohonenNeuron(weightNumber,maxWeight);
        }
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
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(3);
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
}
