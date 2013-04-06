package org.codemetrics.log.writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.codeline.CodeLineMetric;

public class PDFWriter implements Writer {
    private String filename;
    private Document document;
    private Paragraph paragraph;

    private Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    private Font red = new Font(Font.FontFamily.TIMES_ROMAN, 10,
      Font.NORMAL, BaseColor.RED);

    @Override
    public void writeTitle(String title) {
        addTitle(title);
    }

    @Override
    public void writeDate(Date date) {
        addDate(date);
    }

    @Override
    public void writePackageName(String name) {
        addPackageName(name);
    }

    @Override
    public void writeClassName(String name) {
        addClassName(name);
    }

    @Override
    public void writeMethodName(String name) {
        addMethodName(name);
    }

    @Override
    public void writeNumberOfParameters(int numberOfParameters) {
        addNumberOfParameters(numberOfParameters);
    }

    @Override
    public void writeCodeLines(CodeLineMetric codeline) {
        addCodeLines(codeline);
    }
    
    @Override
    public void close(){
        try {
            this.document.add(this.paragraph);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.document.close();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addTitle(String title) {        
        addEmptyLine(this.paragraph, 1);
        this.paragraph.add(new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        addEmptyLine(this.paragraph, 1);
    }

    private void addDate(Date date) {
        this.paragraph.add(new Paragraph("Report generated the " + date, red));
        addEmptyLine(this.paragraph, 1);
    }

    private void addPackageName(String name) {
        this.paragraph.add(new Paragraph("Package name: " + name, small));
    }

    private void addClassName(String name) {
        this.paragraph.add(new Paragraph("Class name: " + name, small));
    }

    private void addMethodName(String name) {
        this.paragraph.add(new Paragraph("Method name: " + name, small));
    }

    private void addNumberOfParameters(int numberOfParameters) {
        this.paragraph.add(new Paragraph("Number of parameters: " + numberOfParameters, small));
    }

    private void addCodeLines(CodeLineMetric codeline) {
        this.paragraph.add(new Paragraph("CodeLines: " + codeline.getTotalCodeLines(), small));
    }

    @Override
    public void setFile(String filename) {
        this.filename = filename;
        this.paragraph = new Paragraph();
        try {
            this.document = new Document();
            PdfWriter.getInstance(this.document, new FileOutputStream(this.filename));
            this.document.open();
        } catch (FileNotFoundException | DocumentException e) {
        }
    }
}
