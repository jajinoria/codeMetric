package org.codemetrics.metricparser;

import java.io.File;
import org.codemetrics.codeline.CodeLineMetric;

public class PackageMetricParser {

    private int numberOfClasses;

    public int getNumberOfClasses(String sourceDirectory) {
        this.numberOfClasses = 0;

        File folder = new File(sourceDirectory);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (isJavaFile(listOfFiles[i])) {
                this.numberOfClasses++;
            }
            else if (listOfFiles[i].isDirectory()){
                this.numberOfClasses += getNumberOfClasses(listOfFiles[i].getAbsolutePath());
            }
        }
        
        return this.numberOfClasses;
    }
    
    public CodeLineMetric getCodeLines(String sourceDirectory){
        CodeLineMetric codeLineMetric = new CodeLineMetric();
        
        return codeLineMetric;
    }

    private boolean isJavaFile(File file) {
        return file.isFile() && (file.getName().endsWith(".java") || file.getName().endsWith(".JAVA"));
    }
}
