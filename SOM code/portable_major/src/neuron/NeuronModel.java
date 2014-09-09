package neuron;


public interface NeuronModel{
    
   
     public double[] getWeight();
    
    
     public void setWeight(double[] weight);
    
    
    public double getValue(double[] inputVector);

}
