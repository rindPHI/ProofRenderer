/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.parser.ProofLexer;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser;
import de.tud.cs.se.ds.proofrenderer.parser.ProofTreeLoader;

/**
 * TODO
 *
 * @author Dominic Scheurer
 */
public class Main {

    private File proofTreeFile = null;

    /**
     * TODO
     * 
     * @param proofTreeFile
     */
    public Main(File proofTreeFile) {
        this.proofTreeFile = proofTreeFile;
    }

    /**
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {
        File proofTreeFile = null;

        for (final String arg : args) {
            if (arg.startsWith("-")) {
                // By now, we ignore all arguments except
                // for the file name
            }
            else {
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

        final Main instance = new Main(proofTreeFile);

        try {
            String rendered = instance.render();
            System.out.println(rendered);
        }
        catch (IOException e) {
            System.err.println("Error while reading file '"
                    + proofTreeFile.getAbsolutePath() + "':");
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    public String render() throws IOException {
        final ProofLexer lexer = new ProofLexer(new ANTLRInputStream(
                new FileInputStream(proofTreeFile)));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ProofParser parser = new ProofParser(tokens);

        final ProofTreeLoader loader = new ProofTreeLoader();
        final ProofTree parseResult = loader.visitInit(parser.init());
        
        return "";
    }
}
