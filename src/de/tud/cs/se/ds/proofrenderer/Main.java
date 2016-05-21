/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
        
        // Command line arguments handling
        final Options clopt = new Options();
        clopt.addOption(Option.builder("f").argName("FILE").longOpt("file")
                .desc("The .pt  file to transform").required().hasArg()
                .type(File.class).build());
        clopt.addOption(Option.builder("r").argName("RENDERER")
                .longOpt("renderer")
                .desc("The renderer for the proof [*latex* | plain]")
                .required(false).hasArg().build());
        clopt.addOption(Option.builder("h").hasArg(false)
                .desc("Display this help").required(false).build());
        
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parsed = parser.parse(clopt, args);
            
            String fileName = parsed.getOptionValue("f");
            proofTreeFile = new File(fileName);
            if (!proofTreeFile.exists()) {
                System.err.println("The given file does not exist.");
                System.exit(1);
            }
            
        }
        catch (ParseException e1) {
            System.err.println(e1.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar ProofRenderer.jar", clopt);
            System.exit(1);
        }
        // END Command line arguments handlingSystem.exit(1);

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

        return new Renderer(parseResult).render();
    }
}
