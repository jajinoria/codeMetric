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

        CodeLineMetric metric = packageParser.getCodeLines(getFile().getAbsolutePath());
        System.out.println(metric.getTotalCodeLines());
        assertTrue(metric.getEffectiveLines() == 32);
        assertTrue(metric.getCommentLines() == 17);
        assertTrue(metric.getEmptyLines() == 9);
        assertTrue(metric.getTotalCodeLines() == 58);
        assertTrue(packageParser.getNumberOfClasses(path) == 2);
    }

    private File getFile() {
        File file = new File("test/org/codemetrics/testFiles/integerToStringManually");
        return file;
    }
}
