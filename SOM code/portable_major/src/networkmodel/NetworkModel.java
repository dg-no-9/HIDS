package networkmodel;

import java.util.HashMap;
import neuron.NeuronModel;
import topology.TopologyModel;


public interface NetworkModel{
    
    public NeuronModel getNeuron(int neuronNumber);
    
    public int getNumbersOfNeurons();
    
    public TopologyModel getTopology();
   
    public void setTopology(TopologyModel topology);
    
    public void networkToFile(String filename);
    
    public void setBMUMap(HashMap map);
    
    public HashMap getBMUMap();
    
    public void AmmendBMUList();
    
    public void BMUListToText();
    
}