
package org.codemetrics.log.writer;

import java.util.Date;
import org.codemetrics.codeline.CodeLine;

public interface Writer {
    public void writeTitle(String string);
    public void writeDate(Date date);
    public void writePackageName(String string);
    public void writeClassName(String string);
    public void writeMethodName(String string);
    public void writeNumberOfParameters(int numberOfParameters);
    public void writeCodeLines(CodeLine codeline);
    public void close();

    public void setFile(String filename);
}
