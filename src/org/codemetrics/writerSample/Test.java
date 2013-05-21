
package org.codemetrics.writerSample;

import java.io.File;
import org.codemetrics.codeline.CodeLineMetric;
import org.codemetrics.log.MethodMetricLogger;
import org.codemetrics.log.writer.PDFWriter;
import org.codemetrics.log.writer.PlainTextWriter;
import org.codemetrics.metricparser.ClassMetricParser;

public class Test {
    private static String path = getFile().getPath();

    public static void main(String[] args) {
        ClassMetricParser c = new ClassMetricParser();
        System.out.println("Numero de atributos es " + c.getNumberOfAttributes(path));
        System.out.println("Numero de metodos es "+c.getNumberOfMethods(path));
        System.out.println("Numero de imports es" +c.getNumberOfImports(path));
        
        CodeLineMetric codelines = c.getCodeLines(path);
        System.out.println("Numero de comentarios es " + codelines.getCommentLines());
        System.out.println("Numero de lineas de codigo efectivas es " + codelines.getEffectiveLines());
        System.out.println("Numero de lineas en blanco es " + codelines.getEmptyLines());
        
    }
    
     private static File getFile() {
        File file = new File
       ("test/org/codemetrics/testFiles/"
                + "integerToStringManually/IntegerToStringManually.java"); 
        return file;
    }
    
    
}
