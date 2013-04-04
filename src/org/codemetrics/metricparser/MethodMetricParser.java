
package org.codemetrics.metricparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codemetrics.codeline.CodeLine;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricParser {
    private BufferedReader buffer=null;
    private Stack keyStack=new Stack();
    
    public int getNumberOfParameters(Method method){
        return method.getParameterTypes().length;
    }
    
    public CodeLine getCodeLines(File sourceFile, Method method) {
        try {
            return getCodeLines(sourceFile, method.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (IOException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Could not read file properly");
    }

    private CodeLine getCodeLines(File sourceFile, String methodName) throws FileNotFoundException, IOException{
        buffer = new BufferedReader(new FileReader(sourceFile));
        buffer = new BufferedReader(buffer);
        
        MethodLocation methodLocationInFile = findMethodInFile(methodName);
        
        tryToCloseBuffer();
        
        CodeLine codeLine = new CodeLine();
        codeLine.setEffectiveCodeLines(methodLocationInFile.finishLine-methodLocationInFile.startLine+1);
        return codeLine;
    }

    private void tryToCloseBuffer() {
        if(buffer!=null) try {
            buffer.close();
        } catch (IOException ex) {
            Logger.getLogger(MethodMetricParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MethodLocation findMethodInFile(String methodName) throws IOException {
        //String comment = "(?<!\")\\\\";
        int startOfMethod = findStartOfMethod(methodName);
        return new MethodLocation(startOfMethod, findEndOfMethod(startOfMethod));
    }
    
    private int findStartOfMethod(String methodName) throws IOException{
        int lineNumber=0;
        String codeLine;
        while( (codeLine=buffer.readLine()) != null){
            lineNumber++;
            if(isMethod(codeLine, methodName)){
                if(codeLine.contains("{"))
                  updateStack("{");
                break;
            }
        }
        return lineNumber;
    }
 
    private boolean isMethod(String codeLine, String methodName) {
        String enclosedBySpaces = "\\s"+methodName+"\\s";
        String followedByParameters = "\\s"+methodName+"\\(";
        String metaExpression="("+enclosedBySpaces+")|("+followedByParameters+")";
        String methodFlag = "private|public|protected";
        
        return containsMetaExpression(methodFlag, codeLine) & 
                containsMetaExpression(metaExpression, codeLine);
    }
    
     private boolean containsMetaExpression(String metaExpression, String expression){
       Pattern pattern = Pattern.compile(metaExpression);
       Matcher matcher = pattern.matcher(expression); 
       return matcher.find();
    }
     
    private int findEndOfMethod(int lineNumber) throws IOException{
        String codeLine;
        while( (codeLine=buffer.readLine()) != null){
            lineNumber++;
            if(codeLine.contains("{"))             
                try{
                    updateStack("{");
                } catch (RuntimeException ex){
                    return lineNumber;
                }
            if( codeLine.contains("}"))
                 try{
                     updateStack("}");
                 } catch (RuntimeException ex){
                     return lineNumber;
                 }
        }
        return lineNumber;
    }
    
    private void updateStack(String key){
        if(key.equals("{"))
            keyStack.push(key);
        else{
            if(!keyStack.empty()){
                keyStack.pop();
                if(keyStack.empty())throw new RuntimeException();
            }
        }
            
    }
    
    private class MethodLocation{
        public int startLine;
        public int finishLine;
        
        public MethodLocation(int start, int finish){
            startLine = start;
            finishLine = finish;
        }
    }
}
