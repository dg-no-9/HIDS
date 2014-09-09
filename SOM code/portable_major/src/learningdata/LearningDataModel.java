package learningdata;

public interface LearningDataModel {
     
    public double[] getData(int index);
    
    @Override
    public String toString();
    
    public int getDataSize();
}
