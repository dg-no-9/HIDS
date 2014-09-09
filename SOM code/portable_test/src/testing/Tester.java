/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metric.MetricModel;
import networkmodel.NetworkModel;
import neuron.NeuronModel;
import traineddata.TrainedDataModel;

/**
 *
 * @author bunukopoi
 */

class ROCData{
        int TP = 0, FP = 0, TN = 0, FN = 0;
}

public class Tester {
    private NetworkModel network;
    private TestDataModel testData;
    private MetricModel metric;
    private TrainedDataModel trainedData;
    private boolean labelled;
    
    public enum result {TP, FP, TN, FN};
    
    private int threshold;
    
   List <ROCData> graphData = new ArrayList<ROCData>();
   
   FileWriter writer;

    public Tester(NetworkModel network, MetricModel metric, TestDataModel testData, TrainedDataModel trainedData) {
        this.network = network;
        this.metric = metric;
        this.testData = testData;
        this.trainedData = trainedData;
        try {
            writer = new FileWriter("nodes.txt");
        } catch (IOException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isLabelled() {
        return labelled;
    }

    public MetricModel getMetric() {
        return metric;
    }

    public NetworkModel getNetwork() {
        return network;
    }

    public TestDataModel getTestData() {
        return testData;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setLabelled(boolean labelled) {
        this.labelled = labelled;
    }

    public void setMetric(MetricModel metric) {
        this.metric = metric;
    }

    public void setNetwork(NetworkModel network) {
        this.network = network;
    }

    public void setTestData(TestDataModel testData) {
        this.testData = testData;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;//using squared disance
    }
    
    public result testData(TestDataFormat aData){
        String dn = this.getBestDistance(aData.data); //dist = {bestdistance + bestneuron}
        String [] temp = dn.split("\t");
        
        double dist = Double.valueOf(temp[0]);
        int bestNode = Integer.valueOf(temp[1]);
        
        try {
            writer.write(bestNode + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Distance = " + dist + "bestNode = " + bestNode);
        
        double label = aData.label;
        result result = null;
        boolean map_result, label_result;
        
        ArrayList value = new ArrayList();
        value = (ArrayList) trainedData.getBMUMap().get(bestNode);
        //if (trainedData.getBMUMap().containsKey(bestNode)) {
            if (dist <= this.threshold)
                map_result = false;
            else 
                map_result = true;
//            if (dist <= 2 * (Double)value.get(0) )// && dist >= 2 * ((Double)value.get(0) - (Double)value.get(1))) 
//                map_result = false; 
//            else
//                map_result = true;
       // }
        //else map_result = true;
        
        if (label == 0.0) label_result = false; else label_result = true;
        
        if (map_result) {
            if (label_result) result = result.TP;//map le intrusion ho ra label le ni tei bhancha
            else result = result.FP;//
        }
        else {
            if (label_result) result = result.FN;
            else result = result.TN;
        }

        //System.out.println("Dist = " + dist);
        return result;
    }

    public double maxDistance = 0.0;
    protected String getBestDistance(double [] vector){ //returns a string containing {bestdistance + bestneuron}}
        NeuronModel tempNeuron;
        double distance, bestDistance = -1;
        int networkSize = network.getNumbersOfNeurons();
        int bestNeuron = 0;
        for(int i=0; i < networkSize; i++){
            tempNeuron = network.getNeuron(i);
            if(tempNeuron != null){
                distance = metric.getDistance(tempNeuron.getWeight(), vector);
                if((distance < bestDistance) || (bestDistance == -1)){
                    bestDistance = distance;
                    bestNeuron = i;
                }
            }
        }
        if (bestDistance > maxDistance) maxDistance = bestDistance;
       return bestDistance + "\t" + bestNeuron;
    }
    
    public void generateROCdata(){
       System.out.println("Test Data being iterated..Be patient");
        System.out.println("REMENBER :: SLOW N STEADY WINS THE RACE!!");

       int start = 0;
       int end = 900;
       int size = testData.getDataSize();
       TestDataFormat aData = new TestDataFormat(testData.getDataSize());
       int index;
       int iteration;
       for (iteration = start, index = 0; iteration < end; iteration += 50, index++){
           this.setThreshold(iteration);
           graphData.add(new ROCData());
           for (int i = 0; i < size; i++){
               aData = testData.getData(i);
               result res = testData(aData);
               if (res == result.TP) graphData.get(index).TP++;
               if (res == result.FP) graphData.get(index).FP++;
               if (res == result.TN) graphData.get(index).TN++;
               if (res == result.FN) graphData.get(index).FN++;
               //System.out.println(" Iteration = " + index + " threshodl = " + iteration + " datanum = " + i);
           }
           System.out.println("Iteration Number = " + index + " Threshold = " + iteration + " TP = " + graphData.get(index).TP + " FP = " + graphData.get(index).FP + " FN = " + graphData.get(index).FN + " TN = " + graphData.get(index).TN);
       }
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
       System.out.println("Upper Limit = " + this.maxDistance);
       System.out.println("FInished testing the data..");
       System.out.println("Data for ROC curve is ready by now");
       
    }
    
    public void ROCdataTOtxt(String filename, String filename2){
        System.out.println("Writing ROC Data to file\"" + filename + "\"");
        System.out.println("Your patience is greatly admired..Have Fun!");
        File filetp = new File(filename);
        File filetn = new File(filename2);
        try {
            FileWriter fw = new FileWriter(filetp);
            FileWriter fw2 = new FileWriter(filetn);
            String line1 = null, line2 = null;
            for (int i = 0; i < graphData.size(); i++){
                ROCData data = graphData.get(i);
                line1 = data.FP / ((double)data.FP + data.TN) + "\t" + data.TP / (double)(data.TP + data.FN) + "\n";
                line2 = data.TN  / ((double)data.FP + data.TN) + "\t" + data.FN / (double)(data.TP + data.FN) + "\n";
                
                fw.write(line1);
                fw2.write(line2);
            }
            System.out.println("Thanks for your patience..");
            System.out.println("Your data has been saved on file\"" + filename + "\"");
            System.out.println("Have Fun with your curves of life");
            fw.close();
            fw2.close();
        } catch (IOException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

