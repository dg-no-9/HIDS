/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ownhmm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bijay
 */
public class Model {

    double[][] prob;

    Model(int size){
        prob = new double[size][size];
    }

    void train(List<List<Integer>> sequences){

        long[][] count = new long[prob.length][prob.length];

        for (int i=0; i<sequences.size(); i++){
            List<Integer> seq = sequences.get(i);
            int prev = seq.get(0);
            for (int j = 1; j<seq.size();j++){
                int curr = seq.get(j);
                count[prev][curr]++;
                prev = curr;
            }

        }

        for (int i=0; i<prob.length; i++){
            long total=0;
            for (int j=0; j<prob.length; j++){
                total += count[i][j];
            }

            for (int j=0; j<prob.length; j++){
                if (total > 0)
                    prob[i][j] = count[i][j]/(double)total;
            }
        }

    }


    void print (){
        for (int i=0; i<prob.length; i++){
            for (int j=0; j<prob.length; j++){
                System.out.print(prob[i][j] + "  ");
            }
            System.out.println(" ");
        }
    }
   

    double Probability(List<Integer> sequence){
        //System.out.println(sequence.toString());
        double probability=0.0;
        if (sequence.size() < 2){
            return 1.0;
        }

        probability = 1.0;
        int prev = sequence.get(0);
        for(int i=1; i<sequence.size(); i++){
            int next = sequence.get(i);
            if (prev == -1 || next == -1)
                return 0.0;
            probability *= prob[prev][next];
            prev = next;
        }

        //System.out.println(sequence.toString());
        //System.out.println(probability);
        return probability;
    }


    boolean Intrusion(List<Integer> sequence, int length, double threshold){
        //System.out.println(sequence.toString());
        if (sequence.size() < 2)
            return false;
        double probability =0.0;
        int count = 0;
        for (int i=1; i<sequence.size(); i=i+length-1){
           int end = i+length-1;
           if (end > sequence.size()){
               end = sequence.size();
           }
           List<Integer> seq = sequence.subList(i-1,end);
           double logprob = java.lang.Math.log(this.Probability(seq));
           if (logprob < threshold)
               return true;
           //probability += this.Probability(seq);
           //count++;
        }
        //probability = probability/count;
        return false;
    }

    void WriteOnFile(String filename){
        try {

            FileWriter file = new FileWriter(filename);
            BufferedWriter bfile = new BufferedWriter(file);

            for (int i=0; i<prob.length; i++){
                for (int j=0; j<prob.length; j++){
                    bfile.write(prob[i][j] + " ");
                }
                bfile.write("\n");
            }

            bfile.close();
            file.close();
            

        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void ReadFromFile(String filename){
        FileReader file = null;
        try {
            file = new FileReader(filename);
            BufferedReader bfile = new BufferedReader(file);

            for (int i=0; i<prob.length; i++){
                String line = bfile.readLine();
                String[] data = line.split(" ");
                for (int j=0; j<data.length; j++){
                    prob[i][j] = Double.parseDouble(data[j]);
                }
            }

            bfile.close();
            file.close();
        } catch(Exception ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

}


