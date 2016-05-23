package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;

public interface ProofRenderer {
    /**
     * Renders the given {@link ProofTree} to a String representation.
     *
     * @return The rendered {@link ProofTree}.
     */
    String render(ProofTree tree);
}
