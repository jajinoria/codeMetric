
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.codeline.CodeLine;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricParser {
    
    public int getNumberOfParameters(Method method){
        return method.getParameterTypes().length;
    }
    
    public CodeLine getCodeLines(File sourceFile, Method method) {
            return parseCodeLines(sourceFile, method.getName());
    }

    private CodeLine parseCodeLines(File sourceFile, String methodName) {       
        MethodReader fileReader = new MethodReader(sourceFile);
        CodeLine codeLine = new CodeLine();
        
        fileReader.goToStartOfMethod(methodName);
        int startOfMethod = fileReader.getCurrentLineNumber();
        
        do{
           fileReader.readLine();
        }while(!fileReader.atEndOfMethod());
        
        int endOfMethod = fileReader.getCurrentLineNumber();
        
        fileReader.closeMethodReader();
        codeLine.setEffectiveCodeLines(endOfMethod-startOfMethod+1);
        
        return codeLine;
    }
    
    private class MethodLocation{
        public int startLine;
        public int finishLine;
        
        public MethodLocation(int start, int finish){
            startLine = start;
            finishLine = finish;
        }
    }
}
