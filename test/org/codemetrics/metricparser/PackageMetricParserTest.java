
package org.codemetrics.metricparser;

import java.io.File;
import org.codemetrics.codeline.CodeLineMetric;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PackageMetricParserTest {
    private String path = getFile().getPath();
    
    @Test
    public void testSomeMethod() {
        PackageMetricParser packageParser = new PackageMetricParser();
       // initializeMethods();
        CodeLineMetric metric = packageParser.getCodeLines(getFile().getAbsolutePath());
        System.out.println(metric.getTotalCodeLines());
        assertTrue(metric.getEffectiveLines()==24);
        assertTrue(metric.getCommentLines()==22);
        assertTrue(metric.getEmptyLines()==10);
        assertTrue(metric.getTotalCodeLines()==56);
        assertTrue(packageParser.getNumberOfClasses(path)==2);
    }
    
    private File getFile() {
        File file = new File
       ("test/org/codemetrics/testFiles/integerToStringManually"); 
        return file;
    }
}
