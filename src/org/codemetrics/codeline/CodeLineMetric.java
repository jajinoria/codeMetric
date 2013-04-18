
package org.codemetrics.codeline;

/**
 *
 * @author Daniel & Johanna
 */
public class CodeLineMetric {
    private int effectiveCodeLines;
    private int commentCodeLines;
    private int emptyCodeLines;
   
    public int getEffectiveLines(){
        return effectiveCodeLines;
    }
    
    public int getCommentLines(){
        return commentCodeLines;
    }
    
    public int getEmptyLines(){
        return emptyCodeLines;
    }
    
    public int getTotalCodeLines(){
        return effectiveCodeLines + commentCodeLines + emptyCodeLines;
    }
    
    public void incrementCommentLines(){
        this.commentCodeLines++;
    }
    
    public void incrementEmptyLines(){
        this.emptyCodeLines++;
    }
    
    public void incrementEffectiveLines(){
        this.effectiveCodeLines++;
    }
    
    public void add(CodeLineMetric codeLineMetric){
        this.commentCodeLines += codeLineMetric.commentCodeLines;
        this.effectiveCodeLines += codeLineMetric.effectiveCodeLines;
        this.emptyCodeLines += codeLineMetric.emptyCodeLines;
    }
}
