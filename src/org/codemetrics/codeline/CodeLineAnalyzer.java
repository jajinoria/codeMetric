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
            if (isCommentLineOnly()) {
                return CodeLineType.COMMENT;
            } else {
                return CodeLineType.COMMENT_IN_EFFECTIVE;
            }
        }

        return CodeLineType.EFFECTIVE;
    }

    private boolean isEmptyLine() {
        String writtenLineMetaExpression = "\\d|\\w|\\S";
        return !containsMetaExpression(writtenLineMetaExpression, codeLine);
    }

    private boolean containsMetaExpression(String metaExpression, String expression) {
        Pattern pattern = Pattern.compile(metaExpression);
        Matcher matcher = pattern.matcher(expression);
        return matcher.find();
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

    private boolean isCommentLineOnly() {
        if (commentIsPrecededByCode(startOfComment())) {
            return false;
        }
        if (commentIsFollowedByCode(endOfComment())) {
            return false;
        }
        return true;
    }

    private ArrayList<String> getCommentSymbols() {
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add("//");
        symbols.add("*");
        symbols.add("/*");
        symbols.add("*/");
        return symbols;
    }

    private int endOfComment() {
        int endOfComment = codeLine.lastIndexOf("*/");
        return endOfComment == -1 ? codeLine.length() : endOfComment;
    }

    private boolean commentIsPrecededByCode(int startOfComment) {
        return commentInCodeLine(codeLine.substring(0, startOfComment));
    }

    private boolean commentIsFollowedByCode(int endOfComment) {
        return commentInCodeLine(codeLine.substring(endOfComment));
    }

    private boolean commentInCodeLine(String line) {
        String codeMetaExpression = "\\d|\\w";
        return containsMetaExpression(codeMetaExpression, line);
    }

    public boolean isMethod(String codeLine, String methodName) {
        String enclosedBySpaces = "\\s" + methodName + "\\s";
        String followedByParameters = "\\s" + methodName + "\\(";
        String metaExpression = "(" + enclosedBySpaces + ")|(" + followedByParameters + ")";
        String methodFlag = "private|public|protected";

        return containsMetaExpression(methodFlag, codeLine)
                & containsMetaExpression(metaExpression, codeLine);
    }
}
