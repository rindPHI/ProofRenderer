package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

public class ProofTree implements ProofTreeModelElement {
    private ArrayList<ProofNodeExpression> sequentialBlock = null;
    private ArrayList<ProofTree> subtrees = null;
    
    public ProofTree(ArrayList<ProofNodeExpression> sequentialBlock) {
        this.sequentialBlock = sequentialBlock;
    }
    
    public ProofTree(ArrayList<ProofNodeExpression> sequentialBlock,
            ArrayList<ProofTree> subtrees) {
        this.sequentialBlock = sequentialBlock;
        this.subtrees = subtrees;
    }
    
    public ArrayList<ProofNodeExpression> getSequentialBlock() {
        return sequentialBlock;
    }
    
    public void setSequentialBlock(ArrayList<ProofNodeExpression> sequentialBlock) {
        this.sequentialBlock = sequentialBlock;
    }
    
    public ArrayList<ProofTree> getSubtrees() {
        return subtrees;
    }
    
    public void setSubtrees(ArrayList<ProofTree> subtrees) {
        this.subtrees = subtrees;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("(\n");
        
        final int numExpr = sequentialBlock.size();
        int i = 0;
        for (ProofNodeExpression expr : sequentialBlock) {
            sb.append("\t");
            sb.append(expr.toString());
            if (++i < numExpr)
                sb.append("\n");
        }
        
        for (ProofTree subtree : subtrees) {
            sb.append(subtree.toString().replaceAll("^|\n", "\n\t"));
        }
        
        sb.append("\n)");
        
        return sb.toString();
    }
}
