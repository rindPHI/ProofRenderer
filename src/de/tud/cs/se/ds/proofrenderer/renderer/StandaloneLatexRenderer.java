package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.Usepackage;

@RendererInformation(name = "standalone-latex", description = "Creates a standalone LaTeX document containing the specified bussproofs proof")
public class StandaloneLatexRenderer extends LatexRenderer implements
        ProofRenderer {

    @Override
    public String render(ProofTree tree) {
        final StringBuilder sb = new StringBuilder();

        sb.append("\\documentclass{article}\n").append(
                "\\usepackage{bussproofs}\n");

        for (Usepackage usepackage : tree.getUsePackages()) {
            sb.append(render(usepackage));
        }

        for (String macro : tree.getMacrodefs().keySet()) {
            sb.append(render(tree.getMacrodef(macro)));
        }

        sb.append("\n\\begin{document}\n\n");

        sb.append("\\begin{prooftree}").append(render(tree.getSubtree()))
                .append("\n\\end{prooftree}");

        sb.append("\n\n\\end{document}");

        return sb.toString();
    }

}
