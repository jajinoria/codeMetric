package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.CodeLineMetric;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ClassMetricParserTest {

    private Method[] methods;
    private String path = getFile().getPath();

    @Test
    public void testSomeMethod() {
        ClassMetricParser classParser = new ClassMetricParser();
        initializeMethods();
        CodeLineMetric metric = classParser.getCodeLines(getFile().getAbsolutePath());
        System.out.println(metric.getEffectiveLines());
        assertTrue(metric.getEffectiveLines() == 14);
        assertTrue(metric.getCommentLines() == 11);
        assertTrue(metric.getEmptyLines() == 5);
        assertTrue(metric.getTotalCodeLines() == 30);
        assertTrue(classParser.getNumberOfAttributes(path) == 1);
        assertTrue(classParser.getNumberOfMethods(path) == 1);
        assertTrue(classParser.getNumberOfImports(path) == 1);
    }

    private void initializeMethods() {
        CodeMetricsClassLoader loader = new CodeMetricsClassLoader();
        Class classLoaded = loader.loadFileAsClass(getFile().getPath());
        this.methods = classLoaded.getMethods();
    }

    private File getFile() {
        File file = new File("test/org/codemetrics/testFiles/"
                + "integerToStringManually/IntegerToStringManually.java");
        return file;
    }
}
