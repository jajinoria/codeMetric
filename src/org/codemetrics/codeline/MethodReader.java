package org.codemetrics.codeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.metricparser.MethodMetricParser;

public class MethodReader {

    private Stack keyMethodStack = new Stack();
    private BufferedReader buffer = null;
    private int lineNumber = 0;
    private boolean startOfMethodFound = false;

    public MethodReader(File sourceFile) {
        try {
            this.buffer = new BufferedReader(new java.io.FileReader(sourceFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 public String goToStartOfMethod(String methodName) {
        String codeLine;
        while ((codeLine = readLine() ) != null) {
            if (startOfMethod(codeLine, methodName)) {
                tryToUpdateKeyStack(codeLine);
                startOfMethodFound = true;
                return codeLine;
            }
        }
        throw new RuntimeException("Start of method could NOT be found");
    }
 
    private boolean startOfMethod(String codeLine, String methodName) {
       CodeLineAnalyzer codeLineAnalyzer = new CodeLineAnalyzer();
       return codeLineAnalyzer.isMethod(codeLine, methodName);
    }
     
 
    public String readLine() {
        lineNumber++;
        try {
            String methodLine = buffer.readLine();
            if (startOfMethodFound) {
                tryToUpdateKeyStack(methodLine);
            }
            return methodLine;
        } catch (IOException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Problems reading the next line in a method");
    }

    private void tryToUpdateKeyStack(String codeLine) {
        if (codeLine.contains("{")) {
            updateStack("{");
        }
        if (codeLine.contains("}")) {
            updateStack("}");
        }
    }

    public int getCurrentLineNumber() {
        return lineNumber;
    }

    public boolean atEndOfMethod() {
        return this.keyMethodStack.empty();
    }

    public void closeMethodReader() {
        if (buffer != null) {
            try {
                buffer.close();
            } catch (IOException ex) {
                Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isMethod(String codeLine, String methodName) {
        CodeLineAnalyzer codeLineAnalyzer = new CodeLineAnalyzer();
        return codeLineAnalyzer.isMethod(codeLine, methodName);
    }

    private void updateStack(String key) {
        if (key.equals("{")) {
            keyMethodStack.push(key);
        } else {
            if (!keyMethodStack.empty()) {
                keyMethodStack.pop();
            }
        }
    }
}
