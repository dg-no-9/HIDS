/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ownhmm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bijay
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        UniqueCalls calls = new UniqueCalls();
        calls.ReadUniqueCalls("system_calls.txt");
        System.out.println(calls.GetCalls().size());

        TrainingSequence seq = new TrainingSequence();
        //seq.ReadSequenceFromFile("unique_matrix.txt", calls.GetCalls());

        //System.out.println(seq.GetSequence().toString());
        //List<List<Integer>> mylist = new ArrayList<List<Integer>>();

        List<Integer> jpt = new ArrayList<Integer>();
        jpt.add(0);
        jpt.add(0);
        jpt.add(1);
        jpt.add(1);
        //mylist.add(jpt);

        TestSequence free_test_seq = new TestSequence();
        free_test_seq.ReadSequenceFromFile("intrusionfree.txt", calls.GetCalls());

        TestSequence int_test_seq = new TestSequence();
        int_test_seq.ReadSequenceFromFile("intrusions.txt", calls.GetCalls());
        //System.out.println(seq.GetSequence().size());
        Model model = new Model(63);
        //model.train(seq.GetSequence());
        double threshold = 0.0;
        double end = -100.0;
        double gap = -1.0;
        int length = 10;
        model.ReadFromFile("model.txt");
        model.print();
        Writer writer = new Writer("hmm_roc_10.txt");

        /*Writer normal = new Writer("normal.txt");
        Writer intrusion = new Writer("intrusion.txt");

        int process = 0;

        for (int i=0; i<free_test_seq.GetSequences().size();i++){
            double prob = model.Probability(free_test_seq.GetSequences().get(i));
            //prob = java.lang.Math.log(prob);
            normal.WriteLine(i + " " + prob);
            process++;
        }

        for (int i=1; i<int_test_seq.GetSequences().size();i++){
            double prob = model.Probability(int_test_seq.GetSequences().get(i));
            //prob = java.lang.Math.log(prob);
            intrusion.WriteLine(process + " " + prob);
            process++;
        }

        normal.Close();
        intrusion.Close();*/

        while (threshold > end){
            int false_positive=0, true_negative=0;
            for (int i=0; i < free_test_seq.GetSequences().size(); i++){
                //System.out.println(model.Probability(test_seq.GetSequences().get(i), 10));
                if (model.Intrusion(free_test_seq.GetSequences().get(i), length, threshold))
                    false_positive++;
                else
                    true_negative++;

            }

            int true_positive =0, false_negative =0;
            for (int i=0; i< int_test_seq.GetSequences().size(); i++){
                if (model.Intrusion(int_test_seq.GetSequences().get(i), length, threshold))
                    true_positive++;
                else
                    false_negative++;
            }
            System.out.print("Threshold = " + threshold);
            System.out.print("False Positive = " + false_positive);
            System.out.print("True Negative = " + true_negative);
            System.out.print("True Positive =" + true_positive);
            System.out.print("False Negatice =" + false_negative);
            System.out.println("");
            threshold += gap;

            double fp = false_positive / ((double)false_positive + true_negative);
            double tp = true_positive / ((double)true_positive + false_negative);
            System.out.println(fp + " " + tp);
            writer.WriteLine(fp + " " + tp);

        }

        writer.Close();
        //List<Integer> a = new ArrayList<Integer>();
        //a.add(1);
        //a.add(2);
        //a.add(3);
        //List<Integer> b = a.subList(0, 3);
        //System.out.println(b.toString());
        //model.WriteOnFile("model.txt");
        // TODO code application logic here
    }

}
