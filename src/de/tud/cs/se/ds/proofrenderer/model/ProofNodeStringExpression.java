package de.tud.cs.se.ds.proofrenderer.model;


public class ProofNodeStringExpression extends ProofNodeExpression {
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
    
    @Override
    public String toString() {
        return "\"" + expr + "\"";
    }
}
