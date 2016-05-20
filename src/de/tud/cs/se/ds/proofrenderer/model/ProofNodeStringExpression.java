package de.tud.cs.se.ds.proofrenderer.model;

import de.tud.cs.se.ds.proofrenderer.model.OperatorDefinition.OperatorPositions;


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
    public OperatorDefinition getOperator() {
        return new OperatorDefinition("STRING", "", Integer.MAX_VALUE, OperatorPositions.INFIX);
    }
    
    @Override
    public String toString() {
        return "\"" + expr + "\"";
    }
}
