
package org.codemetrics.log;

import java.util.Date;
import org.codemetrics.log.writer.Writer;
import org.codemetrics.metricparser.MethodMetricParser;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricLogger extends Logger{
    private MethodMetricParser methodMetricParser;
    private Writer writer;
    
    public MethodMetricLogger (String filename, Writer writer){
        super(filename);
        this.methodMetricParser = new MethodMetricParser();
        this.writer = writer;
        this.writer.setFile(this.filename);
    }

    public MethodMetricParser getMethodMetricParser() {
        return methodMetricParser;
    }

    public void setMethodMetricParser(MethodMetricParser methodMetricParser) {
        this.methodMetricParser = methodMetricParser;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }    

    @Override
    public void log() {
        this.writer.writeTitle("Method metric"); 
        this.writer.writeDate(new Date());
        this.writer.writePackageName("org.gs2.test");
        this.writer.writeClassName("Test");
        this.writer.writeMethodName("BlahBlahBlah");
        this.writer.writeNumberOfParameters(1);   // aquí irá methodMetricParser.getNumberOfParamters();
        this.writer.close();                      // lo mismo con codelines;
    }
    
}
