/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ownhmm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bijay
 */
public class TrainingSequence {

    List<List<Integer>> sequences = new ArrayList<List<Integer>>();

    void ReadSequenceFromFile(String filename, HashMap<Integer, Integer> map){
        FileReader file = null;
        try {
            file = new FileReader(filename);
            BufferedReader bfile = new BufferedReader(file);
            String line = new String();
            while ((line = bfile.readLine()) != null) {
                //System.out.println(line);
                String[] sys = line.split(" ");
                List<Integer> mySeq = new ArrayList<Integer>();
                for (int j = 0; j < sys.length; j++) {
                    int a = Integer.parseInt(sys[j]);
                    Integer temp = map.get(a);
                    mySeq.add(temp);
                }
                //System.out.println(mySeq.toString());
                sequences.add(mySeq);
            }
            bfile.close();
            file.close();
        } catch (Exception ex) {
            Logger.getLogger(TrainingSequence.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }


    List<List<Integer>> GetSequence(){
        return sequences;
    }

}
