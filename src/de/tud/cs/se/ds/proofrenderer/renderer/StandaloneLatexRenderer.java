package de.tud.cs.se.ds.proofrenderer.renderer;

import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.model.TexInput;
import de.tud.cs.se.ds.proofrenderer.model.Usepackage;

@RendererInformation(name = "standalone-latex", description = "Creates a standalone LaTeX document containing the specified bussproofs proof")
public class StandaloneLatexRenderer extends LatexRenderer implements
        ProofRenderer {
    private boolean fitToPage = false;

    @Override
    public String render(ProofTreeModelElement tree, String[] args) {
        final Options clopt = new Options();

        clopt.addOption(Option.builder("p").longOpt("fit-to-page")
                .hasArg(false).desc("Fit proof tree to page size")
                .required(false).build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine parsed = parser.parse(clopt, args);

            if (parsed.hasOption("p")) {
                fitToPage = true;
            }
        }
        catch (ParseException e) {
            System.err.println("Error in parsing arguments for renderer:");
            System.err.println(e.getLocalizedMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("--renderer-args \"...\"", clopt);
        }

        return render(tree);
    }

    @Override
    public String render(ProofTreeModelElement proofTree) {
        final StringBuilder sb = new StringBuilder();

        ProofTree tree = (ProofTree) proofTree;
        
        sb.append("\\documentclass{article}\n").append(
                "\\usepackage{bussproofs}\n");

        final Set<Usepackage> packages = tree.getUsePackages();
        if (fitToPage) {
            packages.add(new Usepackage("graphics", ""));
        }

        for (Usepackage usepackage : packages) {
            sb.append(render(usepackage));
        }

        for (TexInput texinput : tree.getLatexInputs()) {
            sb.append(render(texinput));
        }

        for (String macro : tree.getMacrodefs().keySet()) {
            sb.append(render(tree.getMacrodef(macro)));
        }

        if (fitToPage) {
            sb.append("\n\\newenvironment{scprooftree}[1]%\n")
                    .append("\t{\\gdef\\scalefactor{#1}\\begin{center}\\proofSkipAmount \\leavevmode}%\n")
                    .append("\t{\\resizebox{\\scalefactor}{!}{\\DisplayProof}\\proofSkipAmount \\end{center} }\n");
        }

        sb.append("\n\\begin{document}\n\n");

        if (fitToPage) {
            sb.append("\\begin{scprooftree}{\\textwidth}");
        } else {
            sb.append("\\begin{prooftree}");
        }
        
        sb.append(render(tree.getSubtree()));
        
        if (fitToPage) {
            sb.append("\n\\end{scprooftree}");
        } else {
            sb.append("\n\\end{prooftree}");
        }

        sb.append("\n\n\\end{document}");

        return sb.toString();
    }

}
