/**
 * 
 */
package de.tud.cs.se.ds.proofrenderer.renderer;

import de.tud.cs.se.ds.proofrenderer.model.ProofTreeModelElement;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeTag;
import de.tud.cs.se.ds.proofrenderer.model.ProofTreeTags;

/**
 * TODO
 *
 * @author Dominic Scheurer
 *
 */
public class TagsRenderer implements ProofRenderer {

    @Override
    public String render(ProofTreeModelElement tree) {
        assert tree instanceof ProofTreeTag || tree instanceof ProofTreeTags;
        
        final StringBuilder sb = new StringBuilder();
        
        if (tree instanceof ProofTreeTag) {
            sb.append(tree.toString())
                .append("\n");
        } else {
            ProofTreeTags tags = (ProofTreeTags) tree;
            for (ProofTreeTag tag : tags.getTags()) {
                sb.append(tag.toString())
                    .append("\n");
            }
        }
        
        return sb.toString();
    }

    @Override
    public String render(ProofTreeModelElement parseResult, String[] args) {
        // TODO Auto-generated method stub
        return render(parseResult);
    }

}
