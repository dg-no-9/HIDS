package neighbourhoodfunction;

public interface NeighbourhoodFunctionModel {
    
     public double[] getParameters();
     
     public void setParameters(double[] parameters);
    
     public double getValue(int distance);
}
