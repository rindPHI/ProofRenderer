package de.tud.cs.se.ds.proofrenderer.model;

public class MacroDefinition implements ProofTreeModelElement {
    private String name;
    private String strDef;
    private int numParams;
    
    public MacroDefinition(String name, int numParams, String strDef) {
        super();
        this.name = name;
        this.strDef = strDef;
        this.numParams = numParams;
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

    public int getNumParams() {
        return numParams;
    }

    public void setNumParams(int numParams) {
        this.numParams = numParams;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("(macro ")
            .append(name)
            .append(" ")
            .append(numParams)
            .append(" \"")
            .append(strDef)
            .append("\")");
        
        return sb.toString();
    }
}
