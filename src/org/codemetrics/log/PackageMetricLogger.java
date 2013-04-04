package org.codemetrics.log;

import org.codemetrics.log.writer.Writer;
import org.codemetrics.metricparser.ClassMetricParser;
import org.codemetrics.metricparser.PackageMetricParser;

public class PackageMetricLogger extends Logger {
    
    private PackageMetricParser packageMetricParser;
    private Writer writer;
    
    public PackageMetricLogger (String filename, Writer writer){
        super(filename);
        this.packageMetricParser = new PackageMetricParser();
        this.writer = writer;
        this.writer.setFile(this.filename);
    }

    public PackageMetricParser getPackageMetricParser() {
        return this.packageMetricParser;
    }

    public void setPackageMetricParser(PackageMetricParser packageMetricParser) {
        this.packageMetricParser = packageMetricParser;
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
