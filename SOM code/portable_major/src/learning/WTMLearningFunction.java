package learning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import learningdata.LearningDataModel;
import learningfactor.LearningFactorFunctionalModel;
import metric.MetricModel;
import neighbourhoodfunction.NeighbourhoodFunctionModel;
import networkmodel.NetworkModel;
import neuron.NeuronModel;
import topology.TopologyModel;

public class WTMLearningFunction{
    
    protected MetricModel metrics;
    
    protected NetworkModel networkModel; 
    
    protected int maxIteration;
     
    protected LearningDataModel learningData;
     
    protected LearningFactorFunctionalModel functionalModel;
    
    protected TopologyModel topology;
    
    protected NeighbourhoodFunctionModel neighboorhoodFunction;
    
    private boolean showComments = false;
    
    public static long iteration_count = 0;
    
    public WTMLearningFunction(NetworkModel networkModel,int maxIteration,MetricModel metrics,
            LearningDataModel learningData,LearningFactorFunctionalModel functionalModel,
            NeighbourhoodFunctionModel neighboorhoodFunction) {
        this.maxIteration = maxIteration;
        this.networkModel = networkModel;
        this.metrics = metrics;
        this.learningData = learningData;
        this.functionalModel = functionalModel;
        this.topology = networkModel.getTopology();
        this.neighboorhoodFunction = neighboorhoodFunction;
    }

    public boolean isShowComments() {
        return showComments;
    }

    public void setShowComments(boolean showComments) {
        this.showComments = showComments;
    }
    
    public void setNeighboorhoodFunction(NeighbourhoodFunctionModel neighboorhoodFunction) {
        this.neighboorhoodFunction = neighboorhoodFunction;
    }

    public NeighbourhoodFunctionModel getNeighboorhoodFunction() {
        return neighboorhoodFunction;
    }
    
    public MetricModel getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricModel metrics) {
        this.metrics = metrics;
    }

    public void setNetworkModel(NetworkModel networkModel) {
        this.networkModel = networkModel;
    }

    public NetworkModel getNetworkModel() {
        return networkModel;
    }

    public void setMaxIteration(int maxIteration) {
        this.maxIteration = maxIteration;
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setLearningData(LearningDataModel learningData) {
        this.learningData = learningData;
    }

    public LearningDataModel getLearningData() {
        return learningData;
    }

    public void setFunctionalModel(LearningFactorFunctionalModel functionalModel) {
        this.functionalModel = functionalModel;
    }

    public LearningFactorFunctionalModel getFunctionalModel() {
        return functionalModel;
    }
   
    protected int getBestNeuron(double[] vector){
        NeuronModel tempNeuron;
        double distance, bestDistance = -1;
        int networkSize = networkModel.getNumbersOfNeurons();
        int bestNeuron = 0;
        for(int i=0; i< networkSize; i++){
            tempNeuron = networkModel.getNeuron(i);
            if(tempNeuron != null){
                distance = metrics.getDistance(tempNeuron.getWeight(), vector);
                if((distance < bestDistance) || (bestDistance == -1)){
                    bestDistance = distance;
                    bestNeuron = i;
                }
            }
        }
        if (networkModel.getBMUMap().containsKey(bestNeuron)){
            ArrayList temp = (ArrayList) networkModel.getBMUMap().get(bestNeuron);
            temp.add(bestDistance);
        }
        
        else{
            ArrayList temp = new ArrayList();
            temp.add(bestDistance);
            networkModel.getBMUMap().put(bestNeuron, temp);
        }
        return bestNeuron;
    }
    
    
    protected void changeNeuronWeight(int neuronNumber, double[] vector,
        int iteration, int distance){
        double[] weightList = networkModel.getNeuron(neuronNumber - 1).getWeight();
        int weightNumber = weightList.length;
        double weight;
        if(showComments){
            String vectorText="[";
            for(int i=0; i<vector.length; i++){
                vectorText += vector[i];
                if(i < vector.length -1 ){
                    vectorText += ", ";
                }
            }
            vectorText += "]";
            System.out.println("Vector: " + vectorText);
            String weightText="[";
            for(int i=0; i<weightList.length; i++){
                weightText += weightList[i];
                 if(i < weightList.length -1 ){
                    weightText += ", ";
                }
            }
            weightText += "]";
            System.out.println("Neuron "+ (neuronNumber+1 ) + " weight before change: " + weightText);    
        }
        for (int i=0; i<weightNumber; i++){
            weight = weightList[i];
            weightList[i] += functionalModel.getValue(iteration) * neighboorhoodFunction.getValue(distance) * (vector[i] - weight);
        }
        networkModel.getNeuron(neuronNumber - 1).setWeight(weightList);
        
        if(showComments){
            String weightText="[";
            for(int i=0; i<weightList.length; i++){
                weightText += weightList[i];
                if(i < weightList.length -1 ){
                    weightText += ", ";
                }
            }
            weightText += "]";
            System.out.println("Neuron "+ (neuronNumber + 1 ) + " weight after change: " + weightText);
        }
    }
    
   
    public void changeWeight(int neuronNumber,double[] vector, int iteration){
        TreeMap neighboorhood = topology.getNeighbourhood(neuronNumber);
        Iterator it = neighboorhood.keySet().iterator();
        int neuronNr;
        while(it.hasNext()){
            neuronNr = (Integer)it.next();
            changeNeuronWeight(neuronNr,vector,iteration,(Integer)neighboorhood.get(neuronNr));
        }
    }
    
    public void learn(){
        int bestNeuron = 0;
        double[] vector;
        int dataSize = learningData.getDataSize();
        for (int i=0; i< maxIteration; i++){
            showComments = true;    //zzzzzzzzzzzzzzz
            if(showComments){
                System.out.println("Iteration number: " + (i + 1));
            }
            for(int j= 0; j<dataSize; j++){
                
                showComments = true;    //zzzzzzzzzzzzzzzzzz
                vector = learningData.getData(j);
                bestNeuron = getBestNeuron(vector); 
                if(showComments){
                    System.out.println(j + "th data : BMU number : " + (bestNeuron + 1));
                }
                showComments = false;
                changeWeight(bestNeuron, vector, i);
                
                topology.setRadius((int) (topology.getBaseRadius() * Math.exp(-(iteration_count * iteration_count) / 1000000.0)));    //decaying the radius on pass through each data entry
                iteration_count++;
                System.out.println(" Radius = " + topology.getRadius());
            }  
            iteration_count = 0;
            topology.setRadius(topology.getBaseRadius());
        }
    }
}
