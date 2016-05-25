package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;

public interface ProofRenderer {
    /**
     * Renders the given {@link ProofTree} to a String representation.
     *
     * @param tree The {@link ProofTree} to render.
     * @return The rendered {@link ProofTree}.
     */
    String render(ProofTreeModelElement tree);
    
    /**
     * Renders the given {@link ProofTree} to a String representation,
     * tanking the additional arguments into account.
     *
     * @param parseResult The {@link ProofTree} to render.
     * @param args The arguments for the renderer.
     * @return The rendered {@link ProofTree}.
     */
    String render(ProofTreeModelElement parseResult, String[] args);
}
