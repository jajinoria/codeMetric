
package org.codemetrics.metricparser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.CodeLineMetric;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Johanna
 */
public class MethodMetricParserTest {
    
    private Method[] methods;
    
    @Test
    public void testSomeMethod() {
        MethodMetricParser methodParser = new MethodMetricParser();
        initializeMethods();
        CodeLineMetric metric = methodParser.getCodeLines(getFile(), methods[0]);
        assertTrue(metric.getEffectiveLines()==6);
        assertTrue(metric.getCommentLines()==1);
        assertTrue(metric.getEmptyLines()==0);
        assertTrue(metric.getTotalCodeLines()==7);
    }
    
    private void initializeMethods(){
        CodeMetricsClassLoader loader = new CodeMetricsClassLoader();
        Class classLoaded = loader.loadFileAsClass(getFile().getPath());
        this.methods = classLoaded.getMethods();    
    }
    
    private File getFile() {
        File file = new File
       ("test/org/codemetrics/testFiles/"
                + "integerToStringManually/IntegerToStringManually.java"); 
        return file;
    }
}
