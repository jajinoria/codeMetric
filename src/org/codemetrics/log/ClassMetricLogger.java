
package org.codemetrics.log;

import org.codemetrics.log.writer.Writer;
import org.codemetrics.metricparser.ClassMetricParser;

public class ClassMetricLogger extends Logger {
    
    private ClassMetricParser classMetricParser;
    private Writer writer;
    
    public ClassMetricLogger (String filename, Writer writer){
        super(filename);
        this.classMetricParser = new ClassMetricParser();
        this.writer = writer;
        this.writer.setFile(this.filename);
    }

    public ClassMetricParser getClassMetricParser() {
        return this.classMetricParser;
    }

    public void setClassMetricParser(ClassMetricParser classMetricParser) {
        this.classMetricParser = classMetricParser;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }    

    @Override
    public void log() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
