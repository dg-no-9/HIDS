package portable_major;

import learning.WTMLearningFunction;
import learningdata.LearningData;
import learningdata.LearningDataModel;
import learningfactor.ConstantFunctionalFactor;
import learningfactor.LearningFactorFunctionalModel;
import metric.EuclidesMetric;
import neighbourhoodfunction.GaussNeighbourhoodFunction;
import networkmodel.DefaultNetwork;
import networkmodel.NetworkModel;
import topology.MatrixTopology;
import topology.TopologyModel;

/*
 * author : THE BOSS
 * subject : Self Organising Map (SOM)
 */

public class portable_major {

    public static void main(String[] args) {
        boolean showComments = true;
        TopologyModel topology = new MatrixTopology(100, 100, 10);
        
        int maxCol = 63;
        double maxWeight[] = new double[maxCol];
        for (int i = 0; i < maxCol; i++){
            maxWeight[i] = 1000;
        }
        
        NetworkModel network = new DefaultNetwork(maxCol, maxWeight, topology);
        
        LearningFactorFunctionalModel factor = new ConstantFunctionalFactor(0.95);
        
        /*String [] days = {"Monday", "Tuesday","Wednesday", "Thursday", "Friday"};
        String [] weeks = {"Week0","Week2", "Week3", "Week4", "Week5", "Week6"};
        String filepath = null;
        
        for (int week = 0; week < weeks.length; week++)
        for (int day = 0; day < days.length; day++){
            if (showComments){
                System.out.println("Week = " + weeks[0] + " Day = " + days[day]);
            }
            filepath = "datasets/trainingdata/" + weeks[0] + "/" + days[day] + ".txt";
            LearningDataModel trainingData = new LearningData(filepath);
            WTMLearningFunction learning = new WTMLearningFunction(network, 1, new EuclidesMetric(), trainingData, factor, new GaussNeighbourhoodFunction(2));
            learning.learn();
        }
         */
        
        String filepath = "datasets/trainingdata/training_matrix.txt";
        LearningDataModel trainingData = new LearningData(filepath);
        WTMLearningFunction learning = new WTMLearningFunction(network, 5, new EuclidesMetric(), trainingData, factor, new GaussNeighbourhoodFunction(2));
        learning.learn();
        
        network.networkToFile("datasets/trainednetwork/trainedmodel(100,100,10,0.95,5,1000(resetted)).txt");
        network.AmmendBMUList();
        network.BMUListToText();
    }
}
