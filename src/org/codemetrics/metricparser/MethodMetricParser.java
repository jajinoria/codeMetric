
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.codeline.CodeLineMetric;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricParser {
    
    public int getNumberOfParameters(Method method){
        return method.getParameterTypes().length;
    }
    
    public CodeLineMetric getCodeLines(File sourceFile, Method method) {
            return parseCodeLines(sourceFile, method.getName());
    }

    private CodeLineMetric parseCodeLines(File sourceFile, String methodName) {       
        MethodReader methodReader = new MethodReader(sourceFile);
        CodeLineMetric codeLine = new CodeLineMetric();
        
        methodReader.goToStartOfMethod(methodName);
        int startOfMethod = methodReader.getCurrentLineNumber();
        int a=-1;
        
        //do{
        if(methodReader.readLine().isEmpty())
               a = 23;
        //}while(!methodReader.atEndOfMethod());
        
        int endOfMethod = methodReader.getCurrentLineNumber();
        
        methodReader.closeMethodReader();
        codeLine.setEffectiveCodeLines(a);
        
        return codeLine;
    }
    
}
