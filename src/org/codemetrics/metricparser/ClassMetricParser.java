package org.codemetrics.metricparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codemetrics.classloader.CodeMetricsClassLoader;
import org.codemetrics.codeline.ClassReader;
import org.codemetrics.codeline.CodeLineAnalyzer;
import org.codemetrics.codeline.CodeLineMetric;
import org.codemetrics.codeline.CodeLineType;
import org.codemetrics.codeline.MethodReader;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class ClassMetricParser {

    private CodeLineMetric codeLineMetric;
    Field[] atributes;
    Method[] methods;

    public int getNumberOfAttributes(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        atributes= classToAnalize.getDeclaredFields();     
        return atributes.length;
    }

    public int getNumberOfMethods(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        methods = classToAnalize.getDeclaredMethods();         
        return methods.length;
    }

     private boolean containsWordInLine(String atribute, String method) {
        Pattern pattern = Pattern.compile(atribute);
        Matcher matcher = pattern.matcher(method);
        return matcher.find();
    }
    
    
     
    private boolean analizeCodeLine(File sourceFile, String methodName, String atribute) {
        boolean conteins= false;
        MethodReader methodReader = new MethodReader(sourceFile);        
        methodReader.goToStartOfMethod(methodName);       
        do{
           conteins = containsWordInLine(atribute, methodReader.readLine());
           if (conteins == true)
               return conteins;
        }while(!methodReader.atEndOfMethod());        
        methodReader.closeMethodReader();        
       return conteins;
    }
     
    
    public Double sumMF (File sourceFile)
    {
    Double total = 0.0;    
    for(int i= 0; i < methods.length; i ++)
    {    
    for (int j=0; j < atributes.length; j ++) {
            if(analizeCodeLine(sourceFile, methods[i].getName(), atributes[j].getName()))
                total ++;
        }
    }    
    return total;
    }
    
    
    
    public Double calculateLackOfCohesion(String sourceFile)
    {     
    return 1 - sumMF(getFile(sourceFile))/(getNumberOfMethods(sourceFile)* getNumberOfAttributes(sourceFile));    
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
