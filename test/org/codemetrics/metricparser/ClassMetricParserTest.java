
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ClassMetricParserTest {
    private Method[] methods;
    private String path = getFile().getPath();
    
    @Test
    public void testSomeMethod() {
        ClassMetricParser classParser = new ClassMetricParser();
        initializeMethods();
        /*CodeLineMetric metric = classParser.getCodeLines(getFile(), methods[0]);
        assertTrue(metric.getEffectiveLines()==6);
        assertTrue(metric.getCommentLines()==1);
        assertTrue(metric.getEmptyLines()==0);
        assertTrue(metric.getTotalCodeLines()==7);*/
        assertTrue(classParser.getNumberOfAttributes(path)==1);
        assertTrue(classParser.getNumberOfMethods(path)==1);
        assertTrue(classParser.getNumberOfImports(path)==1);
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