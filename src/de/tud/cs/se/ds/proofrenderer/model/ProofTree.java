package de.tud.cs.se.ds.proofrenderer.model;

import java.util.HashMap;

public class ProofTree implements ProofTreeModelElement {
    private HashMap<String, OperatorDefinition> opdefs = null;
    private HashMap<String, MacroDefinition> macrodefs = null;
    private SubTree subtree = null;

    public ProofTree(HashMap<String, MacroDefinition> macrodefs,
            HashMap<String, OperatorDefinition> opdefs, SubTree subtree) {
        this.macrodefs = macrodefs;
        this.opdefs = opdefs;
        this.subtree = subtree;
    }

    public HashMap<String, MacroDefinition> getMacrodefs() {
        return macrodefs;
    }

    public void setMacrodefs(HashMap<String, MacroDefinition> macrodefs) {
        this.macrodefs = macrodefs;
    }

    public MacroDefinition getMacrodef(String name) {
        return macrodefs.get(name);
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

        for (String macrodef : macrodefs.keySet()) {
            sb.append(getMacrodef(macrodef).toString()).append("\n");
        }

        for (String opdef : opdefs.keySet()) {
            sb.append(getOpdef(opdef).toString()).append("\n");
        }

        sb.append("\n(proof ");
        sb.append(subtree.toString());
        sb.append(")");

        return sb.toString();
    }
}
