package de.tud.cs.se.ds.proofrenderer.model;

import java.util.HashMap;

/**
 * TODO
 *
 * @author Dominic Scheurer
 */
public class ProofTree {
    private TreeNode root;
    private HashMap<String, Operator> operators;
    
    public ProofTree() {
    }
    
    public ProofTree(TreeNode root, HashMap<String, Operator> operators) {
        this.root = root;
        this.operators = operators;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public HashMap<String, Operator> getOperators() {
        return operators;
    }

    public void setOperators(HashMap<String, Operator> operators) {
        this.operators = operators;
    }
}
