/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ownhmm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bijay
 */
public class UniqueCalls {
    HashMap<Integer, Integer> calls;

    UniqueCalls(){
        calls = new HashMap<Integer, Integer>();
    }

    void ReadUniqueCalls(String filename){
        FileReader file = null;
        try {
            file = new FileReader(filename);
            BufferedReader bfile = new BufferedReader(file);

            String line = new String();
            int i = 0;
            while ((line=bfile.readLine()) != null){

                int call = Integer.parseInt(line);
                calls.put(call, i);
                i++;
            }

            bfile.close();
            file.close();
        } catch (Exception ex) {
            Logger.getLogger(UniqueCalls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    HashMap<Integer,Integer> GetCalls (){
        return calls;
    }


}
