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
    public void someClass() {
        ClassMetricParser classParser = new ClassMetricParser();
        initializeMethods();
        CodeLineMetric metric = classParser.getCodeLines(getFile().getAbsolutePath());
        System.out.println(metric.getEffectiveLines());
        assertTrue(metric.getEffectiveLines() == 21);
        assertTrue(metric.getCommentLines() == 5);
        assertTrue(metric.getEmptyLines() == 4);
        assertTrue(metric.getTotalCodeLines() == 30);
        assertTrue(classParser.getNumberOfAttributes(path) == 2);
        assertTrue(classParser.getNumberOfMethods(path) == 1);
        assertTrue(classParser.getNumberOfImports(path) == 1);
        assertTrue(classParser.calculateLackOfCohesion(path) == 0.5);
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
