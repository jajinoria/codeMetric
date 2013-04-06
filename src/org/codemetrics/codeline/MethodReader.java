package org.codemetrics.codeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codemetrics.metricparser.MethodMetricParser;


/**
 *
 * @author usuario
 */
public class MethodReader {
    
    private Stack keyMethodStack = new Stack();
    private BufferedReader buffer = null;
    private int lineNumber=0;
    private boolean readingMethod = false;
    
    public MethodReader(File sourceFile){
        try {
            this.buffer = new BufferedReader(new java.io.FileReader(sourceFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void goToStartOfMethod(String methodName) {
        String codeLine;
        while ((codeLine = readLine() ) != null) {
            if (isMethod(codeLine, methodName)) {
                tryToUpdateKeyStack(codeLine);
                readingMethod = true;
                break;
            }
        }
    }
     
    public String readLine(){
        lineNumber++;
        try {
            String methodLine = buffer.readLine();
            if(readingMethod) tryToUpdateKeyStack(methodLine);
            return  methodLine;
        } catch (IOException ex) {
            Logger.getLogger(MethodReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Problems reading the next line");
    }
    
    private void tryToUpdateKeyStack(String codeLine) {
        if (codeLine.contains("{")) 
            updateStack("{");
        if (codeLine.contains("}")) 
            updateStack("}");
    }
    
    public int getCurrentLineNumber(){
        return lineNumber;
    }

    public boolean atEndOfMethod(){
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
        String enclosedBySpaces = "\\s" + methodName + "\\s";
        String followedByParameters = "\\s" + methodName + "\\(";
        String metaExpression = "(" + enclosedBySpaces + ")|(" + followedByParameters + ")";
        String methodFlag = "private|public|protected";
        return containsMetaExpression(methodFlag, codeLine) & containsMetaExpression(metaExpression, codeLine);
    }
    
    private boolean containsMetaExpression(String metaExpression, String expression) {
        Pattern pattern = Pattern.compile(metaExpression);
        Matcher matcher = pattern.matcher(expression);
        return matcher.find();
    }

    private void updateStack(String key) {
        if (key.equals("{"))
            keyMethodStack.push(key);
        else {
            if (!keyMethodStack.empty())
                keyMethodStack.pop();
        }
    }
       
}
