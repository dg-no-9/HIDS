package networkmodel;

import neuron.NeuronModel;
import topology.TopologyModel;


public interface NetworkModel{
    
    public NeuronModel getNeuron(int neuronNumber);
    
    public int getNumbersOfNeurons();
    
    public TopologyModel getTopology();
   
    public void setTopology(TopologyModel topology);
    
    public void networkToFile(String filename);
    
}