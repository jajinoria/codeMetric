package org.codemetrics.codeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johanna
 */
public class ClassReader {

    private BufferedReader buffer = null;
    private int lineNumber = 0;

    public ClassReader(File sourceFile) {
        try {
            this.buffer = new BufferedReader(new java.io.FileReader(sourceFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String readLine(){
        lineNumber++;
        try {
            String methodLine = buffer.readLine(); 
            return  methodLine;
        } catch (IOException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Problems reading the next line");
    }
    
    public int getCurrentLineNumber(){
        return lineNumber;
    }

    public void closeClassReader() {
        if (buffer != null) {
            try {
                buffer.close();
            } catch (IOException ex) {
                Logger.getLogger(ClassReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
