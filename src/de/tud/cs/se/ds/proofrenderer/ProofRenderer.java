/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author Dominic Scheurer
 */
public class ProofRenderer {
    
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
        return "";
    }
}
