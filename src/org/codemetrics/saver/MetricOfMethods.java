
package org.codemetrics.saver;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.metricparser.MethodMetricParser;


public class MetricOfMethods {
    
  

    public double CyclomaticComplexity(File absolutePath , Method method) {
        MethodMetricParser methodMetricParser = new MethodMetricParser();     
        return methodMetricParser.getCyclomaticComplexity(absolutePath, method);
    }
    
     public double NumberOfParameters(Method method) {
        MethodMetricParser methodMetricParser = new MethodMetricParser();
        return methodMetricParser.getNumberOfParameters(method);
    }

   
}
