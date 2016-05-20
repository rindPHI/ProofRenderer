package de.tud.cs.se.ds.proofrenderer;

import java.util.ArrayList;
import java.util.Collections;

import de.tud.cs.se.ds.proofrenderer.exception.RendererException;
import de.tud.cs.se.ds.proofrenderer.model.ProofNodeExpression;
import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.SubTree;

public class Renderer {

    private final ProofTree proofTree;
    
    public Renderer(ProofTree proofTree) {
        this.proofTree = proofTree;
    }

    public String render() {
        final StringBuilder sb = new StringBuilder();
      
        sb.append("\\begin{prooftree}")
            .append(render(proofTree.getSubtree()))
            .append("\n\\end{prooftree}");
        
        return sb.toString();
    }
    
    private String render(SubTree tree) {
        final StringBuilder sb = new StringBuilder();

        final ArrayList<ProofNodeExpression> reversedSeqBlock = tree.getSequentialBlock();
        final int numSubtrees = tree.getSubtrees().size();
        
        for (final SubTree subtree : tree.getSubtrees()) {
            sb.append(render(subtree));
        }
        
        Collections.reverse(reversedSeqBlock);
        
        sb.append("\n")
            .append(getInvRule(numSubtrees))
            .append("{")
            .append(reversedSeqBlock.get(0))
            .append("}");
        
        for (int i = 1; i < reversedSeqBlock.size(); i++) {
            sb.append("\n")
                .append(getInvRule(1))
                .append("{")
                .append(reversedSeqBlock.get(i))
                .append("}");
        }
        
        return sb.toString();
    }
    
    private String getInvRule(int premises) {
        switch (premises) {
        case 0: return "\\AxiomC";
        case 1: return "\\UnaryInfC";
        case 2: return "\\BinaryInfC";
        case 3: return "\\TrinaryInfC";
        case 4: return "\\QuaternaryInfC";
        case 5: return "\\QuinaryInfC";
        default:
            throw new RendererException("Illegal number of premises: " + premises);
        }
    }
    
//    final StringBuilder sb = new StringBuilder();
//    
//    
//    
//    return sb.toString();
    
}
