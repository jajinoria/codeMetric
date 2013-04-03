
package org.codemetrics.metricparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.codeline.CodeLine;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricParser {
    private BufferedReader buffer=null;
    
    public int getNumberOfParameters(Method method){
        return method.getParameterTypes().length;
    }
    
    public CodeLine getCodeLines(File sourceFile, Method method){
        try {
            return getCodeLines(sourceFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Problems reading source File");
    }

    private CodeLine getCodeLines(File sourceFile) throws FileNotFoundException, IOException {
        buffer = new BufferedReader(new FileReader(sourceFile));
        int lines=0;
        
        buffer = new BufferedReader(buffer);
        
        while(buffer.readLine() != null)
            lines++;
        
        CodeLine codeLine = new CodeLine();
        codeLine.setEffectiveCodeLines(lines);
        return codeLine;
    }

    private void tryToCloseBuffer() {
        if(buffer!=null) try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
