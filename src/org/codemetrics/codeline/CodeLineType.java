
package org.codemetrics.codeline;

/**
 *
 * @author Johanna 
 */
public class CodeLineType {
    public final static CodeLineType EFFECTIVE = new CodeLineType();
    public final static CodeLineType COMMENT = new CodeLineType();
    public final static CodeLineType EMPTY = new CodeLineType();
    public final static CodeLineType COMMENT_IN_EFFECTIVE = new CodeLineType();
    
    private CodeLineType(){};
    
    public boolean equals(CodeLineType codeLine){
        return this == codeLine;
    }
}
