package org.codemetrics.log.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.codeline.CodeLine;

public class PlainTextWriter implements Writer {

    private String fileName;
    private FileWriter outFile;
    private PrintWriter out;

    public PlainTextWriter(String fileName) {
        try {
            this.fileName = fileName;
            this.outFile = new FileWriter(this.fileName);
            this.out = new PrintWriter(this.outFile);
        } catch (IOException ex) {
            Logger.getLogger(PlainTextWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeTitle(String name) {
        out.println(name);
        out.println(" ");
        out.println(" ");
    }

    @Override
    public void writeDate(Date date) {
        out.println("Report generated the " + date);
        out.println(" ");
    }

    @Override
    public void writePackageName(String name) {
        out.println("Package name: " + name);
    }

    @Override
    public void writeClassName(String name) {
        out.println("Class name: " + name);
    }

    @Override
    public void writeMethodName(String name) {
        out.println("Method name: " + name);
    }

    @Override
    public void writeNumberOfParameters(int numberOfParameters) {
        out.println("Number of parameters: " + numberOfParameters);
    }

    @Override
    public void writeCodeLines(CodeLine codeline) {
    }

    @Override
    public void close() {
        this.out.close();
    }
}
