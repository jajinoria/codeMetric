
package org.codemetrics.writerSample;

import org.codemetrics.log.MethodMetricLogger;
import org.codemetrics.log.writer.PDFWriter;
import org.codemetrics.log.writer.PlainTextWriter;
import org.codemetrics.metricparser.ClassMetricParser;

public class Test {

    public static void main(String[] args) {
        MethodMetricLogger m = new MethodMetricLogger("C:/Users/usuario/Desktop/pdf.pdf", new PDFWriter());
        m.log();
        
        ClassMetricParser c = new ClassMetricParser();
       // System.out.println(c.getNumberOfImports(c.getClass()));
        
    }
    
    
}
