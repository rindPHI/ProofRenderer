package de.tud.cs.se.ds.proofrenderer.model;

import java.util.List;

public class ProofTreeTag implements ProofTreeModelElement {
    
    private String name;
    private String file;
    private String regex;
    private char type;
    private int line;
    private List<String> scope;
    
    public ProofTreeTag(String name, String file, String regex, char type,
            int line, List<String> scope) {
        this.name = name;
        this.file = file;
        this.regex = regex;
        this.type = type;
        this.line = line;
        this.scope = scope;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    public String getRegex() {
        return regex;
    }
    
    public void setRegex(String regex) {
        this.regex = regex;
    }
    
    public char getType() {
        return type;
    }
    
    public void setType(char type) {
        this.type = type;
    }
    
    public int getLine() {
        return line;
    }
    
    public void setLine(int line) {
        this.line = line;
    }
    
    public List<String> getScope() {
        return scope;
    }
    
    public void setScope(List<String> scope) {
        this.scope = scope;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(name)
            .append("\t")
            .append(file)
            .append("\t")
            .append("/^")
            .append(regex)
            .append("$/;\"\t")
            .append(type)
            .append("\tline:")
            .append(line);
        
        if (scope != null && !scope.isEmpty()) {
            sb.append("\t");
            int i = 0;
            for (String s : scope) {
                sb.append(s);
                
                if (++i < scope.size()) {
                    sb.append(".");
                }
            }
        }
        
        return sb.toString();
    }
}
