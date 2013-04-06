
package org.codemetrics.codeline;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class CodeLineMetric {
    private int effectiveCodeLines;
    private int commentCodeLines;
    private int emptyCodeLines;

    public void setEffectiveCodeLines(int effectiveCodeLines) {
        this.effectiveCodeLines = effectiveCodeLines;
    }

    public void setCommentCodeLines(int commentCodeLines) {
        this.commentCodeLines = commentCodeLines;
    }

    public void setEmptyCodeLines(int emptyCodeLines) {
        this.emptyCodeLines = emptyCodeLines;
    }
    
    public int getEffectiveCodeLines(){
        return effectiveCodeLines;
    }
    
    public int getCommentCodeLines(){
        return commentCodeLines;
    }
    
    public int getEmptyCodeLines(){
        return emptyCodeLines;
    }
    
    public int getTotalCodeLines(){
        return effectiveCodeLines + commentCodeLines + emptyCodeLines;
    }
    
    public void incrementCommentCodeLines(){
        this.commentCodeLines++;
    }
    
    public void incrementEmptyCodeLines(){
        this.emptyCodeLines++;
    }
    
    public void incrementCodeLines(){
        this.commentCodeLines++;
    }
}
