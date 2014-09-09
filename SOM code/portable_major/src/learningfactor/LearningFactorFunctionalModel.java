package learningfactor;

public interface LearningFactorFunctionalModel {
    
    public double[] getParameters();
    
    public void setParameters(double[] parameters);
    
    public double getValue(int k);
    
}
