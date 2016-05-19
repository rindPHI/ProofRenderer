package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

public class ProofNodeExpression implements ProofTreeModelElement {
    private OperatorDefinition operator = null;
    private ArrayList<ProofNodeExpression> children = null;
    private String label = null;
    
    public ProofNodeExpression(OperatorDefinition operator,
            ArrayList<ProofNodeExpression> children, String label) {
        this.operator = operator;
        this.children = children;
        this.label = label;
    }

    public ProofNodeExpression(OperatorDefinition operator,
            ArrayList<ProofNodeExpression> children) {
        this.operator = operator;
        this.children = children;
    }
    
    private ProofNodeExpression() {}

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
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    class ProofNodeStringExpression extends ProofNodeExpression {
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
}
