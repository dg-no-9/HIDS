package neuron;

import java.util.Random;
import metric.MetricModel;

public class KohonenNeuron implements NeuronModel{
   
    private double[] weight;
   
    private MetricModel distanceFunction;
    
    
    public KohonenNeuron(int weightNumber, double[] maxWeight){
        if(weightNumber == maxWeight.length){
            Random rand = new Random();
            weight = new double[weightNumber];
            for(int i=0; i< weightNumber; i++){
                weight[i] = rand.nextDouble() * maxWeight[i];
            }
        }
    }
    
    public KohonenNeuron(double[] weightArray) {
        int weightSize = weightArray.length;
        weight = new double[weightSize];
        for(int i=0; i< weightSize; i++){
            weight[i] = weightArray[i];
        }
    }
    @Override
    public double[] getWeight(){
        return weight.clone();
    }
    
    
    @Override
    public double getValue(double[] inputVector){
        double value = 0;
        int inputSize = inputVector.length;
        if ( distanceFunction != null)
            value = distanceFunction.getDistance(weight, inputVector);
        return value;
      
    }
    
    @Override
    public void setWeight(double[] weight){
        System.arraycopy(weight, 0, this.weight, 0, weight.length);
    }
    
    @Override
    public String toString(){
        String text="";
        text += "[ ";
        int weightSize = weight.length;
        for (int i=0; i< weightSize; i++){
            text += weight[i];
            if(i < weightSize -1 ){
                text += ", ";
            }
        }
        text += " ]";
        return text;
    }

    public MetricModel getDistanceFunction() {
        return distanceFunction;
    }

    public void setDistanceFunction(MetricModel distanceFunction) {
        this.distanceFunction = distanceFunction;
    }
}
