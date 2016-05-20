package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

public class ProofTree implements ProofTreeModelElement {
    private ArrayList<OperatorDefinition> opdefs = null;
    private SubTree subtree = null;
    
    public ProofTree(ArrayList<OperatorDefinition> opdefs, SubTree subtree) {
        super();
        this.opdefs = opdefs;
        this.subtree = subtree;
    }

    public ArrayList<OperatorDefinition> getOpdefs() {
        return opdefs;
    }

    public void setOpdefs(ArrayList<OperatorDefinition> opdefs) {
        this.opdefs = opdefs;
    }

    public SubTree getSubtree() {
        return subtree;
    }

    public void setSubtree(SubTree subtree) {
        this.subtree = subtree;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        for (OperatorDefinition opdef : opdefs) {
            sb.append(opdef.toString())
                .append("\n");
        }

        sb.append("\n(proof ");
        sb.append(subtree.toString());
        sb.append(")");
        
        return sb.toString();
    }
}
