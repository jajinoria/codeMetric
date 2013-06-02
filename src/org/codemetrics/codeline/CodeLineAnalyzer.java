package org.codemetrics.codeline;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeLineAnalyzer {

    private String codeLine;

    public CodeLineType determineCodeLineType(String codeLine) {
        this.codeLine = codeLine;

        if (isEmptyLine()) {
            return CodeLineType.EMPTY;
        }

        if (containsValidCommentSymbol()) {           
                return typeOfCommentLine();          
        }

        return CodeLineType.EFFECTIVE;
    }
      private CodeLineType typeOfCommentLine() {
        if(isCommentLineOnly()) 
               return CodeLineType.COMMENT; 
        return CodeLineType.COMMENT_IN_EFFECTIVE;
    }
  

    private boolean isEmptyLine() {
        String writtenLineMetaExpression = "\\d|\\w|\\S";
        return !containsMetaExpression(writtenLineMetaExpression, codeLine);
    }

    public boolean containsMetaExpression(String metaExpression, String expression) {
        Pattern pattern = Pattern.compile(metaExpression);
        Matcher matcher = pattern.matcher(expression);
        return matcher.find();
    }
    
       public boolean containsExpressionWithoutComents (String metaExpression, String expression)
    {    this.codeLine = expression;
         if (containsValidCommentSymbol()) {
            return false;
          }
         return containsMetaExpression(metaExpression, expression);   
   
    }


    private boolean containsValidCommentSymbol() {
        String commentLineMetaExpression = "//|\\*|/\\*|\\*/";
        if (containsMetaExpression(commentLineMetaExpression, codeLine)) {
            return !isSymbolInsideAString();
        }

        return false;
    }

    private boolean isSymbolInsideAString() {
        int numberOfOcurrences = numberOfOcurrences(startOfComment(), "\"");
        if (numberOfOcurrences == 0) {
            return false;
        }
        return numberOfOcurrences % 2 == 0 ? false : true;
    }

    private int numberOfOcurrences(int limit, String toMatch) {
        int ocurrences = 0;
        for (int index = 0; index < limit; index++) {
            String charToString = String.valueOf(codeLine.charAt(index));
            if (toMatch.equals(charToString)) {
                ocurrences++;
            }
        }
        return ocurrences;
    }

    private int startOfComment() {
        int symbolIndexInArray = -1;

        for (String symbol : getCommentSymbols()) {
            if ((symbolIndexInArray = codeLine.indexOf(symbol)) != -1) {
                break;
            }
        }
        return symbolIndexInArray;
    }

   private boolean isCommentLineOnly(){
        if(commentIsPrecededByCode(startOfCommentPos())) return false;
        if(commentIsFollowedByCode(endOfComment())) return false;
        return true;
    }
      private int endOfComment(){
        int endOfComment = codeLine.lastIndexOf("*/");
        return endOfComment == -1 ? codeLine.length() : endOfComment;
    }
    
    private boolean commentIsPrecededByCode(int startOfComment){
        if(startOfComment<0) return false;
        return commentInCodeLine(codeLine.substring(0, startOfComment));
    }
 
    private boolean commentIsFollowedByCode(int endOfComment) {
        return commentInCodeLine(codeLine.substring(endOfComment));
    }
    
    private boolean commentInCodeLine(String line){
        String codeMetaExpression = "\\d|\\w";
        return containsMetaExpression(codeMetaExpression, line);
    }
    
    private int startOfCommentPos(){
        int symbolIndexInArray=-1;
        
        for(String symbol:getCommentSymbols())
            if( ( symbolIndexInArray=codeLine.indexOf(symbol) ) != -1) 
                break;
              
        return symbolIndexInArray;
    }
   
    
    private ArrayList<String> getCommentSymbols() {
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add("//");
        symbols.add("*");
        symbols.add("/*");
        symbols.add("*/");
        return symbols;
    }  

    public boolean isMethod(String codeLine, String methodName) {
        String enclosedBySpaces = "\\s" + methodName + "\\s";
        String followedByParameters = "\\s" + methodName + "\\(";
        String metaExpression = "(" + enclosedBySpaces + ")|(" + followedByParameters + ")";
        String methodFlag = "private|public|protected";

        return containsMetaExpression(methodFlag, codeLine)
                & containsMetaExpression(metaExpression, codeLine);
    }

    public int isReservedWord(String line) {
        this.codeLine = line;

        if (containsValidCommentSymbol()) {
            return 0;
        }
        int cyclomaticComplexity = 0;
        line = replaceCommonElements(line);
        String[] words = line.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("if") || words[i].equals("while") || words[i].equals("for")
                    || words[i].equals("case") || words[i].equals("&&") || words[i].equals("||")
                    || words[i].equals("catch") || words[i].equals("try") || words[i].equals("?")) {
                cyclomaticComplexity++;
            }
        }
        return cyclomaticComplexity;

    }

    private String replaceCommonElements(String string) {
        string = string.replace("{", " ");
        string = string.replace("}", " ");
        string = string.replace("(", " ");
        string = string.replace(")", " ");

        return string;
    }
}
