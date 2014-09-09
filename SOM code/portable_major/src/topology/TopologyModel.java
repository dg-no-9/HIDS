package topology;

import coords.Coords;
import java.util.TreeMap;
import java.util.ArrayList;

public interface TopologyModel {
    public ArrayList getConnectedNeurons(int neuronNumber);
    
    public int getColNumber();
    
    public TreeMap getNeighbourhood(int neuronNumber);
    
    public Coords getNeuronCoordinate(int neuronNumber);
    
    public int getNumbersOfNeurons();
    
    public int getNeuronNumber(Coords coords);
    
    public int getRadius();
    
    public int getBaseRadius();
    
    public int getRowNumber();
    
    public void setColNumber(int colNumber);
    
    public void setRadius(int radius);
    
    public void setRowNumber(int rowNumber);
    
    @Override
    public String toString();
}
