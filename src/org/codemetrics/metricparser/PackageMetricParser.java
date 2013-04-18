package org.codemetrics.metricparser;

import java.io.File;
import java.util.ArrayList;
import org.codemetrics.codeline.CodeLineMetric;

public class PackageMetricParser {

    private ArrayList<String> files;

    public int getNumberOfClasses(String sourceDirectory) {
        getFiles(sourceDirectory);
        return this.files.size();
    }
    
    public CodeLineMetric getCodeLines(String sourceDirectory){
        CodeLineMetric codeLineMetric = new CodeLineMetric();
        ClassMetricParser classMetricParser = new ClassMetricParser();
        if(this.files == null) getFiles(sourceDirectory);
        for(String absPath:this.files)
            codeLineMetric.add(classMetricParser.getCodeLines(absPath));
        return codeLineMetric;
    }

    private boolean isJavaFile(File file) {
        return file.isFile() && (file.getName().endsWith(".java") || file.getName().endsWith(".JAVA"));
    }

    private void getFiles(String sourceDirectory) {
        File folder = new File(sourceDirectory);
        File[] listOfFiles = folder.listFiles();
        
        this.files = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (isJavaFile(listOfFiles[i])) {
                this.files.add(listOfFiles[i].getAbsolutePath());
            }
            else if (listOfFiles[i].isDirectory()){
                getFiles(listOfFiles[i].getAbsolutePath());
            }
        }
    }

}
