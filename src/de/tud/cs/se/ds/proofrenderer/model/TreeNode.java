package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author Dominic Scheurer
 *
 */
public class TreeNode {
    private TreeNode nextSibling;
    private ArrayList<TreeNode> children;
    
    public TreeNode(TreeNode nextSibling, ArrayList<TreeNode> children) {
        this.nextSibling = nextSibling;
        this.children = children;
    }

    public TreeNode getNextSibling() {
        return nextSibling;
    }

    public void setNextSibling(TreeNode nextSibling) {
        this.nextSibling = nextSibling;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }
}
