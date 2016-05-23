/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.reflections.Reflections;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.parser.ProofLexer;
import de.tud.cs.se.ds.proofrenderer.parser.ProofParser;
import de.tud.cs.se.ds.proofrenderer.parser.ProofTreeLoader;
import de.tud.cs.se.ds.proofrenderer.renderer.ProofRenderer;
import de.tud.cs.se.ds.proofrenderer.renderer.RendererInformation;

/**
 * TODO
 *
 * @author Dominic Scheurer
 */
public class Main {

    private File proofTreeFile = null;
    private File output = null;
    private ProofRenderer renderer = null;

    /**
     * TODO
     * 
     * @param proofTreeFile
     */
    public Main(String[] args) {
        // Command line arguments handling
        final Options clopt = new Options();
        final OptionGroup optGroup = new OptionGroup();
        optGroup.setRequired(true);

        optGroup.addOption(Option.builder("f").argName("FILE").longOpt("file")
                .desc("The .pt  file to transform").required().hasArg()
                .type(File.class).build());
        clopt.addOption(Option.builder("r").argName("RENDERER")
                .longOpt("renderer").desc("The renderer for the proof")
                .required(false).hasArg().build());
        clopt.addOption(Option.builder("o").longOpt("output").hasArg()
                .argName("OUTPUT")
                .desc("Desired output file or - for command line output")
                .build());

        optGroup.addOption(Option.builder("s").longOpt("show-renderers")
                .hasArg(false).desc("Show available renderers").build());

        clopt.addOption(Option.builder("h").longOpt("help").hasArg(false)
                .desc("Display this help").required(false).build());

        clopt.addOptionGroup(optGroup);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parsed = parser.parse(clopt, args);

            if (parsed.hasOption("s")) {
                System.out.println(getRendererInformation());
                System.exit(0);
            } else {
            
                String fileName = parsed.getOptionValue("f");
                proofTreeFile = new File(fileName);
                if (!proofTreeFile.exists()) {
                    throw new ParseException("The given file does not exist.");
                }
    
                final String rendererVal = parsed.getOptionValue('r', "latex");
    
                renderer = getAvailableRenderers().get(rendererVal);
    
                if (renderer == null) {
                    throw new ParseException("Unknown renderer: '" + rendererVal
                            + "'");
                }
    
                final String outputVal = parsed.getOptionValue('o', "-");
    
                if (!outputVal.equals("-")) {
                    output = new File(outputVal);
                }
                
            }
        }
        catch (ParseException e1) {
            System.err.println(e1.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar ProofRenderer.jar", clopt);
            System.out.println();
            System.out.println(getRendererInformation());
            System.exit(1);
        }
        // END Command line arguments handlingSystem.exit(1);
    }

    /**
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {
        final Main instance = new Main(args);
        String rendered = null;
        try {
            rendered = instance.render();
        }
        catch (IOException e) {
            System.err.println("Error while reading file '"
                    + instance.proofTreeFile.getAbsolutePath() + "':");
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }

        try {
            instance.output(rendered);
        }
        catch (IOException e) {
            System.err.println("Error while writing to file '"
                    + instance.output.getAbsolutePath() + "':");
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }
    }

    public String render() throws FileNotFoundException, IOException {
        ProofTree parseResult = parse();

        return renderer.render(parseResult);
    }

    public ProofTree parse() throws IOException, FileNotFoundException {
        final ProofLexer lexer = new ProofLexer(new ANTLRInputStream(
                new FileInputStream(proofTreeFile)));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ProofParser parser = new ProofParser(tokens);

        final ProofTreeLoader loader = new ProofTreeLoader();
        final ProofTree parseResult = loader.visitInit(parser.init());

        return parseResult;
    }

    private void output(String str) throws IOException {
        if (output == null) {
            System.out.println(str);
        }
        else {
            Files.write(output.toPath(), str.getBytes());
        }
    }
    
    private String getRendererInformation() {
        final StringBuilder sb = new StringBuilder();
        
        final HashMap<String, ProofRenderer> renderers = getAvailableRenderers();
        
        sb.append("Available renderers:\n\n");
        for (String renderer : renderers.keySet()) {
            sb.append(renderer);
            
            final RendererInformation annotation = renderers.get(renderer).getClass()
                    .getAnnotation(RendererInformation.class);
            
            if (!annotation.description().isEmpty()) {
                sb.append(":\n");
                sb.append("\t" + annotation.description());
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }

    private HashMap<String, ProofRenderer> getAvailableRenderers() {
        final HashMap<String, ProofRenderer> result = new HashMap<String, ProofRenderer>();

        final Reflections reflections = new Reflections(
                "de.tud.cs.se.ds.proofrenderer.renderer");
        final Set<Class<?>> renderers = reflections
                .getTypesAnnotatedWith(RendererInformation.class);

        for (Class<?> rendererClass : renderers) {
            try {
                final Object renderer = rendererClass.newInstance();

                assert renderer instanceof ProofRenderer;

                final RendererInformation annotation = rendererClass
                        .getAnnotation(RendererInformation.class);

                result.put(annotation.name(), (ProofRenderer) renderer);
            }
            catch (InstantiationException | IllegalAccessException e) {
                System.err
                        .println("Problem with accessing class declared as renderer: '"
                                + rendererClass.getCanonicalName() + "'");
            }
        }

        return result;
    }
}
