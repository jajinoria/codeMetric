
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
    
    public CodeLine getCodeLines(File sourceFile){
        return null;
    }
}
