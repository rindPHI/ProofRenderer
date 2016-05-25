package de.tud.cs.se.ds.proofrenderer.model;

import java.util.ArrayList;

public class ProofTreeTags implements ProofTreeModelElement {
    private ArrayList<ProofTreeTag> tags;

    public ProofTreeTags(ArrayList<ProofTreeTag> tags) {
        this.tags = tags;
    }

    public ArrayList<ProofTreeTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<ProofTreeTag> tags) {
        this.tags = tags;
    };
}
