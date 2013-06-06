package org.codemetrics.cubes.saver;

import java.io.File;
import org.codemetrics.metricparser.PackageMetricParser;

/**
 *
 * @author Jose
 */
public class MetricOfPackage {

    double totalLinesOfCode(File sourceFiles) {
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getCodeLines(sourceFiles.getAbsolutePath()).getTotalCodeLines();
    }

    double totalOfClasses(File sourceFiles) {
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getNumberOfClasses(sourceFiles.getAbsolutePath());
    }

    double effectiveLinesOfCode(File sourceFiles) {
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getCodeLines(sourceFiles.getAbsolutePath()).getEffectiveLines();
    }

    double commentLinesOfCode(File sourceFiles) {
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getCodeLines(sourceFiles.getAbsolutePath()).getCommentLines();
    }

    double emptyLinesOfCode(File sourceFiles) {
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getCodeLines(sourceFiles.getAbsolutePath()).getEmptyLines();
    }
}
