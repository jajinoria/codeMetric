package org.codemetrics.metricparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.ClassReader;
import org.codemetrics.codeline.CodeLineAnalyzer;
import org.codemetrics.codeline.CodeLineMetric;
import org.codemetrics.codeline.CodeLineType;

public class ClassMetricParser {

    private CodeLineMetric codeLineMetric;

    public int getNumberOfAttributes(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        return classToAnalize.getDeclaredFields().length;
    }

    public int getNumberOfMethods(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        return classToAnalize.getDeclaredMethods().length;
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
