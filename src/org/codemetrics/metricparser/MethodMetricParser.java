package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.codeline.CodeLineAnalyzer;
import org.codemetrics.codeline.CodeLineMetric;
import org.codemetrics.codeline.CodeLineType;
import org.codemetrics.codeline.MethodReader;

public class MethodMetricParser {

    private CodeLineMetric codeLineMetric = new CodeLineMetric();

    public int getNumberOfParameters(Method method) {
        return method.getParameterTypes().length;
    }

    public CodeLineMetric getCodeLines(File sourceFile, Method method) {
        return analizeCodeLine(sourceFile, method.getName());
    }

    private CodeLineMetric analizeCodeLine(File sourceFile, String methodName) {
        MethodReader methodReader = new MethodReader(sourceFile);

        methodReader.goToStartOfMethod(methodName);

        CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();
        do {
            upDateCodeLineMetric(codeLineReader.determineCodeLineType(methodReader.readLine()));
        } while (!methodReader.atEndOfMethod());

        methodReader.closeMethodReader();
        return codeLineMetric;
    }

    public boolean wordInMethod(File sourceFile, String methodName, String word) {
        boolean contains = false;
        CodeLineAnalyzer analizer = new CodeLineAnalyzer();
        MethodReader methodReader = new MethodReader(sourceFile);
        methodReader.goToStartOfMethod(methodName);
        do {
            contains = analizer.containsMetaExpression(word, methodReader.readLine());
            if (contains == true) {
                return contains;
            }
        } while (!methodReader.atEndOfMethod());
        methodReader.closeMethodReader();
        return contains;
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

    public int getCyclomaticComplexity(File sourceFile, Method method) {
        int cyclomaticComplexity = 0;
        MethodReader methodReader = new MethodReader(sourceFile);
        CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();
        cyclomaticComplexity += codeLineReader.isReservedWord(methodReader.goToStartOfMethod(method.getName()));
        do {
            cyclomaticComplexity += codeLineReader.isReservedWord(methodReader.readLine());

        } while (!methodReader.atEndOfMethod());
        methodReader.closeMethodReader();
        return cyclomaticComplexity;
    }
}
