
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.CodeLineMetric;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PackageMetricParserTest {
    private Method[] methods;
    private String path = getFile().getPath();
    
    @Test
    public void testSomeMethod() {
        PackageMetricParser packageParser = new PackageMetricParser();
       // initializeMethods();
       /* CodeLineMetric metric = classParser.getCodeLines(getFile());
        System.out.println(metric.getEffectiveLines());
        assertTrue(metric.getEffectiveLines()==12);
        assertTrue(metric.getCommentLines()==11);
        assertTrue(metric.getEmptyLines()==5);
        assertTrue(metric.getTotalCodeLines()==28);*/
        assertTrue(packageParser.getNumberOfClasses(path)==5);
    }
    
    private void initializeMethods(){
        CodeMetricsClassLoader loader = new CodeMetricsClassLoader();
        Class classLoaded = loader.loadFileAsClass(getFile().getPath());
        this.methods = classLoaded.getMethods();    
    }
    
    private File getFile() {
        File file = new File
       ("test/org/codemetrics"); 
        return file;
    }
}
