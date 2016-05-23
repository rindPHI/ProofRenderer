/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;

/**
 * A "plain" renderer just returning the String representation of the
 * {@link ProofTree} object. May be used for cleaning up / formatting proof tree
 * files.
 *
 * @author Dominic Scheurer
 */
@RendererInformation(name = "plain")
public class PlainRenderer implements ProofRenderer {

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.tud.cs.se.ds.proofrenderer.renderer.ProofRenderer#render(de.tud.cs
     * .se.ds.proofrenderer.model.ProofTree)
     */
    @Override
    public String render(ProofTree tree) {
        return tree.toString();
    }

}
