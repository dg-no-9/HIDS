/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ownhmm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bijay
 */
public class Writer {

    FileWriter writer;

    Writer(String filename){
        try {
            writer = new FileWriter(filename);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void WriteLine(String line){
        try {
            writer.write(line + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void Close(){
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
