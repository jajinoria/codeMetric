package org.codemetrics.metricparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.ClassReader;
import org.codemetrics.codeline.CodeLineAnalyzer;
import org.codemetrics.codeline.CodeLineMetric;
import org.codemetrics.codeline.CodeLineType;

public class ClassMetricParser {

    private CodeLineMetric codeLineMetric;
    Field[] atributes;
    Method[] methods;

    public ClassMetricParser(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        this.atributes = classToAnalize.getDeclaredFields();
        this.methods = classToAnalize.getDeclaredMethods();
    }

    public ClassMetricParser() {
    }

    public int getNumberOfAttributes(String classFilename) {
        return atributes.length;
    }

    public int getNumberOfMethods(String classFilename) {
        return methods.length;
    }

    public Double sumMF(File sourceFile) {
        Double total = 0.0;
        MethodMetricParser methodParser = new MethodMetricParser();
        for (int i = 0; i < methods.length; i++) {
            for (int j = 0; j < atributes.length; j++) {
                if (methodParser.wordInMethod(sourceFile, methods[i].getName(), atributes[j].getName())) {
                    total++;
                }
            }
        }
        return total;
    }

    public Double calculateLackOfCohesion(String sourceFile) {
        return 1 - sumMF(getFile(sourceFile)) / (getNumberOfMethods(sourceFile) * getNumberOfAttributes(sourceFile));
    }

    public int getNumberOfImports(String classFilename) {
        try {
            Scanner in = new Scanner(new FileReader(classFilename));
            String text = "";
            while (in.hasNext()) {
                text += in.nextLine();
            }

            int index = text.indexOf("import");
            int count = 0;
            while (index != -1) {
                count++;
                text = text.substring(index + 1);
                index = text.indexOf("import");
            }
            return count;
        } catch (FileNotFoundException ex) {
        }
        return 0;
    }

    public CodeLineMetric getCodeLines(String sourceFile) {
        this.codeLineMetric = new CodeLineMetric();
        CodeLineAnalyzer codeLineAnalyzer = new CodeLineAnalyzer();
        ClassReader classReader = new ClassReader(getFile(sourceFile));

        String codeLine = classReader.readLine();
        do {
            upDateCodeLineMetric(codeLineAnalyzer.determineCodeLineType(codeLine));
            codeLine = classReader.readLine();
        } while (codeLine != null);

        classReader.closeClassReader();
        return codeLineMetric;
    }

    private void upDateCodeLineMetric(CodeLineType codeLineType) {
        if (codeLineType.equals(CodeLineType.COMMENT)) {
            codeLineMetric.incrementCommentLines();
        }
        if (codeLineType.equals(CodeLineType.EFFECTIVE)) {
            codeLineMetric.incrementEffectiveLines();
        }
        if (codeLineType.equals(CodeLineType.EMPTY)) {
            codeLineMetric.incrementEmptyLines();
        }
        if (codeLineType.equals(CodeLineType.COMMENT_IN_EFFECTIVE)) {
            codeLineMetric.incrementCommentLines();
            codeLineMetric.incrementEffectiveLines();
        }
    }

    private File getFile(String absolutePath) {
        File file = new File(absolutePath);
        return file;
    }

    private Class loadClass(String classFilename) {
        CodeMetricsClassLoader loader = new CodeMetricsClassLoader();
        return loader.loadFileAsClass(classFilename);
    }
}
