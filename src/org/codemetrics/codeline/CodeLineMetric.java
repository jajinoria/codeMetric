package org.codemetrics.codeline;

public class CodeLineMetric {

    private int effectiveCodeLines;
    private int commentCodeLines;
    private int emptyCodeLines;

    public int getEffectiveLines() {
        return effectiveCodeLines;
    }

    public int getCommentLines() {
        return commentCodeLines;
    }

    public int getEmptyLines() {
        return emptyCodeLines;
    }

    public int getTotalCodeLines() {
        return effectiveCodeLines + commentCodeLines + emptyCodeLines;
    }

    public void incrementCommentLines() {
        this.commentCodeLines++;
    }

    public void incrementEmptyLines() {
        this.emptyCodeLines++;
    }

    public void incrementEffectiveLines() {
        this.effectiveCodeLines++;
    }

    public void upDateCodeLineMetric(CodeLineType codeLineType) {
        if (codeLineType.equals(CodeLineType.COMMENT)) {
            incrementCommentLines();
        }

        if (codeLineType.equals(CodeLineType.EFFECTIVE)) {
            incrementEffectiveLines();
        }

        if (codeLineType.equals(CodeLineType.EMPTY)) {
            incrementEmptyLines();
        }

        if (codeLineType.equals(CodeLineType.COMMENT_IN_EFFECTIVE)) {
            incrementCommentLines();
            incrementEffectiveLines();
        }
    }

    public void add(CodeLineMetric codeLineMetric) {
        this.commentCodeLines += codeLineMetric.commentCodeLines;
        this.effectiveCodeLines += codeLineMetric.effectiveCodeLines;
        this.emptyCodeLines += codeLineMetric.emptyCodeLines;
    }
}
