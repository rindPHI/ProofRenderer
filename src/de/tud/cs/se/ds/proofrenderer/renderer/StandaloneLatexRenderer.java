package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;

@RendererInformation(name = "standalone-latex")
public class StandaloneLatexRenderer implements ProofRenderer {

    @Override
    public String render(ProofTree tree) {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("\\documentclass{article}\n")
            .append("\\usepackage{bussproofs}\n\n")
            .append("\\begin{document}\n\n");
        
        sb.append(new LatexRenderer().render(tree));
        
        sb.append("\n\n\\end{document}");
        
        return sb.toString();
    }

}
