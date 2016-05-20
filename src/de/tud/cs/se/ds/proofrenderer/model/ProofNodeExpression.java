package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

public class ProofNodeExpression implements ProofTreeModelElement {
    private OperatorDefinition operator = null;
    private ArrayList<ProofNodeExpression> children = null;
    private String leftLabel = "", rightLabel = "";
    
    public ProofNodeExpression(OperatorDefinition operator,
            ArrayList<ProofNodeExpression> children, String leftLabel, String rightLabel) {
        this.operator = operator;
        this.children = children;
        this.leftLabel = leftLabel;
        this.rightLabel = rightLabel;
    }

    public ProofNodeExpression(OperatorDefinition operator,
            ArrayList<ProofNodeExpression> children) {
        this.operator = operator;
        this.children = children;
    }
    
    protected ProofNodeExpression() {}

    public OperatorDefinition getOperator() {
        return operator;
    }

    public void setOperator(OperatorDefinition operator) {
        this.operator = operator;
    }

    public ArrayList<ProofNodeExpression> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ProofNodeExpression> children) {
        this.children = children;
    }
    
    public String getLeftLabel() {
        return leftLabel;
    }

    public void setLeftLabel(String label) {
        this.leftLabel = label;
    }

    public String getRightLabel() {
        return rightLabel;
    }

    public void setRightLabel(String rightLabel) {
        this.rightLabel = rightLabel;
    }

    static class ProofNodeStringExpression extends ProofNodeExpression {
        private String expr;
        
        public ProofNodeStringExpression(String expr) {
            this.expr = expr;
        }

        public String getExpr() {
            return expr;
        }

        public void setExpr(String expr) {
            this.expr = expr;
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        if (leftLabel != null && !leftLabel.isEmpty()) {
            sb.append("\"")
                .append(leftLabel)
                .append("\" : ");
        }
        
        sb.append("(")
            .append(operator.getName())
            .append(" ");
        
        for (ProofNodeExpression child : children) {
            sb.append(" ")
                .append(child);
        }
        
        sb.append(")");
        
        if (rightLabel != null && !rightLabel.isEmpty()) {
            sb.append(" : \"")
                .append(rightLabel)
                .append("\"");
        }
        
        return sb.toString();
    }
}
