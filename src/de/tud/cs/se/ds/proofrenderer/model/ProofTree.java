package de.tud.cs.se.ds.proofrenderer.model;

import java.util.HashMap;

public class ProofTree implements ProofTreeModelElement {
    private HashMap<String, OperatorDefinition> opdefs = null;
    private SubTree subtree = null;
    
    public ProofTree(HashMap<String, OperatorDefinition> opdefs, SubTree subtree) {
        super();
        this.opdefs = opdefs;
        this.subtree = subtree;
    }

    public HashMap<String, OperatorDefinition> getOpdefs() {
        return opdefs;
    }
    
    public OperatorDefinition getOpdef(String name) {
        return opdefs.get(name);
    }

    public void setOpdefs(HashMap<String, OperatorDefinition> opdefs) {
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
        
        for (String opdef : opdefs.keySet()) {
            sb.append(getOpdef(opdef).toString())
                .append("\n");
        }

        sb.append("\n(proof ");
        sb.append(subtree.toString());
        sb.append(")");
        
        return sb.toString();
    }
}
