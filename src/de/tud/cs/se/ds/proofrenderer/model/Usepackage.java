package de.tud.cs.se.ds.proofrenderer.model;

public class Usepackage implements ProofTreeModelElement {
    private String pkg;
    private String args;
    
    public Usepackage(String name, String strDef) {
        super();
        this.pkg = name;
        this.args = strDef;
    }

    public String getPkgName() {
        return pkg;
    }

    public void setPkgName(String pkg) {
        this.pkg = pkg;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("(usepackage \"")
            .append(pkg)
            .append("\" \"")
            .append(args)
            .append("\")");
        
        return sb.toString();
    }
}
