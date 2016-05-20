package de.tud.cs.se.ds.proofrenderer.model;

public class OperatorDefinition implements ProofTreeModelElement {
    private String name;
    private String strDef;
    private int precedence;
    private OperatorPositions opPos;
    
    public static enum OperatorPositions {
        PREFIX, INFIX, SUFFIX, PARAM
    }
    
    public OperatorDefinition(String name, String strDef, int precedence, OperatorPositions opPos) {
        super();
        this.name = name;
        this.strDef = strDef;
        this.precedence = precedence;
        this.opPos = opPos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrDef() {
        return strDef;
    }

    public void setStrDef(String strDef) {
        this.strDef = strDef;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    public OperatorPositions getOpPos() {
        return opPos;
    }

    public void setOpPos(OperatorPositions opPos) {
        this.opPos = opPos;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("(defop ")
            .append(name)
            .append(" \"")
            .append(strDef)
            .append("\" ")
            .append(precedence)
            .append(" ")
            .append(opPos.toString().toLowerCase())
            .append(")");
        
        return sb.toString();
    }
}
