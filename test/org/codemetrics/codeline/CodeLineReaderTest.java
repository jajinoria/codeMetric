
package org.codemetrics.codeline;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class CodeLineReaderTest {
    
    private String[] trueComments;
    private String[] falseComments;
    private String[] trueCode;
    private String[] falseCode;
    private String[] trueCommentAndEffective;
    
    @Before
    public void setUp(){
        setUpComments();
        setUpCode();
        setUpCommentAndCodeHybrid();
    }

    @Test
    public void testAssertTrueCommentLine() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[0]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[1]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[2]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[3]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[4]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[5]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[6]).equals(CodeLineType.COMMENT));
       assertTrue(codeLineReader.determineCodeLineType(this.trueComments[7]).equals(CodeLineType.COMMENT));    
    }
    
    @Test
    public void testAssertFalseCommentLine() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[0]).equals(CodeLineType.COMMENT));
       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[1]).equals(CodeLineType.COMMENT));
       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[2]).equals(CodeLineType.COMMENT));
       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[3]).equals(CodeLineType.COMMENT));
       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[4]).equals(CodeLineType.COMMENT));
       assertFalse(codeLineReader.determineCodeLineType(this.falseComments[5]).equals(CodeLineType.COMMENT));
    }
    
    @Test
    public void testAssertTrueCodeLine() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertTrue(codeLineReader.determineCodeLineType(this.trueCode[0]).equals(CodeLineType.EFFECTIVE));
       assertTrue(codeLineReader.determineCodeLineType(this.trueCode[1]).equals(CodeLineType.EFFECTIVE));
       assertTrue(codeLineReader.determineCodeLineType(this.trueCode[2]).equals(CodeLineType.EFFECTIVE));
       assertTrue(codeLineReader.determineCodeLineType(this.trueCode[3]).equals(CodeLineType.EFFECTIVE));    
    }
    
    @Test
    public void testAssertFalseCodeLine() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertFalse(codeLineReader.determineCodeLineType(this.falseCode[0]).equals(CodeLineType.EFFECTIVE));
       assertFalse(codeLineReader.determineCodeLineType(this.falseCode[1]).equals(CodeLineType.EFFECTIVE));
       assertFalse(codeLineReader.determineCodeLineType(this.falseCode[2]).equals(CodeLineType.EFFECTIVE));    
    }
    
    @Test
    public void testAssertTrueCommentAndEffective() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertTrue(codeLineReader.determineCodeLineType(this.trueCommentAndEffective[0]).equals(CodeLineType.COMMENT_IN_EFFECTIVE));
       assertTrue(codeLineReader.determineCodeLineType(this.trueCommentAndEffective[1]).equals(CodeLineType.COMMENT_IN_EFFECTIVE));
       assertTrue(codeLineReader.determineCodeLineType(this.trueCommentAndEffective[2]).equals(CodeLineType.COMMENT_IN_EFFECTIVE));    
    }
    
    @Test
    public void testAssertTrueEmpty() {
       CodeLineAnalyzer codeLineReader = new CodeLineAnalyzer();

       assertTrue(codeLineReader.determineCodeLineType(" \n").equals(CodeLineType.EMPTY));
       assertTrue(codeLineReader.determineCodeLineType("     \n").equals(CodeLineType.EMPTY)); 
    }
       
    private void setUpComments(){
        this.trueComments = new String[]{
            "// helllo there",
            "// 90876 there",
            "// public void int there",
            "/* helllo there",
            "* helllo there",
            "** helllo there",
            "* helllo there */",
            "/* helllo there */"  
        };
        
        this.falseComments = new String[]{
            " helllo there",
            "90876 there //",
            "public void int there *",
            "/* helllo */ there",
            "* */ lalala",
            " \" // it seems like a comment but it´s not \" " 
        };
    }

    private void setUpCode(){
        this.trueCode = new String[]{
            "private void setUpCode()",
            "void",
            "}",
            "String fish=\" I am a fish \" ",
        };
        
         this.falseCode = new String[]{
            "private void setUpCode() //seguido de comentario",
            "*es comentario void",
            "/* le precede comentario */ int a = 2345;",
        };
    }
    
    private void setUpCommentAndCodeHybrid(){
        this.trueCommentAndEffective = new String[]{
            this.trueCode[0]+ " " +this.trueComments[0],
            this.trueComments[1]+ " */ " +this.trueCode[1],
            this.trueComments[1]+ " */ " +this.trueCode[1]+ " " +this.trueComments[2],
        };
    }
}
