package neighbourhoodfunction;

public class GaussNeighbourhoodFunction implements NeighbourhoodFunctionModel{
    
    private double r;   // radius
    
    public GaussNeighbourhoodFunction(int radius) {
        this.r = radius;
    }
   
    @Override
    public double[] getParameters(){
        double[] paremateres = new double[1];
        paremateres[0] = r;
        return paremateres;    
    }
    
    @Override
    public void setParameters(double[] parameters){
        r = parameters[0];
    }
  
    @Override
    public double getValue(int distance){
       return java.lang.Math.exp(-(java.lang.Math.pow(distance,2))/ (2 * r * r ));
    }   
}
