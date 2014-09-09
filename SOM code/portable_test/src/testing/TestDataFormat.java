/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

/**
 *
 * @author bunukopoi
 */
public class TestDataFormat {
    double [] data = null;
    double label;

    public TestDataFormat() {
    }
    
    public TestDataFormat(int len){
        data = new double[len];
    }
    
    public void initialize(int length){
        this.data = new double[length];
    }
    
    public int getDataSize(){
        return data.length;
    }
}
