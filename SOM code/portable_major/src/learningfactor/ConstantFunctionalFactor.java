package learningfactor;

public class ConstantFunctionalFactor implements LearningFactorFunctionalModel{
    
    private double constant;
    
    public ConstantFunctionalFactor(double constant) {
        this.constant = constant;
    }
    
    @Override
    public double[] getParameters(){
        double[] parameteres = new double[1];
        parameteres[0] = constant;
        return parameteres;
    }
    
    @Override
    public void setParameters(double[] parameters){
        constant = parameters[0];
    }
    
    @Override
    public double getValue(int k){
        return constant;
    }
}
