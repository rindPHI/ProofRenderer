/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;

/**
 * TODO
 *
 * @author Dominic Scheurer
 */
public class ProofRenderer {
    private static enum Scopes {
        TOP_LEVEL, COMMENT, STRING, OP_EXPECTED, OP_DEF
    }
    
    private File proofTreeFile = null; 

    /**
     * TODO
     * 
     * @param proofTreeFile
     */
    public ProofRenderer(File proofTreeFile) {
        this.proofTreeFile = proofTreeFile;
    }
    
    /**
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {
      File proofTreeFile = null;
        
        for (final String arg: args) {
            if (arg.startsWith("-")) {
                // By now, we ignore all arguments except
                // for the file name
            } else {
                // This should be the file name
                File tmp = new File(arg);
                if (arg.endsWith(".pt") && tmp.exists()) {
                    proofTreeFile = tmp;
                }
            }
        }
        
        if (proofTreeFile == null) {
            System.err.println("Please supply the .pt file to parse");
            System.exit(1);
        }
        
        final ProofRenderer instance = new ProofRenderer(proofTreeFile);
        
        try {
            String rendered = instance.render();
        }
        catch (IOException e) {
            System.err.println("Error while reading file '" + proofTreeFile.getAbsolutePath() + "':");
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    public String render() throws IOException {
        final ProofTree parsed = parse();
        return parsed.toString();
    }

    private ProofTree parse() throws IOException {
        final ProofTree result = new ProofTree();
        
        int lineNo = 0, indent = 0;
        Scopes scope = Scopes.TOP_LEVEL;
        Scopes scopeBefore = scope;
        boolean skipWhiteSpace = false;
        boolean readUntilWhiteSpace = false;
        
        FileReader rd = new FileReader(proofTreeFile);
        
        StringBuilder bf = null;
        char c;
        while ((c = (char) rd.read()) != (char) -1) {
            
            if (skipWhiteSpace) {
                if (c == ' ' || c == '\t' || c == '\n') {
                    continue;
                }
            }
            
            if (scope == Scopes.COMMENT) {
                if (c == '\n') {
                    scope = scopeBefore;
                    continue;
                } else {
                    continue;
                }
            }
            

            if (c == ';' && scope != Scopes.COMMENT && scope != Scopes.STRING) {
                scopeBefore = scope;
                scope = Scopes.COMMENT;
                continue;
            }
            
            if (scope == Scopes.TOP_LEVEL) {
                // Expecting operator declaration or proof object
                skipWhiteSpace = true;
                
                if (c == '(') {
                    scope = Scopes.OP_EXPECTED;
                    readUntilWhiteSpace = true;
                }
            }
            
            System.out.print(c);
            
        }
        
        return result;
    }
}
