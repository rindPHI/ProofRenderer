/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTree;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;

/**
 * A "plain" renderer just returning the String representation of the
 * {@link ProofTree} object. May be used for cleaning up / formatting proof tree
 * files.
 *
 * @author Dominic Scheurer
 */
@RendererInformation(name = "plain", description = "Outputs the given proof cleaned-up in the same syntax")
public class PlainRenderer implements ProofRenderer {

    @Override
    public String render(ProofTreeModelElement tree, String[] args) {
        // TODO Auto-generated method stub
        return render(tree);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.tud.cs.se.ds.proofrenderer.renderer.ProofRenderer#render(de.tud.cs
     * .se.ds.proofrenderer.model.ProofTree)
     */
    @Override
    public String render(ProofTreeModelElement tree) {
        return tree.toString();
    }

}
