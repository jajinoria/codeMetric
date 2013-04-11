
package org.codemetrics.writerSample;

import java.io.File;
import org.codemetrics.log.MethodMetricLogger;
import org.codemetrics.log.writer.PDFWriter;
import org.codemetrics.log.writer.PlainTextWriter;
import org.codemetrics.metricparser.ClassMetricParser;

public class Test {
    private static String path = getFile().getPath();

    public static void main(String[] args) {
        ClassMetricParser c = new ClassMetricParser();
        System.out.println(c.getNumberOfAttributes(path));
        System.out.println(c.getNumberOfMethods(path));
        System.out.println(c.getNumberOfImports(path));
        
    }
    
     private static File getFile() {
        File file = new File
       ("test/org/codemetrics/testFiles/"
                + "integerToStringManually/IntegerToStringManually.java"); 
        return file;
    }
    
    
}
