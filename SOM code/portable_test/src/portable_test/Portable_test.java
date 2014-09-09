package portable_test;

import metric.EuclidesMetric;
import networkmodel.DefaultNetwork;
import networkmodel.NetworkModel;
import testing.TestDataModel;
import testing.Tester;
import testing.TestingData;
import topology.MatrixTopology;
import topology.TopologyModel;
import traineddata.TrainedDataModel;

/**
 *
 * author : THE BOSS
 */
public class Portable_test {
    
    public static void main(String[] args) {
       TopologyModel topology = new MatrixTopology(100, 100, 10);
        
       String trainedmodelfile = "datasets/trained_models/mean-sd.txt";
       String trainedweightsfile = "datasets/trained_models/trainedmodel.txt";
       NetworkModel network = new DefaultNetwork(trainedweightsfile, topology);
       
       TrainedDataModel trainedData = new TrainedDataModel(trainedmodelfile);
          
       String filepath = "datasets/testdata/test(intrusion_free)_matrix.txt";
       //String filepath = "datasets/testdata/intrusions.txt";
       TestDataModel testingData = new TestingData(filepath);
       Tester testEngine = new Tester(network, new EuclidesMetric(), testingData, trainedData);
       testEngine.generateROCdata();
       //testEngine.ROCdataTOtxt("datasets/test_results/fp-tn.txt", "datasets/test_results/tn-fn.txt");
    }
}
