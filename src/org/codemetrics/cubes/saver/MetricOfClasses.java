
package org.codemetrics.cubes.saver;

import java.io.File;
import org.codemetrics.metricparser.ClassMetricParser;

/**
 *
 * @author Jose
 */
public class MetricOfClasses {

    public double lackOfCohesion(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.calculateLackOfCohesion(absolutePath);
    }

    public double numberOfImports(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.getNumberOfImports(absolutePath);
    }

    public double codeLinesOfClasses(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.getCodeLines(absolutePath).getTotalCodeLines();
    }

    public double emptyCodeLinesOfClasses(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.getCodeLines(absolutePath).getEmptyLines();
    }

    public double effectiveCodeLinesOfClasses(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.getCodeLines(absolutePath).getEffectiveLines();
    }

    public double commentCodeLinesOfClasses(String absolutePath) {
        ClassMetricParser classParser = new ClassMetricParser(absolutePath);
        return classParser.getCodeLines(absolutePath).getCommentLines();
    }
    public File getFile (String absolutePath)
    {
     ClassMetricParser classParser = new ClassMetricParser(absolutePath);
     return classParser.getFile(absolutePath);
    }
}
