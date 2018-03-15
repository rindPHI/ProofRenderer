package de.tud.cs.se.ds.proofrenderer.model;

public class TexInput implements ProofTreeModelElement {
    private String texfile;
    
    public TexInput(String texfile) {
        super();
        this.texfile = texfile;
    }

    public String getTexfile() {
        return texfile;
    }

    public void setTexfile(String pkg) {
        this.texfile = pkg;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("(input \"")
            .append(texfile)
            .append("\")");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TexInput &&
                ((TexInput) obj).texfile.equals(texfile);
    }
    
    @Override
    public int hashCode() {
        return texfile.hashCode();
    }
}
